package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.GeminiAgentService
import com.example.data.SampleData
import com.example.data.local.AppDatabase
import com.example.data.local.InquiryEntity
import com.example.model.ActionType
import com.example.model.AgentType
import com.example.model.ChatMessage
import com.example.model.Destination
import com.example.model.OfficialContacts
import com.example.model.SocialForum
import com.example.model.TourRequirement
import com.example.model.TravelService
import com.example.model.VisitorPurpose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IslamabadViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val inquiryDao = db.inquiryDao()
    private val bookmarkDao = db.bookmarkDao()
    private val agentService = GeminiAgentService()

    // Navigation Tab: 0 = Explore, 1 = AI Agents, 2 = Services, 3 = Custom Planner, 4 = Inquiries & Guide
    private val _selectedTab = MutableStateFlow(0)
    val selectedTab: StateFlow<Int> = _selectedTab.asStateFlow()

    // Currency toggle: false = PKR, true = USD
    private val _useUsd = MutableStateFlow(false)
    val useUsd: StateFlow<Boolean> = _useUsd.asStateFlow()

    // Purpose and Destination filters
    private val _selectedPurpose = MutableStateFlow<VisitorPurpose?>(null)
    val selectedPurpose: StateFlow<VisitorPurpose?> = _selectedPurpose.asStateFlow()

    private val _selectedDestinationCategory = MutableStateFlow("All")
    val selectedDestinationCategory: StateFlow<String> = _selectedDestinationCategory.asStateFlow()

    private val _selectedDestinationDetail = MutableStateFlow<Destination?>(null)
    val selectedDestinationDetail: StateFlow<Destination?> = _selectedDestinationDetail.asStateFlow()

    // Service filters & booking dialog
    private val _selectedServiceCategory = MutableStateFlow("All")
    val selectedServiceCategory: StateFlow<String> = _selectedServiceCategory.asStateFlow()

    private val _bookingTargetService = MutableStateFlow<TravelService?>(null)
    val bookingTargetService: StateFlow<TravelService?> = _bookingTargetService.asStateFlow()

    // Active AI Agent & Chat State
    private val _activeAgent = MutableStateFlow(AgentType.ALL_PURPOSE_CONCIERGE)
    val activeAgent: StateFlow<AgentType> = _activeAgent.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isAgentTyping = MutableStateFlow(false)
    val isAgentTyping: StateFlow<Boolean> = _isAgentTyping.asStateFlow()

    // Custom Tour Builder state
    private val _customTourForm = MutableStateFlow(TourRequirement())
    val customTourForm: StateFlow<TourRequirement> = _customTourForm.asStateFlow()

    private val _generatedCustomPlan = MutableStateFlow<String?>(null)
    val generatedCustomPlan: StateFlow<String?> = _generatedCustomPlan.asStateFlow()

    private val _isGeneratingPlan = MutableStateFlow(false)
    val isGeneratingPlan: StateFlow<Boolean> = _isGeneratingPlan.asStateFlow()

    // Inquiries from Room Database
    val savedInquiries: StateFlow<List<InquiryEntity>> = inquiryDao.getAllInquiries()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Bookmarks from Room Database
    val savedBookmarks: StateFlow<List<com.example.data.local.BookmarkEntity>> = bookmarkDao.getAllBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookmarkedIds: StateFlow<List<String>> = bookmarkDao.getAllBookmarkedIdsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Feedback Toast / SnackBar state
    private val _userMessageToast = MutableStateFlow<String?>(null)
    val userMessageToast: StateFlow<String?> = _userMessageToast.asStateFlow()

    init {
        // Initialize chat with default greeting from ALL_PURPOSE_CONCIERGE
        setAgent(AgentType.ALL_PURPOSE_CONCIERGE)
    }

    fun isItemBookmarked(id: String): Boolean {
        return bookmarkedIds.value.contains(id)
    }

    fun toggleBookmarkDestination(destination: Destination) {
        viewModelScope.launch {
            if (isItemBookmarked(destination.id)) {
                bookmarkDao.deleteById(destination.id)
                _userMessageToast.value = "Removed from Bookmarks"
            } else {
                val entity = com.example.data.local.BookmarkEntity(
                    id = destination.id,
                    itemType = "DESTINATION",
                    title = destination.name,
                    subtitle = destination.subtitle,
                    category = destination.category,
                    detailSnippet = destination.description.take(180) + "...",
                    priceOrDistance = destination.distanceFromCenter,
                    ratingOrVehicle = "★ ${destination.rating}"
                )
                bookmarkDao.insertBookmark(entity)
                _userMessageToast.value = "Saved to Bookmarks!"
            }
        }
    }

    fun toggleBookmarkService(service: TravelService) {
        viewModelScope.launch {
            if (isItemBookmarked(service.id)) {
                bookmarkDao.deleteById(service.id)
                _userMessageToast.value = "Removed from Bookmarks"
            } else {
                val entity = com.example.data.local.BookmarkEntity(
                    id = service.id,
                    itemType = when (service.category) {
                        "Stays & Hotels" -> "HOTEL"
                        "Transport & Transit" -> "TRANSPORT"
                        "Dining & Food" -> "FOOD"
                        else -> "TOUR"
                    },
                    title = service.title,
                    subtitle = service.subtitle,
                    category = service.category,
                    detailSnippet = service.description.take(180) + "...",
                    priceOrDistance = "PKR ${"%,d".format(service.pricePkr)} ($${service.priceUsd}) ${service.priceUnit}",
                    ratingOrVehicle = service.vehicleOrType
                )
                bookmarkDao.insertBookmark(entity)
                _userMessageToast.value = "Saved to Bookmarks!"
            }
        }
    }

    fun removeBookmark(id: String) {
        viewModelScope.launch {
            bookmarkDao.deleteById(id)
            _userMessageToast.value = "Bookmark removed"
        }
    }

    fun selectTab(index: Int) {
        _selectedTab.value = index
    }

    fun toggleCurrency() {
        _useUsd.value = !_useUsd.value
    }

    fun selectPurpose(purpose: VisitorPurpose?) {
        _selectedPurpose.value = purpose
        if (purpose != null) {
            _selectedDestinationCategory.value = purpose.categoryMatch
            // Synchronize active AI Concierge with chosen purpose
            val targetAgent = when (purpose) {
                VisitorPurpose.HEALTH_MEDICAL -> AgentType.HEALTH_MEDICAL
                VisitorPurpose.LEGAL_COURTS -> AgentType.COURTS_OFFICIAL
                VisitorPurpose.EDUCATION_EXAMS -> AgentType.STUDY_ACADEMICS
                VisitorPurpose.SHOPPING_LIFESTYLE -> AgentType.ALL_PURPOSE_CONCIERGE
                VisitorPurpose.TOURISM_EXPLORE -> AgentType.ALL_PURPOSE_CONCIERGE
                VisitorPurpose.NORTHERN_GATEWAY -> AgentType.NORTHERN_GATEWAY
            }
            setAgent(targetAgent)
            // Pre-fill Custom Planner requirement
            _customTourForm.value = _customTourForm.value.copy(visitorPurpose = purpose)
        } else {
            _selectedDestinationCategory.value = "All"
        }
    }

    fun clearPurposeFilter() {
        selectPurpose(null)
    }

    fun setDestinationCategory(category: String) {
        _selectedDestinationCategory.value = category
        // Sync selected purpose if matching
        val matchingPurpose = VisitorPurpose.values().firstOrNull { it.categoryMatch.equals(category, ignoreCase = true) }
        _selectedPurpose.value = matchingPurpose
    }

    fun openDestinationDetail(destination: Destination?) {
        _selectedDestinationDetail.value = destination
    }

    fun setServiceCategory(category: String) {
        _selectedServiceCategory.value = category
    }

    fun openBookingDialog(service: TravelService?) {
        _bookingTargetService.value = service
    }

    fun clearToast() {
        _userMessageToast.value = null
    }

    fun setAgent(agent: AgentType) {
        _activeAgent.value = agent
        _messages.value = listOf(
            ChatMessage(
                text = agent.greeting,
                isFromUser = false,
                agentType = agent
            )
        )
    }

    fun askAgentAboutDestination(destination: Destination) {
        val targetAgent = when (destination.purpose) {
            VisitorPurpose.HEALTH_MEDICAL -> AgentType.HEALTH_MEDICAL
            VisitorPurpose.LEGAL_COURTS -> AgentType.COURTS_OFFICIAL
            VisitorPurpose.EDUCATION_EXAMS -> AgentType.STUDY_ACADEMICS
            VisitorPurpose.SHOPPING_LIFESTYLE -> AgentType.ALL_PURPOSE_CONCIERGE
            VisitorPurpose.TOURISM_EXPLORE -> AgentType.ALL_PURPOSE_CONCIERGE
            VisitorPurpose.NORTHERN_GATEWAY -> AgentType.NORTHERN_GATEWAY
        }
        setAgent(targetAgent)
        selectTab(1) // Switch to AI Agents chat
        sendMessage("Tell me about visiting ${destination.name} (${destination.category}) in ${destination.locationSector}. What are key tips, metro bus access, and advice?")
    }

    fun sendMessage(userText: String) {
        val trimmed = userText.trim()
        if (trimmed.isEmpty()) return

        val agent = _activeAgent.value
        val userMsg = ChatMessage(
            text = trimmed,
            isFromUser = true,
            agentType = agent
        )
        _messages.value = _messages.value + userMsg

        _isAgentTyping.value = true
        viewModelScope.launch {
            try {
                val reply = agentService.consultAgent(agent, trimmed)
                val agentMsg = ChatMessage(
                    text = reply,
                    isFromUser = false,
                    agentType = agent
                )
                _messages.value = _messages.value + agentMsg
            } catch (e: Exception) {
                val errorMsg = ChatMessage(
                    text = "I am ready to assist you. Here is advice for ${agent.title}: Let me know if you would like to book transport (Metro, inDrive, Yango, 4x4), hotels, or visit schedules!",
                    isFromUser = false,
                    agentType = agent
                )
                _messages.value = _messages.value + errorMsg
            } finally {
                _isAgentTyping.value = false
            }
        }
    }

    fun submitServiceInquiry(
        service: TravelService,
        clientName: String,
        contact: String,
        origin: String,
        dates: String,
        details: String
    ) {
        viewModelScope.launch {
            val entity = InquiryEntity(
                clientName = clientName.ifBlank { "Valued Visitor" },
                contactInfo = contact.ifBlank { "In-App Inquiry" },
                serviceTitle = service.title,
                category = service.category,
                origin = origin,
                dates = dates.ifBlank { "Flexible Dates" },
                details = details,
                estimatedCostPkr = service.pricePkr,
                estimatedCostUsd = service.priceUsd,
                status = "Inquiry Confirmed"
            )
            inquiryDao.insertInquiry(entity)
            _bookingTargetService.value = null
            _userMessageToast.value = "Inquiry submitted! Our 24/7 team will contact you."
        }
    }

    fun updateTourRequirement(updated: TourRequirement) {
        _customTourForm.value = updated
    }

    fun generateCustomItinerary() {
        val form = _customTourForm.value
        _isGeneratingPlan.value = true

        val prompt = buildString {
            append("Create a comprehensive tailor-made ${form.durationDays}-day visit plan for Islamabad.")
            append(" Purpose of Visit: ${form.visitorPurpose.title} (${form.visitorPurpose.description}).")
            append(" Budget Tier: ${form.budgetTier.title} (${form.budgetTier.estimatedDailyPkr}).")
            append(" Preferred Transport: ${form.preferredTransport}.")
            append(" Arriving from: ${form.origin}.")
            append(" Group: ${form.travelGroupType}.")
            if (form.includePickAndDrop) append(" Include Airport / Station Pick & Drop.")
            if (form.includeHotel) append(" Include Accommodations matching ${form.budgetTier.stayType}.")
            if (form.includeDedicatedCar) append(" Include Dedicated city transport.")
            if (form.specialNotes.isNotBlank()) append(" Special requests: ${form.specialNotes}.")
            append(" Provide day-by-day scheduling, transport advice (Metro, inDrive/Yango, or private cab), and cost estimates in PKR and USD.")
        }

        viewModelScope.launch {
            try {
                val result = agentService.consultAgent(AgentType.ALL_PURPOSE_CONCIERGE, prompt)
                _generatedCustomPlan.value = result
            } catch (e: Exception) {
                _generatedCustomPlan.value = "Custom plan generated: Tailor-made ${form.durationDays}-day itinerary for ${form.visitorPurpose.title} on a ${form.budgetTier.title} budget. Transport via ${form.preferredTransport} with dedicated stay coordination."
            } finally {
                _isGeneratingPlan.value = false
            }
        }
    }

    fun saveCustomPlanAsInquiry() {
        val form = _customTourForm.value
        val planText = _generatedCustomPlan.value ?: return

        viewModelScope.launch {
            val dailyMultiplier = when (form.budgetTier) {
                com.example.model.BudgetTier.BUDGET_SAVER -> 3500L
                com.example.model.BudgetTier.COMFORT_STANDARD -> 9500L
                com.example.model.BudgetTier.EXECUTIVE_VIP -> 28000L
            }
            val estPkr = (form.durationDays * dailyMultiplier)
            val estUsd = (estPkr / 280).toInt()
            val entity = InquiryEntity(
                clientName = form.clientName.ifBlank { "Visitor Client" },
                contactInfo = form.phoneOrContact.ifBlank { "In-App Request" },
                serviceTitle = "${form.durationDays}-Day Tailored ${form.visitorPurpose.title} (${form.budgetTier.title})",
                category = "Custom Visit Plan",
                origin = form.origin,
                dates = form.travelDates.ifBlank { "Upcoming Dates" },
                details = planText.take(500) + "...",
                estimatedCostPkr = estPkr,
                estimatedCostUsd = estUsd,
                status = "Tailored Request Saved"
            )
            inquiryDao.insertInquiry(entity)
            _userMessageToast.value = "Tailored plan saved to My Inquiries!"
            selectTab(4) // Move to Inquiries
        }
    }

    fun deleteInquiry(id: Long) {
        viewModelScope.launch {
            inquiryDao.deleteById(id)
        }
    }

    fun shareInquiryViaIntent(context: Context, inquiry: InquiryEntity) {
        val shareText = """
            *${OfficialContacts.APP_NAME} - Official Visitor Inquiry*
            📋 Service: ${inquiry.serviceTitle}
            👤 Client: ${inquiry.clientName}
            📞 Contact: ${inquiry.contactInfo}
            ✈️ Origin: ${inquiry.origin}
            📅 Dates: ${inquiry.dates}
            💰 Estimated: PKR ${"%,d".format(inquiry.estimatedCostPkr)} / $${inquiry.estimatedCostUsd}
            📝 Details: ${inquiry.details}
            
            🌐 Official Web: ${OfficialContacts.WEBPAGE_URL}
            📧 Email: ${OfficialContacts.OFFICIAL_EMAIL}
            📱 Hotline / WhatsApp / Telegram: ${OfficialContacts.OFFICIAL_PHONE}
            _Booked via ${OfficialContacts.APP_NAME} 24/7 AI Desk_
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "${OfficialContacts.APP_NAME} Visitor Inquiry: ${inquiry.serviceTitle}")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Share Inquiry via WhatsApp or Email"))
    }

    fun openDirectWhatsApp(context: Context, inquiry: InquiryEntity) {
        val phone = OfficialContacts.OFFICIAL_PHONE_INTL.removePrefix("+")
        val message = Uri.encode(
            "Hello ${OfficialContacts.APP_NAME}! I would like to confirm my visit booking for ${inquiry.serviceTitle} (${inquiry.dates}). Client: ${inquiry.clientName} (${inquiry.contactInfo}). Origin: ${inquiry.origin}"
        )
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$message")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            shareInquiryViaIntent(context, inquiry)
        }
    }

    fun openOfficialForum(context: Context, forum: SocialForum) {
        try {
            when (forum.actionType) {
                ActionType.CALL_PHONE -> {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(forum.actionUrl))
                    context.startActivity(intent)
                }
                ActionType.SEND_SMS -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(forum.actionUrl)).apply {
                        putExtra("sms_body", "Hello ${OfficialContacts.APP_NAME}! I am visiting Islamabad and need transport, hotel, or guide assistance.")
                    }
                    context.startActivity(intent)
                }
                ActionType.SEND_EMAIL -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(forum.actionUrl))
                    context.startActivity(intent)
                }
                ActionType.OPEN_WHATSAPP,
                ActionType.OPEN_TELEGRAM,
                ActionType.OPEN_URL -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(forum.actionUrl))
                    context.startActivity(intent)
                }
            }
        } catch (e: Exception) {
            _userMessageToast.value = "Unable to open application. Contact: ${OfficialContacts.OFFICIAL_PHONE}"
        }
    }
}

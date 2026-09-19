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
import com.example.model.AgentType
import com.example.model.ChatMessage
import com.example.model.Destination
import com.example.model.TourRequirement
import com.example.model.TravelService
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

    // Destination filters
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
    private val _activeAgent = MutableStateFlow(AgentType.CITY_GUIDE)
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
        // Initialize chat with default greeting from CITY_GUIDE
        setAgent(AgentType.CITY_GUIDE)
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
                    priceOrDistance = destination.distanceFromIslamabad,
                    ratingOrVehicle = "★ ${destination.rating}"
                )
                bookmarkDao.insertBookmark(entity)
                _userMessageToast.value = "Added to Bookmarks! (Accessible in Inquiries & Bookmarks)"
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
                        "Hotels & Stays" -> "HOTEL"
                        "Pick & Drop" -> "TRANSPORT"
                        "Camping & Adventure" -> "CAMPING"
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

    fun setDestinationCategory(category: String) {
        _selectedDestinationCategory.value = category
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
        setAgent(destination.recommendedAgent)
        selectTab(1) // Switch to AI Agents chat
        sendMessage("Tell me about visiting ${destination.name} in ${destination.category}. What are the highlights, visiting times, and tips?")
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
                    text = "I am ready to assist you. Here is advice for ${agent.title}: Let me know if you would like to book transport, hotels, or scenic tours!",
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
                clientName = clientName.ifBlank { "Valued Tourist" },
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
            _userMessageToast.value = "Inquiry submitted! Our coordination team will contact you."
        }
    }

    fun updateTourRequirement(updated: TourRequirement) {
        _customTourForm.value = updated
    }

    fun generateCustomItinerary() {
        val form = _customTourForm.value
        _isGeneratingPlan.value = true

        val prompt = buildString {
            append("Create a comprehensive customized ${form.durationDays}-day travel plan for ${form.travelGroupType} arriving from ${form.origin}.")
            append(" Preferences include: ")
            if (form.includePickAndDrop) append("Airport Pick & Drop, ")
            if (form.includeHotel) append("Hotel accommodations in Islamabad / Galyat, ")
            if (form.includeDedicatedCar) append("Dedicated chauffeur vehicle, ")
            if (form.includeCamping) append("Camping & trekking experience, ")
            if (form.specialNotes.isNotBlank()) append("Special wishes: ${form.specialNotes}. ")
            append("Include recommended places (Islamabad sights, Murree, Nathia Gali, Ayubia), schedule, and budget breakdown in PKR and USD.")
        }

        viewModelScope.launch {
            try {
                val result = agentService.consultAgent(AgentType.CUSTOM_TOUR_PLANNER, prompt)
                _generatedCustomPlan.value = result
            } catch (e: Exception) {
                _generatedCustomPlan.value = "Custom plan generated: 3-day itinerary covering Islamabad capital sights, Murree Mall Road, Patriata Cable Car, and Nathia Gali pine chalets with dedicated transport."
            } finally {
                _isGeneratingPlan.value = false
            }
        }
    }

    fun saveCustomPlanAsInquiry() {
        val form = _customTourForm.value
        val planText = _generatedCustomPlan.value ?: return

        viewModelScope.launch {
            val estPkr = (form.durationDays * 25000L)
            val estUsd = (form.durationDays * 90)
            val entity = InquiryEntity(
                clientName = form.clientName.ifBlank { "Tour Planner Client" },
                contactInfo = form.phoneOrContact.ifBlank { "In-App Request" },
                serviceTitle = "${form.durationDays}-Day Tailored Tour (${form.travelGroupType})",
                category = "Custom Tour Package",
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
            *${com.example.model.OfficialContacts.APP_NAME} - Official Service Inquiry*
            📋 Service: ${inquiry.serviceTitle}
            👤 Client: ${inquiry.clientName}
            📞 Contact: ${inquiry.contactInfo}
            ✈️ Origin: ${inquiry.origin}
            📅 Dates: ${inquiry.dates}
            💰 Estimated: PKR ${"%,d".format(inquiry.estimatedCostPkr)} / $${inquiry.estimatedCostUsd}
            📝 Details: ${inquiry.details}
            
            🌐 Official Web: ${com.example.model.OfficialContacts.WEBPAGE_URL}
            📧 Email: ${com.example.model.OfficialContacts.OFFICIAL_EMAIL}
            📱 Hotline / WhatsApp / Telegram: ${com.example.model.OfficialContacts.OFFICIAL_PHONE}
            _Booked via ${com.example.model.OfficialContacts.APP_NAME} 24/7 AI Desk_
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "explore Islmbd Travel Inquiry: ${inquiry.serviceTitle}")
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(intent, "Share Inquiry via WhatsApp or Email"))
    }

    fun openDirectWhatsApp(context: Context, inquiry: InquiryEntity) {
        val phone = com.example.model.OfficialContacts.OFFICIAL_PHONE_INTL.removePrefix("+")
        val message = Uri.encode(
            "Hello ${com.example.model.OfficialContacts.APP_NAME}! I would like to confirm my inquiry for ${inquiry.serviceTitle} (${inquiry.dates}). Client: ${inquiry.clientName} (${inquiry.contactInfo}). Origin: ${inquiry.origin}"
        )
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$phone&text=$message")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            shareInquiryViaIntent(context, inquiry)
        }
    }

    fun openOfficialForum(context: Context, forum: com.example.model.SocialForum) {
        try {
            when (forum.actionType) {
                com.example.model.ActionType.CALL_PHONE -> {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(forum.actionUrl))
                    context.startActivity(intent)
                }
                com.example.model.ActionType.SEND_SMS -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(forum.actionUrl)).apply {
                        putExtra("sms_body", "Hello explore Islmbd! I need assistance with travel, hotel, or transport.")
                    }
                    context.startActivity(intent)
                }
                com.example.model.ActionType.SEND_EMAIL -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(forum.actionUrl))
                    context.startActivity(intent)
                }
                com.example.model.ActionType.OPEN_WHATSAPP,
                com.example.model.ActionType.OPEN_TELEGRAM,
                com.example.model.ActionType.OPEN_URL -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(forum.actionUrl))
                    context.startActivity(intent)
                }
            }
        } catch (e: Exception) {
            _userMessageToast.value = "Unable to open application. Contact: ${com.example.model.OfficialContacts.OFFICIAL_PHONE}"
        }
    }
}

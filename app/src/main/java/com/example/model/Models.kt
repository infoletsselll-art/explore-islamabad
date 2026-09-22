package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.graphics.vector.ImageVector

enum class VisitorPurpose(
    val title: String,
    val icon: ImageVector,
    val description: String,
    val keyDestinations: List<String>
) {
    HEALTH_MEDICAL(
        title = "Health & Medical Checkup",
        icon = Icons.Default.LocalHospital,
        description = "Specialist checkups, diagnostic tests, surgeries & hospital visits",
        keyDestinations = listOf("Shifa International (H-8)", "PIMS Hospital (G-8)", "Maroof International (F-10)", "Kulsum International (Blue Area)", "Quaid-e-Azam Hospital")
    ),
    LEGAL_COURTS(
        title = "Court & Official Affairs",
        icon = Icons.Default.Gavel,
        description = "Supreme Court, Islamabad High Court, Embassies / Diplomatic Enclave & Ministries",
        keyDestinations = listOf("Supreme Court of Pakistan", "Islamabad High Court (G-10)", "Diplomatic Enclave (Visa & Embassies)", "Federal Secretariat (Pak Sectt)", "District Courts (F-8/G-11)")
    ),
    EDUCATION_EXAMS(
        title = "Study, Tests & Interviews",
        icon = Icons.Default.School,
        description = "University visits, MDCAT/ECAT, CSS exams, convocations & admissions",
        keyDestinations = listOf("NUST (H-12)", "FAST-NUCES (H-9)", "Quaid-i-Azam University (QAU)", "COMSATS (Park Road)", "Air University & Bahria (E-9)")
    ),
    SHOPPING_LIFESTYLE(
        title = "Shopping & Bazaars",
        icon = Icons.Default.ShoppingBag,
        description = "Modern mega malls, fashion boutiques, electronics & traditional bazaars",
        keyDestinations = listOf("Centaurus Mall (F-8)", "Giga Mall (DHA II)", "Safa Gold Mall (F-7)", "Jinnah Super / Super Market (F-7/F-6)", "Aabpara & Raja Bazaar")
    ),
    TOURISM_EXPLORE(
        title = "Islamabad Sightseeing",
        icon = Icons.Default.LocationCity,
        description = "Iconic architecture, Margalla hills trails, heritage & scenic viewpoints",
        keyDestinations = listOf("Faisal Mosque", "Daman-e-Koh & Monal", "Pakistan Monument & Museum", "Rawal Lake & Lake View Park", "Saidpur Heritage Village")
    ),
    NORTHERN_GATEWAY(
        title = "Gateway to North",
        icon = Icons.Default.Terrain,
        description = "Transit departure to Murree, Galyat, Swat, Naran, Hunza & Skardu",
        keyDestinations = listOf("Murree Mall Road & Expressway", "Nathia Gali & Mushkpuri Peak", "Hazara Motorway to Naran/Kaghan", "Karakoram Highway to Hunza", "Skardu & Gilgit flights/transit")
    )
}

enum class BudgetTier(
    val title: String,
    val subtitle: String,
    val transportType: String,
    val stayType: String,
    val estimatedDailyPkr: String
) {
    BUDGET_SAVER(
        title = "Budget Friendly",
        subtitle = "Students, patients & economy travelers",
        transportType = "Metro Bus (Red/Orange/Blue) + EV Feeder Buses",
        stayType = "Affordable Guest Houses & Hostels (G & I Sectors)",
        estimatedDailyPkr = "PKR 1,500 – 3,500 / day"
    ),
    COMFORT_STANDARD(
        title = "Comfort & Family",
        subtitle = "inDrive/Yango cabs, 3-star hotels & family dining",
        transportType = "inDrive / Yango App Cabs & City Sedans",
        stayType = "3-Star Boutique Hotels & Centaurus Apartments",
        estimatedDailyPkr = "PKR 5,000 – 12,000 / day"
    ),
    EXECUTIVE_VIP(
        title = "Executive & VIP 4x4",
        subtitle = "Chauffeur sedan, 4x4 Prado/Fortuner & luxury hotels",
        transportType = "Dedicated Chauffeur Sedan / 4x4 Prado or Fortuner",
        stayType = "5-Star Hotels (Serena, Marriott, Ramada)",
        estimatedDailyPkr = "PKR 18,000 – 45,000 / day"
    )
}

enum class AgentType(
    val title: String,
    val subtitle: String,
    val greeting: String,
    val icon: ImageVector,
    val defaultPrompts: List<String>
) {
    ALL_PURPOSE_CONCIERGE(
        title = "Islamabad All-Purpose Concierge",
        subtitle = "Open door for all visitors: Health, Courts, Study & Tourism",
        greeting = "Khushamdeed! I am your 24/7 Islamabad Concierge. Whatever the purpose of your visit—medical appointments, Supreme/High Court hearings, university tests, shopping, or transit to the Northern Areas—I will guide your full round trip, arrange the best transport (Metro, inDrive, Yango, or private 4x4), and tailor everything to your exact budget. How can I assist your trip today?",
        icon = Icons.Default.AutoAwesome,
        defaultPrompts = listOf(
            "I'm coming for a medical checkup at Shifa Hospital, help me plan transport & stay",
            "I have a Supreme Court hearing tomorrow, what is the best route and nearby hotel?",
            "What is the cheapest way to travel around Islamabad via Metro and EV bus?",
            "Plan a 3-day customized family tour including shopping and Margalla Hills"
        )
    ),
    HEALTH_MEDICAL(
        title = "Medical & Hospital Navigator",
        subtitle = "PIMS, Shifa, Maroof, Kulsum & Patient Logistics",
        greeting = "Assalam-o-Alaikum. I specialize in medical visitor assistance across Islamabad. I can guide you on hospital locations (Shifa, PIMS, Maroof, Kulsum, Quaid-e-Azam), specialist OPD timings, patient-friendly guest houses nearby, and wheelchair/ambulance or cab transit.",
        icon = Icons.Default.LocalHospital,
        defaultPrompts = listOf(
            "Which guest houses are within walking distance of Shifa International H-8?",
            "How to reach PIMS Hospital from Rawalpindi Railway Station via Metro?",
            "Where to find 24/7 pharmacies and diagnostic labs in Blue Area?",
            "Best comfortable cab options for an elderly patient coming from Peshawar"
        )
    ),
    COURTS_OFFICIAL(
        title = "Courts & Diplomatic Navigator",
        subtitle = "Supreme Court, High Court, Diplomatic Enclave & Red Zone",
        greeting = "Welcome. Coming for legal or governmental affairs? I can guide you on access protocols for the Supreme Court of Pakistan, Islamabad High Court (G-10), District Courts, the Diplomatic Enclave (Visa interviews at US, UK, Schengen embassies), and Federal Ministries.",
        icon = Icons.Default.Gavel,
        defaultPrompts = listOf(
            "What are the security and entry rules for Islamabad High Court G-10?",
            "How to enter the Diplomatic Enclave for a visa interview (Shuttle Service info)?",
            "Best hotels near the Supreme Court of Pakistan and Constitutional Avenue",
            "How to reach Pakistan Secretariat from Islamabad Airport via Metro bus"
        )
    ),
    STUDY_ACADEMICS(
        title = "Student & University Advisor",
        subtitle = "NUST, FAST, QAU, COMSATS, Entry Tests & Hostels",
        greeting = "Hello student & academic visitors! Visiting Islamabad for NUST NET, FAST test, CSS academy, university admissions, or convocations? I can help you find student budget hostels, Metro/bus routes, and university campus directions.",
        icon = Icons.Default.School,
        defaultPrompts = listOf(
            "How to reach NUST H-12 from Faizabad Bus Terminal via Metro?",
            "Affordable student hostels near FAST H-9 and NUST",
            "Best study cafes and public libraries in Islamabad for CSS aspirants",
            "COMSATS Park Road transport options from Islamabad sectors"
        )
    ),
    TRANSIT_BUDGET(
        title = "Transport & Fare Optimizer",
        subtitle = "Metro Bus, inDrive, Yango, Local Cabs & 4x4 Jeeps",
        greeting = "Need transport? Whether your budget is PKR 50 for the Metro Bus or a luxury chauffeur-driven Prado, I compare all options: Metro Red/Orange/Blue/Green lines, inDrive & Yango fare estimates, Airport ISB pick/drop, and 4x4 mountain rentals.",
        icon = Icons.Default.DirectionsCar,
        defaultPrompts = listOf(
            "Compare fares: Metro Bus vs inDrive vs Yango from Airport to F-7",
            "Islamabad Metro Bus complete route map and ticket cost",
            "Book a chauffeur-driven sedan for a full 12-hour city visit",
            "What is the cost of renting a Prado or Fortuner with driver for Murree?"
        )
    ),
    NORTHERN_GATEWAY(
        title = "Northern Gateway Navigator",
        subtitle = "Murree, Galyat, Swat, Naran, Hunza & Skardu Routes",
        greeting = "Islamabad is the crown gateway to the Northern wonders of Pakistan! I organize round trips from Islamabad to Murree, Nathia Gali, Kaghan, Swat, Hunza, and Skardu with experienced mountain drivers, hotel bookings, and weather telemetry.",
        icon = Icons.Default.Terrain,
        defaultPrompts = listOf(
            "How to travel from Islamabad to Nathia Gali and Murree in 1 day?",
            "Road status of Hazara Motorway and Babusar Pass to Naran",
            "Best 5-day Northern tour package starting and ending in Islamabad",
            "Hiace van rental cost for 10 people to Hunza from Islamabad"
        )
    )
}

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val isFromUser: Boolean,
    val agentType: AgentType,
    val timestamp: Long = System.currentTimeMillis()
)

data class Destination(
    val id: String,
    val name: String,
    val category: String, // "Health & Hospitals", "Courts & Official", "Universities & Study", "Shopping & Malls", "Tourism & Heritage", "Northern Gateway"
    val purpose: VisitorPurpose,
    val subtitle: String,
    val description: String,
    val highlights: List<String>,
    val locationSector: String,
    val distanceFromCenter: String,
    val metroBusNear: String,
    val rating: Double,
    val budgetLevel: String
)

data class TravelService(
    val id: String,
    val title: String,
    val category: String, // "Transport & Transit", "Stays & Hotels", "Guided Packages", "Dining & Food"
    val subtitle: String,
    val description: String,
    val pricePkr: Long,
    val priceUsd: Int,
    val priceUnit: String, // "per trip", "per day", "per night", "per person"
    val budgetTier: BudgetTier,
    val includedFeatures: List<String>,
    val vehicleOrType: String,
    val isPopular: Boolean = false
)

data class TourRequirement(
    val clientName: String = "",
    val phoneOrContact: String = "",
    val origin: String = "Domestic (Karachi/Lahore/Peshawar/Quetta)",
    val visitorPurpose: VisitorPurpose = VisitorPurpose.TOURISM_EXPLORE,
    val budgetTier: BudgetTier = BudgetTier.COMFORT_STANDARD,
    val durationDays: Int = 3,
    val travelGroupType: String = "Family with Kids",
    val travelDates: String = "",
    val includePickAndDrop: Boolean = true,
    val includeHotel: Boolean = true,
    val includeDedicatedCar: Boolean = true,
    val preferredTransport: String = "inDrive / App Cab",
    val specialNotes: String = ""
)

data class VisitorTestimonial(
    val id: String,
    val name: String,
    val origin: String,
    val purpose: String,
    val category: String, // "Medical", "Legal", "Student", "Tourism"
    val avatarInitials: String,
    val rating: Int = 5,
    val review: String,
    val tripSummaryPills: List<String>
)

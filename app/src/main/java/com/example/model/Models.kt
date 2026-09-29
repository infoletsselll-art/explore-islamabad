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
    val badge: String,
    val icon: ImageVector,
    val description: String,
    val categoryMatch: String,
    val essentialThings: List<String>,
    val guidanceChecklist: List<String>,
    val keyDestinations: List<String>
) {
    HEALTH_MEDICAL(
        title = "Healthcare & Hospital Consultations",
        badge = "Healthcare",
        icon = Icons.Default.LocalHospital,
        description = "Tertiary hospital consultations, specialist diagnostic evaluations, and patient medical care.",
        categoryMatch = "Health & Hospitals",
        essentialThings = listOf(
            "Specialist Clinical OPDs",
            "Wheelchair & Patient Transit",
            "Nearby Guest Stays & Suites",
            "24/7 Diagnostic & Lab Facilities",
            "Direct Metro Hospital Access"
        ),
        guidanceChecklist = listOf(
            "Schedule clinical appointments prior to arrival in the capital",
            "Book verified guest houses situated along Pitras Bukhari Rd or Sector F-10",
            "Utilize the Red Line Metro Bus for direct, unhindered access to PIMS",
            "Arrange wheelchair-equipped inDrive or dedicated chauffeured transport"
        ),
        keyDestinations = listOf(
            "Shifa International Hospital (Sector H-8/4)",
            "PIMS Hospital & Children's Center (Sector G-8)",
            "Maroof International Hospital (Sector F-10)",
            "Kulsum International Hospital (Blue Area)",
            "Quaid-e-Azam International Hospital"
        )
    ),
    LEGAL_COURTS(
        title = "Judicial, Legal & Official Affairs",
        badge = "Judicial",
        icon = Icons.Default.Gavel,
        description = "Supreme Court proceedings, High Court hearings, Federal Ministries, and Embassy affairs.",
        categoryMatch = "Courts & Official",
        essentialThings = listOf(
            "Apex Court Cause Lists",
            "Law Chamber Facilities",
            "Red Zone Entry Clearance",
            "Diplomatic Shuttle Access",
            "Executive Hotel Stays"
        ),
        guidanceChecklist = listOf(
            "Bring original National Identity Card (CNIC) and observe formal court attire",
            "Arrive at Constitution Avenue prior to 08:30 AM to pass through security gates seamlessly",
            "Choose executive lodging in Blue Area, Serena Hotel, or Sector G-10 for swift access",
            "Consult the online Cause List to verify court bench scheduling and courtroom numbers"
        ),
        keyDestinations = listOf(
            "Supreme Court of Pakistan (Constitution Avenue)",
            "Islamabad High Court (Sector G-10)",
            "Diplomatic Enclave & Consular Missions",
            "Federal Secretariat (Pak Secretariat)",
            "District & Sessions Court Complex"
        )
    ),
    EDUCATION_EXAMS(
        title = "Higher Education & Academic Testing",
        badge = "Academics",
        icon = Icons.Default.School,
        description = "University entrance exams, NET/MDCAT, CSS evaluations, convocations, and campus admissions.",
        categoryMatch = "Universities & Study",
        essentialThings = listOf(
            "University Test Centers",
            "Vetted Student Hostels",
            "Metro Student Passes",
            "Quiet Libraries & Study Cafes",
            "Direct Campus Shuttles"
        ),
        guidanceChecklist = listOf(
            "Locate exam centers at NUST H-12, FAST H-9, or Quaid-i-Azam University in advance",
            "Reserve economical, safe student accommodation in Sectors H-13, G-9, or I-8",
            "Use the Orange and Green Metro feeder buses for rapid and budget-conscious transit",
            "Keep printed roll number slips, required identification, and stationery ready"
        ),
        keyDestinations = listOf(
            "NUST Main Campus (Sector H-12)",
            "FAST-NUCES Campus (Sector H-9)",
            "Quaid-i-Azam University (Margalla Foothills)",
            "COMSATS University (Park Road)",
            "Air University & Bahria University (Sector E-9)"
        )
    ),
    SHOPPING_LIFESTYLE(
        title = "Commercial Shopping & Lifestyle",
        badge = "Lifestyle",
        icon = Icons.Default.ShoppingBag,
        description = "Flagship luxury malls, designer fashion avenues, gourmet dining, and traditional artisan markets.",
        categoryMatch = "Shopping & Malls",
        essentialThings = listOf(
            "Flagship Multi-Level Malls",
            "Designer Boutiques & Apparel",
            "Traditional Handcraft Bazaars",
            "Gourmet Restaurant Terraces",
            "Currency Exchange Desks"
        ),
        guidanceChecklist = listOf(
            "Visit Centaurus Mall in F-8 and Giga Mall in DHA for premier international brands",
            "Explore Jinnah Super (F-7 Markaz) and Super Market (F-6) for chic boutiques and artisan crafts",
            "Browse Aabpara Bazaar for budget electronics, textiles, and authentic regional street fare",
            "Take advantage of modern digital payment options and certified exchange centers in Blue Area"
        ),
        keyDestinations = listOf(
            "The Centaurus Mall (Sector F-8/4)",
            "Giga Mall (DHA Phase II)",
            "Safa Gold Mall (Sector F-7 Markaz)",
            "Jinnah Super Market (Sector F-7)",
            "Beverly Centre & Super Market (Sector F-6)"
        )
    ),
    TOURISM_EXPLORE(
        title = "Scenic Tourism, Nature & Heritage",
        badge = "Heritage",
        icon = Icons.Default.LocationCity,
        description = "Iconic national landmarks, Margalla Hills scenic lookouts, heritage villages, and nature trails.",
        categoryMatch = "Tourism & Heritage",
        essentialThings = listOf(
            "Margalla Panoramic Lookouts",
            "Faisal Mosque Architecture",
            "National Cultural Museums",
            "Rawal Lake Boating Promenade",
            "Hiking Trails 3 & 5"
        ),
        guidanceChecklist = listOf(
            "Tour the landmark Faisal Mosque at sunset for inspiring architectural vistas",
            "Drive to Daman-e-Koh and Pir Sohawa for breathtaking views of the illuminated capital skyline",
            "Explore Pakistan Monument and Lok Virsa Museum to discover national heritage and crafts",
            "Embark on a refreshing morning hike along Margalla Trail 3 or Trail 5"
        ),
        keyDestinations = listOf(
            "Faisal Mosque (Margalla Sanctuary)",
            "Daman-e-Koh & Pir Sohawa Lookouts",
            "Pakistan Monument & National Museum",
            "Rawal Lake Promenade & Water Sports",
            "Saidpur Historic Village & Margalla Ridge"
        )
    ),
    NORTHERN_GATEWAY(
        title = "Northern Expeditions & Transit Hub",
        badge = "Expedition",
        icon = Icons.Default.Terrain,
        description = "Departure gateway for Murree, Galyat, Kaghan Valley, Swat, Hunza, and Gilgit-Baltistan.",
        categoryMatch = "Northern Gateway",
        essentialThings = listOf(
            "Inspected 4x4 Prado & Hiace Fleet",
            "Direct Mountain Flight Connections",
            "Real-Time Weather & Highway Alerts",
            "Veteran High-Altitude Drivers",
            "Transit Hotel Coordination"
        ),
        guidanceChecklist = listOf(
            "Review real-time conditions on Murree Expressway, Hazara Motorway, and Babusar Pass",
            "Reserve experienced mountain chauffeurs and vetted 4x4 vehicles for elevated routes",
            "Enjoy an overnight restorative stay in Islamabad prior to morning departures north",
            "Carry appropriate high-altitude clothing, power banks, and essential emergency provisions"
        ),
        keyDestinations = listOf(
            "Murree Expressway & Mall Road Route",
            "Nathia Gali & Ayubia National Reserve",
            "Hazara Motorway to Naran & Kaghan",
            "Karakoram Highway Departure to Hunza",
            "Skardu & Gilgit Mountain Flight Hub (ISB Airport)"
        )
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
        title = "Budget-Conscious Traveler",
        subtitle = "Ideal for students, medical patients & economy transit",
        transportType = "Metro Bus System (Red, Orange, Blue & Green) + CDA EV Feeders",
        stayType = "Quality-inspected guest lodgings & student hostels (Sectors G & I)",
        estimatedDailyPkr = "PKR 1,500 – 3,500 / day"
    ),
    COMFORT_STANDARD(
        title = "Comfort & Family Tier",
        subtitle = "Reliable inDrive/Yango mobility, 3-star boutique hotels & family suites",
        transportType = "On-demand inDrive & Yango app rides + comfortable air-conditioned sedans",
        stayType = "3-Star boutique hotels & premium serviced apartments in Sectors F & E",
        estimatedDailyPkr = "PKR 5,000 – 12,000 / day"
    ),
    EXECUTIVE_VIP(
        title = "Executive & VIP 4x4 Tier",
        subtitle = "Private chauffeur-driven sedans, luxury 4x4 SUVs & 5-star hospitality",
        transportType = "Dedicated chauffeur luxury sedan or 4x4 Prado / Toyota Fortuner",
        stayType = "5-Star landmark hotels (Serena Hotel, Marriott Islamabad, Ramada)",
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
        title = "Visit Islamabad AI Concierge",
        subtitle = "All-in-one guidance for healthcare, judicial, academic & leisure visits",
        greeting = "Welcome to Visit Islamabad — A gateway to visitors of all backgrounds and for all journeys. Whether you are traveling for hospital consultations, Supreme or High Court proceedings, university testing, retail shopping, or transit to the Northern Areas, I am here to orchestrate your entire itinerary. How may I assist your visit today?",
        icon = Icons.Default.AutoAwesome,
        defaultPrompts = listOf(
            "I am visiting for medical consultation at Shifa Hospital; please organize transit and lodging options.",
            "I have an upcoming Supreme Court hearing tomorrow morning; what is the optimal route and nearby hotel?",
            "What is the most economical and efficient way to navigate Islamabad via the Metro and EV bus network?",
            "Please curate a bespoke 3-day family itinerary including shopping, cultural monuments, and the Margalla Hills."
        )
    ),
    HEALTH_MEDICAL(
        title = "Medical & Patient Navigator",
        subtitle = "Specialist assistance for PIMS, Shifa, Maroof, Kulsum & patient transit",
        greeting = "Welcome. I specialize in healthcare and patient logistics throughout Islamabad. I can guide you through leading medical centers (Shifa International, PIMS, Maroof, Kulsum, and Quaid-e-Azam Hospital), outpatient consultation timings, nearby wheelchair-accessible accommodation, and reliable patient transit.",
        icon = Icons.Default.LocalHospital,
        defaultPrompts = listOf(
            "Which verified patient guest houses are situated within walking distance of Shifa International in Sector H-8?",
            "How do I travel smoothly from Rawalpindi Railway Station to PIMS Hospital via the Metro Bus?",
            "Where can I find 24/7 pharmacies, diagnostic imaging laboratories, and emergency care in Blue Area?",
            "What are the most comfortable ride-hailing options for an elderly patient arriving from out of town?"
        )
    ),
    COURTS_OFFICIAL(
        title = "Judicial & Consular Advisor",
        subtitle = "Supreme Court, High Court, Diplomatic Enclave & Red Zone clearance",
        greeting = "Greetings. Are you visiting Islamabad for legal proceedings or government consultations? I provide comprehensive guidance regarding security protocols, dress codes, cause lists, and transportation for the Supreme Court of Pakistan, Islamabad High Court in Sector G-10, District Courts, and the Diplomatic Enclave.",
        icon = Icons.Default.Gavel,
        defaultPrompts = listOf(
            "What are the official security clearance guidelines and entrance protocols for Islamabad High Court in G-10?",
            "How do I arrange the Diplomatic Shuttle Service at Avenue 3 Gate for a foreign embassy visa interview?",
            "Which executive hotels offer the fastest commute to the Supreme Court of Pakistan on Constitution Avenue?",
            "What is the best route to reach the Federal Secretariat from Islamabad International Airport?"
        )
    ),
    STUDY_ACADEMICS(
        title = "Academic & Admissions Counselor",
        subtitle = "NUST, FAST, QAU, COMSATS, entrance examinations & student hostels",
        greeting = "Welcome, students, researchers, and candidates! Whether you are appearing for NUST NET, FAST examinations, CSS academies, university admissions, or convocations, I am here to help you discover safe student accommodation, public transit routes, and campus amenities.",
        icon = Icons.Default.School,
        defaultPrompts = listOf(
            "How do I navigate from Faizabad Bus Interchange to NUST Sector H-12 via public transit?",
            "Where can I find safe, budget-friendly student hostels near FAST H-9 and NUST H-12?",
            "Which quiet study cafes and reference libraries are best suited for CSS examination candidates?",
            "What are the transport schedules for COMSATS University on Park Road from central sectors?"
        )
    ),
    TRANSIT_BUDGET(
        title = "Transit & Mobility Strategist",
        subtitle = "Islamabad Metro Bus, inDrive, Yango, airport transfers & 4x4 SUVs",
        greeting = "Greetings! Whether you prefer the PKR 50 convenience of the Metro Bus network or a chauffeur-driven luxury 4x4, I compare and optimize all mobility options across Islamabad: the Red, Orange, Blue, and Green Metro lines, inDrive & Yango fare estimates, airport transfers, and northern expedition vehicles.",
        icon = Icons.Default.DirectionsCar,
        defaultPrompts = listOf(
            "Compare fares and transit durations: Metro Bus vs inDrive vs Yango from Airport to Sector F-7.",
            "Can you provide the complete route map, operating hours, and ticket guidelines for the Islamabad Metro Bus?",
            "How do I arrange a full-day chauffeured sedan for executive corporate meetings across Blue Area?",
            "What is the approximate rate for renting an inspected Toyota Prado or Fortuner with a mountain driver?"
        )
    ),
    NORTHERN_GATEWAY(
        title = "Northern Expeditions Navigator",
        subtitle = "Murree, Galyat, Kaghan Valley, Swat, Hunza & Skardu departures",
        greeting = "Islamabad stands as the majestic gateway to the scenic northern peaks of Pakistan! I assist travelers with round-trip expeditions from Islamabad to Murree, Nathia Gali, Kaghan, Swat, Hunza, and Skardu, coordinating vetted mountain drivers, scenic lodging, and weather updates.",
        icon = Icons.Default.Terrain,
        defaultPrompts = listOf(
            "What is the optimal one-day round-trip itinerary from Islamabad to Nathia Gali and Murree?",
            "What is the current highway and weather status of Hazara Motorway and Babusar Pass towards Naran?",
            "Can you suggest a curated 5-day Northern Pakistan itinerary starting and finishing in Islamabad?",
            "What are the rental rates for a private Toyota Hiace commuter van for 10 passengers traveling to Hunza?"
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

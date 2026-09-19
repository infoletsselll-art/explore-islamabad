package com.example.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Cabin
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.ui.graphics.vector.ImageVector

enum class AgentType(
    val title: String,
    val subtitle: String,
    val greeting: String,
    val icon: ImageVector,
    val defaultPrompts: List<String>
) {
    CITY_GUIDE(
        title = "Islamabad City & Heritage",
        subtitle = "Margalla Hills, Faisal Mosque, Sectors & Culture",
        greeting = "Assalam-o-Alaikum! I am your Islamabad City Guide. I can help you explore the iconic Faisal Mosque, Pakistan Monument, scenic Margalla trails, Saidpur Village, and top dining spots across the sectors. What would you like to discover today?",
        icon = Icons.Default.LocationCity,
        defaultPrompts = listOf(
            "What are the top 5 must-visit sights in Islamabad?",
            "Best viewpoint for sunset: Daman-e-Koh or Monal?",
            "Which Margalla hiking trail is best for families?",
            "Top traditional Pakistani restaurants in Islamabad"
        )
    ),
    HILL_STATIONS(
        title = "Murree & Galyat Specialist",
        subtitle = "Mall Road, Nathia Gali, Ayubia & Pine Peaks",
        greeting = "Khushamdeed! I specialize in Murree and the serene Galyat hills (Nathia Gali, Ayubia, Mushkpuri, Dunga Gali). Just a 1 to 2 hour drive from Islamabad. Ask me about weather, road conditions, chairlifts, or hiking trails!",
        icon = Icons.Default.Terrain,
        defaultPrompts = listOf(
            "Plan a 2-day trip to Murree and Nathia Gali",
            "How long is the Mushkpuri Peak hike and difficulty?",
            "Ayubia pipeline track details and chairlift timings",
            "Murree Expressway driving tips and toll info"
        )
    ),
    NORTHERN_GATEWAY(
        title = "Northern Areas Navigator",
        subtitle = "Hunza, Skardu, Gilgit & Babusar Routes",
        greeting = "Greetings traveler! Islamabad is the premier international gateway to Northern Pakistan. I can plan your expeditions from Islamabad through Hazara Motorway and Karakoram Highway to Hunza, Skardu, Naran-Kaghan, and Fairy Meadows.",
        icon = Icons.Default.Navigation,
        defaultPrompts = listOf(
            "How to travel from Islamabad to Hunza Valley?",
            "Is Babusar Pass currently open for travel to Naran?",
            "Best 7-day Northern road trip starting from Islamabad",
            "Flights vs road travel from Islamabad to Skardu"
        )
    ),
    HOTEL_CONCIERGE(
        title = "Hotels & Stays Agent",
        subtitle = "Luxury Hotels, Boutique Lodges & Mountain Chalets",
        greeting = "Welcome! Looking for comfortable accommodations? I can suggest top-rated stays in Islamabad (Serena, Marriott, boutique F-sector guest houses) and pine chalets in Nathia Gali & Murree according to your budget.",
        icon = Icons.Default.Hotel,
        defaultPrompts = listOf(
            "Recommend luxury hotels in Islamabad for international tourists",
            "Best cozy pine resorts in Nathia Gali with scenic views",
            "Family-friendly executive guest houses in F-6 and F-7",
            "Average hotel rates in PKR and USD for a 3-night stay"
        )
    ),
    TRANSPORT_FLEET(
        title = "Pick & Drop / Car Rental",
        subtitle = "Airport ISB Transfers, 4x4 SUVs & Chauffeurs",
        greeting = "Need seamless transport? I arrange 24/7 Airport Pick & Drop from Islamabad International Airport (ISB), city chauffeur sedans, luxury 4x4 Prado/Fortuner for mountain roads, and Hiace group vans with vetted drivers.",
        icon = Icons.Default.DirectionsCar,
        defaultPrompts = listOf(
            "Cost for Airport ISB pick and drop to Islamabad sectors?",
            "Daily car rental with driver rates for city and Murree",
            "Which 4x4 vehicle is best for Galyat and Northern roads?",
            "Book a Toyota Hiace van for an 8-person family tour"
        )
    ),
    CAMPING_ADVENTURE(
        title = "Camping & Adventure Agent",
        subtitle = "Margalla Ridge Camping, Galyat Glamping & Gear",
        greeting = "Adventure awaits! I organize safe camping expeditions in Margalla Hills, Mushkpuri alpine ridge, Ayubia glamping pods, and supply camping gear (tents, sleeping bags, bonfires) with local certified wilderness guides.",
        icon = Icons.Default.Cabin,
        defaultPrompts = listOf(
            "Can tourists camp overnight on Margalla Hills?",
            "What camping gear is provided in your Galyat package?",
            "Safety tips and permits required for trekking in Galyat",
            "Best season for alpine camping near Islamabad"
        )
    ),
    CUSTOM_TOUR_PLANNER(
        title = "Tailored Tour Architect",
        subtitle = "Customized Itineraries & Bespoke Packages",
        greeting = "I am your personal travel architect! Tell me who is traveling (solo, honeymoon couple, or family with children), your arrival city or country, budget, and desired duration. I will tailor an all-inclusive custom package for you.",
        icon = Icons.Default.AutoAwesome,
        defaultPrompts = listOf(
            "Create a 4-day customized family itinerary from Karachi",
            "Plan a 5-day luxury honeymoon tour (Islamabad + Nathia Gali)",
            "Tailored corporate retreat package for 15 people",
            "International tourist 3-day express Islamabad & culture tour"
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
    val category: String, // "Islamabad City", "Murree & Galyat", "Northern Gateway"
    val subtitle: String,
    val description: String,
    val highlights: List<String>,
    val distanceFromIslamabad: String,
    val bestSeason: String,
    val rating: Double,
    val recommendedAgent: AgentType
)

data class TravelService(
    val id: String,
    val title: String,
    val category: String, // "Pick & Drop", "Hotels & Stays", "Tours & Sightseeing", "Camping & Adventure"
    val subtitle: String,
    val description: String,
    val pricePkr: Long,
    val priceUsd: Int,
    val priceUnit: String, // e.g. "per trip", "per day", "per night", "per person"
    val includedFeatures: List<String>,
    val vehicleOrType: String,
    val isPopular: Boolean = false
)

data class TourRequirement(
    val clientName: String = "",
    val phoneOrContact: String = "",
    val origin: String = "Domestic (Karachi/Lahore)",
    val durationDays: Int = 3,
    val travelGroupType: String = "Family with Kids",
    val travelDates: String = "",
    val includePickAndDrop: Boolean = true,
    val includeHotel: Boolean = true,
    val includeDedicatedCar: Boolean = true,
    val includeCamping: Boolean = false,
    val specialNotes: String = ""
)

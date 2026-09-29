package com.example.ai

import com.example.BuildConfig
import com.example.model.AgentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.model.ChatMessage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

enum class AiTaskComplexity {
    GENERAL_CHAT,       // Uses gemini-3.5-flash for general multi-turn conversation
    COMPLEX_ITINERARY,  // Uses gemini-3.1-pro-preview for complex reasoning and bespoke itineraries
    FAST_QUERY          // Uses gemini-3.1-flash-lite-preview for rapid response tasks
}

class GeminiAgentService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun consultAgent(
        agent: AgentType,
        userPrompt: String,
        complexity: AiTaskComplexity = AiTaskComplexity.GENERAL_CHAT
    ): String = withContext(Dispatchers.IO) {
        val syntheticHistory = listOf(
            ChatMessage(
                text = userPrompt,
                isFromUser = true,
                agentType = agent
            )
        )
        return@withContext consultAgentMultiTurn(agent, syntheticHistory, complexity)
    }

    suspend fun consultAgentMultiTurn(
        agent: AgentType,
        conversationHistory: List<ChatMessage>,
        complexity: AiTaskComplexity = AiTaskComplexity.GENERAL_CHAT
    ): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val responseText = callGeminiRestApiMultiTurn(apiKey, agent, conversationHistory, complexity)
                if (responseText.isNotBlank()) {
                    return@withContext responseText
                }
            } catch (e: Exception) {
                // Fall back to built-in specialized intelligence
            }
        }

        val lastUserMessage = conversationHistory.lastOrNull { it.isFromUser }?.text ?: ""
        return@withContext generateExpertFallback(agent, lastUserMessage)
    }

    private fun callGeminiRestApiMultiTurn(
        apiKey: String,
        agent: AgentType,
        conversationHistory: List<ChatMessage>,
        complexity: AiTaskComplexity
    ): String {
        val modelName = when (complexity) {
            AiTaskComplexity.COMPLEX_ITINERARY -> "gemini-3.1-pro-preview"
            AiTaskComplexity.GENERAL_CHAT -> "gemini-3.5-flash"
            AiTaskComplexity.FAST_QUERY -> "gemini-3.1-flash-lite-preview"
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"
        val systemInstruction = getAgentSystemInstruction(agent)

        // Filter conversation history to begin from the first user turn onward
        val firstUserIndex = conversationHistory.indexOfFirst { it.isFromUser }
        val relevantMessages = if (firstUserIndex >= 0) {
            conversationHistory.subList(firstUserIndex, conversationHistory.size)
        } else {
            conversationHistory
        }

        val contentsJson = JSONArray()
        for (msg in relevantMessages) {
            val turn = JSONObject().apply {
                put("role", if (msg.isFromUser) "user" else "model")
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", msg.text) })
                })
            }
            contentsJson.put(turn)
        }

        // If history had no user turns, guarantee at least one user turn
        if (contentsJson.length() == 0) {
            contentsJson.put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", "Hello, please assist me with my visit to Islamabad.") })
                })
            })
        }

        val rootJson = JSONObject().apply {
            put("systemInstruction", JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", systemInstruction) })
                })
            })
            put("contents", contentsJson)
            // Live Search Grounding for verified, real-world capital information
            put("tools", JSONArray().apply {
                put(JSONObject().apply {
                    put("googleSearch", JSONObject())
                })
            })
            put("generationConfig", JSONObject().apply {
                put("temperature", if (complexity == AiTaskComplexity.COMPLEX_ITINERARY) 0.4 else 0.7)
                put("topP", 0.95)
                put("maxOutputTokens", if (complexity == AiTaskComplexity.COMPLEX_ITINERARY) 2500 else 1500)
            })
        }

        val requestBody = rootJson.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw RuntimeException("Gemini API error ($modelName): ${response.code}")
            }
            val bodyString = response.body?.string() ?: ""
            val jsonResponse = JSONObject(bodyString)
            val candidates = jsonResponse.optJSONArray("candidates")
            if (candidates != null && candidates.length() > 0) {
                val firstCandidate = candidates.getJSONObject(0)
                val content = firstCandidate.optJSONObject("content")
                val parts = content?.optJSONArray("parts")
                if (parts != null && parts.length() > 0) {
                    val firstPart = parts.getJSONObject(0)
                    return firstPart.optString("text", "")
                }
            }
            return ""
        }
    }

    private fun getAgentSystemInstruction(agent: AgentType): String {
        return when (agent) {
            AgentType.ALL_PURPOSE_CONCIERGE -> """
                You are the Visit Islamabad Concierge — A gateway to the visitors of all types and for all purposes.
                Islamabad is the capital of Pakistan and the gateway to Northern Areas, welcoming visitors of all backgrounds and budgets:
                - Medical visitors (PIMS, Shifa International, Maroof, Kulsum, Quaid-e-Azam Hospital).
                - Legal & Official affairs (Supreme Court of Pakistan, Islamabad High Court G-10, Diplomatic Enclave embassies & visa interviews, Federal Ministries).
                - Academic & Students (NUST, FAST, QAU, COMSATS, Air Univ, entry tests, convocations).
                - Shoppers (Centaurus Mall, Giga Mall, Safa Gold, Jinnah Super F-7, Super Market F-6, Aabpara).
                - Tourists & Sightseeing (Faisal Mosque, Daman-e-Koh, Pakistan Monument, Lok Virsa, Saidpur Village, Margalla trails).
                - Northern Gateway (Murree, Nathia Gali, Kaghan, Swat, Hunza, Skardu).
                - Transport options for EVERY budget: Metro Bus (PKR 50), inDrive/Yango cabs, full-day chauffeurs, and 4x4 mountain rentals.
                Provide structured, warm, and helpful advice with transparent pricing in PKR and USD.
            """.trimIndent()

            AgentType.HEALTH_MEDICAL -> """
                You are the Islamabad Medical & Hospital Logistics AI Guide.
                You assist patients, families, and medical tourists visiting Islamabad:
                - Hospitals: Shifa International (H-8/4), PIMS (G-8/3), Maroof International (F-10 Markaz), Kulsum International (Blue Area), Quaid-e-Azam International (Peshawar Rd).
                - Patient logistics: Proximity guest houses, wheelchair accessibility, ambulance contacts (Rescue 1122), 24/7 pharmacies, diagnostic labs (Chughtai, IDC, Shifa Labs).
                - Transport: Metro Bus PIMS Station, inDrive/Yango medical drops, private chauffeur for patient checkup rounds.
            """.trimIndent()

            AgentType.COURTS_OFFICIAL -> """
                You are the Courts, Diplomatic & Official Affairs AI Navigator for Islamabad.
                You assist lawyers, litigants, applicants, and diplomats:
                - Supreme Court of Pakistan (Constitution Ave): Security protocol, formal dress code, gate access with CNIC.
                - Islamabad High Court (G-10): Courtrooms, filing branches, nearby legal chambers.
                - Diplomatic Enclave (G-5): Entry via Diplomatic Shuttle Service at Avenue 3, visa interview documents, embassy guidelines (US, UK, Schengen, Gulf).
                - Government Ministries / Secretariat: Red zone entry and transport links.
            """.trimIndent()

            AgentType.STUDY_ACADEMICS -> """
                You are the Student & Academic AI Advisor for Islamabad.
                You help students, parents, and researchers:
                - Universities: NUST (H-12), FAST-NUCES (H-9), Quaid-i-Azam University (QAU), COMSATS (Park Rd), Air University & Bahria (E-9).
                - Entry tests: NET, NU, NTS, MDCAT, CSS academy preparation.
                - Budget stays: Safe student hostels in H-9, H-12, I-8, G-8, and library access (National Library, Quaid Public Library).
                - Transit: Metro bus Orange Line to NUST, Red Line to FAST/I-9.
            """.trimIndent()

            AgentType.TRANSIT_BUDGET -> """
                You are the Transport & Fare Optimization AI Specialist.
                Islamabad offers transit for every wallet:
                - Budget: Metro Bus Red Line (Rawalpindi-Secretariat), Orange Line (Peshawar Morr to Airport ISB), Green & Blue feeder buses (PKR 50/trip).
                - App Cabs: inDrive and Yango fair fares (PKR 400 - 1,200 across sectors).
                - Chauffeur: Dedicated Toyota Corolla/Yaris sedan for 12 hours (PKR 6,500/day).
                - 4x4 Mountain Fleet: Toyota Prado, Fortuner & Hiace Grand Cabin 14-seater for Murree, Nathia Gali, Kaghan, and Hunza.
                - Airport Transfers: 24/7 Islamabad International Airport (ISB) pick & drop.
            """.trimIndent()

            AgentType.NORTHERN_GATEWAY -> """
                You are the Northern Gateway Navigator.
                Islamabad is the premier jumping-off point to the wonders of northern Pakistan:
                - Murree (50 mins via Murree Expressway) & Galyat (Nathia Gali, Ayubia Pipeline Track, Mushkpuri Peak).
                - Hazara Motorway (M-15): Route to Abbottabad, Mansehra, Naran-Kaghan & Babusar Pass.
                - Swat Expressway: Gateway to Kalam, Malam Jabba, and Mingora.
                - Karakoram Highway & Flights: Hunza, Skardu, Gilgit, Fairy Meadows.
                Advise on road status, 4x4 vehicle requirements, weather, and scenic stops.
            """.trimIndent()
        }
    }

    private fun generateExpertFallback(agent: AgentType, prompt: String): String {
        val lower = prompt.lowercase()
        return when (agent) {
            AgentType.ALL_PURPOSE_CONCIERGE -> {
                if (lower.contains("medical") || lower.contains("hospital") || lower.contains("doctor") || lower.contains("checkup")) {
                    """
                    🏥 **Medical Visit to Islamabad (Comprehensive Plan):**
                    
                    • **Top Hospitals:**
                      - *Shifa International (Sector H-8/4):* Multi-specialty tertiary care & organ transplants.
                      - *PIMS Hospital (Sector G-8/3):* Premier government institute with Children's Hospital & Burn Center.
                      - *Maroof International (Sector F-10 Markaz):* Boutique executive hospital near high-end cafes & guest houses.
                      - *Kulsum International (Blue Area):* Leading cardiac center directly on Jinnah Avenue.
                    
                    • **Budget Accommodation:** Safe, quiet guest houses in G-8, H-8, and I-8 (PKR 2,800 – 4,500/night).
                    • **Transit:** Red Line Metro directly stops at PIMS Station; inDrive/Yango or dedicated chauffeur car (PKR 6,500/day) for hassle-free patient transit.
                    """.trimIndent()
                } else if (lower.contains("court") || lower.contains("lawyer") || lower.contains("hearing") || lower.contains("supreme") || lower.contains("high court")) {
                    """
                    ⚖️ **Legal & Court Visits in Islamabad:**
                    
                    • **Supreme Court of Pakistan:** Constitution Avenue, Red Zone.
                      - *Access:* Enter through Gate 1 with original CNIC and case causality list or lawyer card. Formal court dress code required.
                      - *Nearest Transit:* Pak Secretariat Metro Station (Red Line) or short cab ride from Serena / Marriott.
                    
                    • **Islamabad High Court (IHC):** Sector G-10 modern judicial complex.
                      - *Access:* Srinagar Highway access. Law chambers and affordable guest houses located in G-10 and G-11 Markaz.
                    """.trimIndent()
                } else {
                    """
                    🌟 **Welcome to Visit Islamabad!**
                    *A gateway to the visitors of all types and for all purposes.*
                    
                    1. **For Patients & Healthcare:** 24/7 assistance for Shifa, PIMS, Maroof, and patient guest houses.
                    2. **For Legal & Official Affairs:** Supreme Court, High Court (G-10), and Diplomatic Enclave (Visa interviews).
                    3. **For Students & Academics:** NUST, FAST, QAU, COMSATS, admission test routes and student hostels.
                    4. **For Shoppers & Families:** Centaurus Mall, Giga Mall, Jinnah Super F-7, and Melody Food Street.
                    5. **For Tourists & Northern Travelers:** Faisal Mosque, Margalla viewpoints, Murree, Nathia Gali, Kaghan & Hunza.
                    
                    🚗 **Transport for Every Wallet:** Metro Bus (PKR 50), inDrive/Yango cabs, or luxury 4x4 Prado rentals.
                    """.trimIndent()
                }
            }

            AgentType.HEALTH_MEDICAL -> {
                """
                🏥 **Medical Guide for Islamabad Visitors:**
                
                • **Major Hospitals:**
                  1. *Shifa International (H-8/4):* Ph: +92 51 8463000. JCI-accredited tertiary care, cardiology, oncology, liver & kidney transplant.
                  2. *PIMS Hospital (G-8/3):* Ph: +92 51 9261170. Large public hospital with Red Line Metro station directly outside.
                  3. *Maroof International (F-10 Markaz):* Modern private care, executive checkup packages.
                  4. *Kulsum International (Blue Area):* Jinnah Avenue, specialized cardiology and general surgery.
                
                • **Patient Stays:** Numerous vetted guest houses operate in H-8, G-8, and I-8 with elevator access, wheelchair assistance, and homestyle food.
                • **Transit:** Dedicated chauffeur sedan (PKR 6,500/day) allows family to attend hospital appointments without parking hassle.
                """.trimIndent()
            }

            AgentType.COURTS_OFFICIAL -> {
                """
                ⚖️ **Courts, Embassies & Red Zone Navigator:**
                
                • **Supreme Court of Pakistan:**
                  - Located on Constitution Avenue.
                  - Security check at Gate 1: Bring 2 photocopies of CNIC and court causality list.
                  - Bags and phones must be deposited at security counter unless authorized lawyer.
                
                • **Islamabad High Court (IHC):**
                  - Sector G-10 judicial complex.
                  - Multiple parking bays, lawyer bar association, and biometric case verification booths.
                
                • **Diplomatic Enclave (Embassies & Visa Interviews):**
                  - Entry for applicants is via **Diplomatic Shuttle Service** at Avenue 3 (Sector G-5).
                  - Purchase ticket at shuttle terminal. Arrive 45 minutes before appointment.
                """.trimIndent()
            }

            AgentType.STUDY_ACADEMICS -> {
                """
                🎓 **Student & Academic Gateway:**
                
                • **NUST (H-12):** Directly connected via Orange Line Metro Bus (NUST Station) on Srinagar Highway.
                • **FAST-NUCES (H-9):** Walking distance from Potohar Metro Bus Station on Red Line.
                • **Quaid-i-Azam University (QAU):** Near Lake View Park and Margalla foothills; reachable via local bus or cab.
                • **COMSATS (Park Road):** Feeder buses from Tarlai / Chak Shahzad.
                
                💡 **Student Budget Tips:**
                - Use the Metro Bus Card (PKR 50/ride) to travel between Rawalpindi and all Islamabad universities.
                - Safe student hostels available in H-9, I-8, and G-9 starting from PKR 12,000/month or PKR 2,000/night for test takers.
                """.trimIndent()
            }

            AgentType.TRANSIT_BUDGET -> {
                """
                🚗 **Islamabad Transport for Every Budget:**
                
                • **Budget Transit (PKR 50 / ride):**
                  - *Metro Bus Red Line:* Saddar Rawalpindi ↔ Faizabad ↔ Blue Area ↔ Pak Secretariat.
                  - *Metro Bus Orange Line:* Peshawar Morr ↔ NUST ↔ Islamabad Airport (ISB).
                  - *Green & Blue Lines:* Bhara Kahu and Kural Chowk feeder routes.
                  - *EV Feeder Buses:* Cover F-6, F-7, G-7, PIMS, and major universities.
                
                • **App Cabs (inDrive & Yango):**
                  - Ride Mini: PKR 350 – 600
                  - Ride AC / Sedan: PKR 600 – 1,200
                  - Motorbike Ride: PKR 150 – 300
                
                • **Chauffeur City Car (Full Day 12 hrs):**
                  - Toyota Corolla / Yaris with driver: PKR 6,500 / day
                
                • **4x4 Luxury & Mountain Fleet:**
                  - Toyota Prado TX / Fortuner: PKR 18,000 / day (Murree, Galyat, Kaghan, Hunza)
                  - Toyota Hiace Grand Cabin (14-seater): PKR 16,000 / day
                """.trimIndent()
            }

            AgentType.NORTHERN_GATEWAY -> {
                """
                🏔️ **Northern Gateway Navigator (Murree, Galyat & Beyond):**
                
                • **Murree (50 mins):** 4-lane Murree Expressway from Bhara Kahu bypass. Visit Mall Road, Pindi Point, Patriata cable car.
                • **Nathia Gali (1.5 - 2 hrs):** Alpine pine paradise at 8,200 ft. Must-do: Mushkpuri Peak hike and Ayubia Pipeline Track.
                • **Hazara Motorway (M-15):** Smooth motorway to Abbottabad, Mansehra, and Naran-Kaghan.
                • **Karakoram Highway (KKH):** Scenic world-famous highway to Chilas, Gilgit, Hunza, and Khunjerab Pass.
                
                🚗 We provide 4x4 Prado and Hiace vans with experienced mountain chauffeurs for safe alpine driving!
                """.trimIndent()
            }
        }
    }
}

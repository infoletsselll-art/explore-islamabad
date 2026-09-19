package com.example.ai

import com.example.BuildConfig
import com.example.model.AgentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class GeminiAgentService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun consultAgent(agent: AgentType, userPrompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Throwable) {
            ""
        }

        if (apiKey.isNotBlank() && apiKey != "MY_GEMINI_API_KEY") {
            try {
                val responseText = callGeminiRestApi(apiKey, agent, userPrompt)
                if (responseText.isNotBlank()) {
                    return@withContext responseText
                }
            } catch (e: Exception) {
                // Fall back to built-in specialized intelligence
            }
        }

        // High-fidelity domain expert fallback
        return@withContext generateExpertFallback(agent, userPrompt)
    }

    private fun callGeminiRestApi(apiKey: String, agent: AgentType, userPrompt: String): String {
        val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

        val systemInstruction = getAgentSystemInstruction(agent)

        val rootJson = JSONObject().apply {
            put("systemInstruction", JSONObject().apply {
                put("parts", JSONArray().apply {
                    put(JSONObject().apply { put("text", systemInstruction) })
                })
            })
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", userPrompt) })
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.7)
                put("topP", 0.95)
                put("maxOutputTokens", 1500)
            })
        }

        val requestBody = rootJson.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw RuntimeException("Gemini API returned code: ${response.code}")
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
            AgentType.CITY_GUIDE -> """
                You are the Islamabad City & Heritage AI Agent. Islamabad is Pakistan's lush green, master-planned capital nestled against the Margalla Hills.
                You have deep knowledge of:
                - Landmarks: Faisal Mosque (dress etiquette, timings), Pakistan Monument & Shakarparian, Lok Virsa Museum, Saidpur Heritage Village, Rawal Lake, Daman-e-Koh and Pir Sohawa (Monal / Highland), Lake View Park.
                - Sectors: Sector grid layout (E, F, G, H, I sectors, Blue Area, Diplomatic Enclave).
                - Dining & Food: F-6 & F-7 Super Market, Beverly Centre, Melody Food Park, authentic Karahi, Kabuli Pulao, continental cafes.
                - Margalla Hills Trails: Trail 3 (challenging), Trail 5 (family friendly, streams), Trail 6.
                Be courteous, welcoming to both domestic and international tourists, highlight costs in PKR and USD, safety tips, and travel times.
            """.trimIndent()

            AgentType.HILL_STATIONS -> """
                You are the Murree & Galyat Hill Stations AI Specialist. 
                Murree (7,500 ft) is a 1-hour drive from Islamabad via the modern Murree Expressway.
                Galyat includes Nathia Gali (8,200 ft), Ayubia, Dunga Gali, Changla Gali, and Khanspur.
                You specialize in:
                - Murree: Mall Road, Pindi Point, Kashmir Point, Patriata (New Murree) Chairlift and Cable Car.
                - Galyat: Nathia Gali Governor House, Saint Matthew's Church, famous spicy Patakha chicken, Miranjani Peak trek (9,776 ft), Mushkpuri Peak trek (9,200 ft), Ayubia Pipeline Track (4km flat nature walk) & Ayubia Chairlift.
                - Weather & Driving: Fog, winter snow chains, landslides, Murree Expressway toll points, scenic rest stops.
                Provide clear driving times, packing advice (warm clothes even in summer evenings), and accommodation recommendations.
            """.trimIndent()

            AgentType.NORTHERN_GATEWAY -> """
                You are the Northern Areas Gateway AI Navigator. Islamabad is the starting hub for travelers heading to Northern Pakistan.
                You guide tourists on:
                - Routes from Islamabad: Hazara Motorway (M-15), N-35 Karakoram Highway (KKH), Babusar Pass (open summer June-Sept), Jaglot-Skardu Road.
                - Destinations: Hunza Valley (Karimabad, Baltit/Altit Forts, Attabad Lake, Passu Cones), Skardu & Deosai Plains, Naran-Kaghan, Gilgit, Fairy Meadows.
                - Logistics: Islamabad Airport (ISB) flights to Gilgit and Skardu vs scenic road trips, private Prado 4x4 rentals, Hiace vans, fuel costs, acclimatization tips.
            """.trimIndent()

            AgentType.HOTEL_CONCIERGE -> """
                You are the Hotels, Resorts & Stays AI Agent for Islamabad, Murree, and Galyat.
                You recommend and arrange:
                - 5-Star Luxury: Islamabad Serena Hotel, Islamabad Marriott, Islamabad Serena Sheesh Mahal.
                - Boutique Guest Houses: Executive villas in F-6, F-7, F-8, E-7 diplomatic zones.
                - Hill Station Lodges: Wooden pine chalets in Nathia Gali, Murree luxury suites, Ayubia mountain cottages.
                - Budget options: Clean, vetted family hotels with rates in PKR and USD.
                Provide room types, amenities (generator backup, heaters in winter, mountain views), and reservation advice.
            """.trimIndent()

            AgentType.TRANSPORT_FLEET -> """
                You are the Transport & Pick-and-Drop AI Fleet Specialist.
                You provide transparent pricing, vehicle specs, and route coordination:
                - Airport Pick & Drop: 24/7 service from Islamabad International Airport (ISB) to any Islamabad sector, Rawalpindi, or direct to Murree/Nathia Gali.
                - Vehicle Options: Clean sedans (Toyota Yaris/Corolla), 4x4 Mountain SUVs (Toyota Prado, Fortuner), 12-seater vans (Toyota Hiace Grand Cabin), 22-seater Coaster.
                - All vehicles include professional vetted drivers, fuel options, toll inclusion, and luggage support for overseas and domestic tourists.
            """.trimIndent()

            AgentType.CAMPING_ADVENTURE -> """
                You are the Camping & Adventure AI Agent.
                You guide and organize:
                - Margalla Hills ridge camping, rock climbing, and guided morning hikes.
                - Mushkpuri and Miranjani alpine meadow camping with campfire, chicken BBQ, and stargazing.
                - Ayubia glamping pods and forest retreats.
                - Complete gear rentals (waterproof tents, down sleeping bags, portable stoves) delivered to hotels in Islamabad.
                - Wilderness safety, environmental Leave-No-Trace principles, and emergency contacts (Rescue 1122, Tourist Police).
            """.trimIndent()

            AgentType.CUSTOM_TOUR_PLANNER -> """
                You are the Tailored Tour Concierge AI Architect.
                You design bespoke tour itineraries according to client requirements:
                - Domestic tourists (arriving from Karachi, Lahore, Multan, Peshawar) or Overseas tourists (UK, USA, Canada, UAE, Europe).
                - Traveler types: Family with young children, honeymoon couples, solo explorers, corporate groups.
                - Combine city heritage, hill station retreats, private transport, hotel reservations, and camping.
                Output structured day-by-day itineraries with morning, afternoon, and evening activities, estimated budgets in PKR and USD, and booking steps.
            """.trimIndent()
        }
    }

    private fun generateExpertFallback(agent: AgentType, prompt: String): String {
        val lower = prompt.lowercase()
        return when (agent) {
            AgentType.CITY_GUIDE -> {
                if (lower.contains("top") || lower.contains("must") || lower.contains("sights") || lower.contains("5")) {
                    """
                    🌟 **Top 5 Must-Visit Sights in Islamabad:**

                    1. **Faisal Mosque:** Pakistan's national symbol at the foot of Margalla Hills. World's largest tent-shaped mosque designed by Vedat Dalokay. Best visited near sunset when the white marble gleams against the green mountains.
                    2. **Daman-e-Koh & Pir Sohawa:** Perched high in Margalla Hills, providing bird's-eye views of the capital grid, Faisal Mosque, and Rawal Lake. Enjoy dinner at Monal or Highland Resort.
                    3. **Pakistan Monument & Lok Virsa:** Shakarparian Hills. The granite petal architecture symbolizes Pakistani unity, while the adjacent Lok Virsa Museum showcases Pakistan's living craft heritage.
                    4. **Saidpur Heritage Village:** A 500-year-old preserved stone hamlet with ancient temples, cobblestones, and premier traditional rooftop dining.
                    5. **Margalla Trails (Trail 3 & 5):** Pristine hiking trails with natural mountain streams and forest shade, ideal for morning nature lovers.

                    💡 *Local Tip:* Modest attire is appreciated at Faisal Mosque. Entry to the courtyard is free.
                    """.trimIndent()
                } else if (lower.contains("restaurant") || lower.contains("food") || lower.contains("eat")) {
                    """
                    🍽️ **Islamabad Culinary & Dining Guide:**

                    - **Traditional Pakistani & Karahi:** 
                      • *Saidpur Village* (Des Pardes / Khiva) - Karahi with live sitar music under mountain cliffs.
                      • *Melody Food Park (G-6)* - Famous crispy fish, Seekh kebabs, and authentic street food.
                      • *Savour Foods (Blue Area)* - Famous Pulao-Kabab with Shami and roast chicken (iconic local staple).
                    - **Mountain Scenic Dining:**
                      • *Monal & La Montana (Pir Sohawa)* - Stunning 3,500 ft elevation terrace with BBQ and Pakistani platters overlooking the illuminated city lights.
                    - **Trendy Cafes & Continental (Sectors F-6 & F-7):**
                      • *Kohsar Market (F-6)* - Street 1 Cafe, Butler’s Chocolate Cafe, artisanal coffee.
                      • *Beverly Centre (Blue Area)* - High-end steakhouses, Italian, and specialty cafes.
                    """.trimIndent()
                } else {
                    """
                    🏔️ **Islamabad Exploration Insights:**

                    Islamabad is one of the world's most scenic planned capitals, built on an orderly grid system (Sectors E, F, G, H, I) flanked by the lush Margalla Hills National Park.

                    - **Best Times to Visit:** October through April offers crisp sunny days and pleasant evenings.
                    - **Getting Around:** Clean wide boulevards make driving effortless. You can book our dedicated chauffeur car or 24/7 Airport Pick & Drop for stress-free sightseeing.
                    - **Safety & Peace:** Islamabad is the safest city in the region, patrolled by the dedicated Islamabad Tourist Police.

                    Ask me anytime about specific sectors, hiking trails, dining spots, or museum timings!
                    """.trimIndent()
                }
            }

            AgentType.HILL_STATIONS -> {
                if (lower.contains("mushkpuri") || lower.contains("hike") || lower.contains("trek")) {
                    """
                    🥾 **Mushkpuri Peak (9,200 ft) Trek Guide:**

                    - **Starting Points:** Either from *Dunga Gali* (steeper, pine path, 2.5 hours) or *Nathia Gali* near Shangrila hotel (gentler, lush meadows, 3 hours).
                    - **Difficulty:** Moderate. Safe and very well-traveled by families and tourists.
                    - **What to Expect:** A magical trail through dense sub-alpine conifer forests opening up to a sweeping mountaintop meadow with yellow wildflowers in summer and powdery snow in winter.
                    - **Gear needed:** Comfortable hiking shoes, water bottle, light windbreaker (temperature is 10°C to 15°C cooler than Islamabad).
                    - **Scenic Highlights:** 360-degree views of Kashmir mountains, Jhelum river valley, and Circle Bakote.
                    """.trimIndent()
                } else if (lower.contains("2-day") || lower.contains("plan") || lower.contains("itinerary")) {
                    """
                    🌲 **Recommended 2-Day Murree & Galyat Itinerary:**

                    **Day 1: Murree & Patriata Adventure**
                    - 08:30 AM: Depart Islamabad via Murree Expressway (scenic 1-hour drive).
                    - 10:00 AM: Patriata New Murree Chairlift & Cable car ride above cloud level.
                    - 01:30 PM: Lunch in Murree & stroll along historic Mall Road and Kashmir Point.
                    - 04:00 PM: Scenic drive along the pine road towards Nathia Gali (8,200 ft).
                    - 07:30 PM: Check into your Pine Chalet; evening bonfire and famous Patakha roast chicken.

                    **Day 2: Nathia Gali & Ayubia Nature Walk**
                    - 08:30 AM: Fresh hot breakfast overlooking misty pine valleys.
                    - 09:30 AM: Walk the historic Ayubia Pipeline Track (4 km easy cliffside walk).
                    - 12:30 PM: Ride the Ayubia Chairlift or visit Saint Matthew's Church.
                    - 03:00 PM: Scenic return drive descending back to Islamabad.
                    """.trimIndent()
                } else {
                    """
                    ⛰️ **Murree & Galyat Hill Station Advice:**

                    - **Distance from Islamabad:** 
                      • Murree: 55 km (~1 hour via Expressway)
                      • Nathia Gali: 85 km (~2 hours scenic winding road)
                      • Ayubia: 78 km (~1 hr 45 min)
                    - **Weather:** Always 8°C to 12°C cooler than the capital. Even in July, you will need a light sweater in the evenings!
                    - **Road Conditions:** Murree Expressway is fully carpeted and dual-carriageway. Galyat roads are well-maintained with stunning valley lookouts.
                    - **Service Note:** We offer 4x4 Prado SUVs, Toyota Hiace family vans, and cozy pine chalet reservations for smooth travel.
                    """.trimIndent()
                }
            }

            AgentType.NORTHERN_GATEWAY -> {
                """
                🏔️ **Islamabad to Northern Pakistan Gateway Guide:**

                Islamabad is the premier launching pad for expeditions into Gilgit-Baltistan and the Karakoram:

                - **Hunza Valley (approx. 550 km):**
                  • *By Air:* 45-minute scenic flight from Islamabad (ISB) to Gilgit Airport, followed by a 2-hour drive on the Karakoram Highway to Karimabad.
                  • *By Road:* Via Hazara Motorway (M-15) through Abbottabad, Chilas, and KKH (12-14 hours scenic drive or 2-day relaxed journey via Naran/Babusar).
                - **Skardu (Gateway to K2 & Deosai):**
                  • Direct daily Boeing 737 / Airbus A320 flights operate between Islamabad (ISB) and Skardu Airport, offering close-up views of Nanga Parbat!
                - **Naran-Kaghan & Babusar Pass (13,700 ft):**
                  • Open from mid-June to late October. 5-hour drive from Islamabad to Naran, crossing over Babusar into Gilgit-Baltistan.

                Let our transport fleet arrange your customized 4x4 Prado with mountain-certified drivers!
                """.trimIndent()
            }

            AgentType.HOTEL_CONCIERGE -> {
                """
                🏨 **Curated Stays & Accommodation Options:**

                **1. Islamabad 5-Star Luxury:**
                - *Islamabad Serena Hotel:* Surrounded by 14 acres of lush gardens in Sector G-5. World-class Maisha Spa, heated pool, 6 international restaurants. (PKR ~75,000 / $270 per night)
                - *Islamabad Marriott Hotel:* Located at the foot of Margalla Hills in F-5, close to the diplomatic enclave and presidency.

                **2. Executive Boutique Guest Houses (F-6, F-7, E-7):**
                - Private residences in quiet tree-lined avenues, walking distance to Super Market cafes. High privacy, home-cooked breakfasts, dedicated generator backup. (PKR 15,000 - 22,000 / $55 - $80 per night)

                **3. Galyat Mountain Chalets (Nathia Gali & Murree):**
                - Alpine wooden chalets surrounded by century-old pine trees, log fireplaces, and balcony views over Kashmir ranges. (PKR 20,000 - 35,000 / $75 - $125 per night)

                You can submit an instant inquiry directly from the Services tab to receive guaranteed rates!
                """.trimIndent()
            }

            AgentType.TRANSPORT_FLEET -> {
                """
                🚗 **Islamabad Fleet & Pick-and-Drop Rates:**

                - **Islamabad Airport (ISB) 24/7 Pick & Drop:**
                  • Toyota Yaris / Corolla Sedan: PKR 5,500 ($20) flat rate to any sector in Islamabad.
                  • Toyota Prado 4x4 Luxury: PKR 14,000 ($50).
                  • Toyota Hiace Grand Cabin (12 seats): PKR 12,000 ($45).
                  *Includes flight tracking, driver placard at arrivals gate, tolls & airport parking.*

                - **Full-Day City & Margalla Hills Chauffeur (12 Hours):**
                  • AC Sedan with vetted driver: PKR 9,500 ($35) / day + fuel.
                  • Toyota Prado / Fortuner 4x4: PKR 22,000 ($80) / day.

                - **Murree & Nathia Gali Roundtrip Transit:**
                  • Dedicated driver with mountain experience, heated/AC vehicle, safe driving on hairpin bends.

                Book your vehicle now through the Services tab for immediate dispatch confirmation!
                """.trimIndent()
            }

            AgentType.CAMPING_ADVENTURE -> {
                """
                ⛺ **Camping & Adventure Services:**

                - **Mushkpuri Summit Meadow Camping (9,200 ft):**
                  • Includes all-weather waterproof dome tents, -10°C rated thermal sleeping bags, foam sleeping mats, live campfire, fresh chicken tikka barbecue dinner, and sunrise breakfast with mountain chai.
                  • Package: PKR 14,000 ($50) per person.
                - **Margalla Ridge Trekking & Day Camping:**
                  • Guided treks along Trail 3, 5, or Monal ridge with certified local wilderness guides.
                - **Camping Gear Rental Delivery:**
                  • 2-person / 4-person Coleman tents, butane gas stoves, trekking poles delivered directly to your hotel in Islamabad.
                  • Rental: PKR 4,500 ($16) per day kit.

                Safety is paramount: All tours are equipped with satellite check-ins, first-aid kits, and direct linkage to Rescue 1122.
                """.trimIndent()
            }

            AgentType.CUSTOM_TOUR_PLANNER -> {
                """
                ✨ **Customized Tour Planning Engine:**

                We create tailored packages based on your exact arrival origin, duration, and preferences:

                - **For Overseas Tourists (UK, USA, Europe, Gulf):**
                  VIP Airport ISB meet-and-greet, luxury Serena or boutique villa accommodation, licensed English-speaking guide, secure transport, and curated cultural heritage stops.
                - **For Domestic Travelers (Karachi, Lahore, Faisalabad):**
                  Family-friendly multi-day packages combining Islamabad shopping, Margalla scenic dining, Patriata chairlift, and peaceful Nathia Gali chalets.
                - **Custom Add-ons:**
                  • Private BBQ bonfire nights
                  • Airport luggage transfers
                  • Photographic escorts for scenic viewpoints

                Use our **Custom Planner** tab to generate a bespoke day-by-day quotation within seconds!
                """.trimIndent()
            }
        }
    }
}

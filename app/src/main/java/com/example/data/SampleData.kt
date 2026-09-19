package com.example.data

import com.example.model.AgentType
import com.example.model.Destination
import com.example.model.TravelService

object SampleData {

    val destinations = listOf(
        // Islamabad City
        Destination(
            id = "faisal_mosque",
            name = "Faisal Mosque",
            category = "Islamabad City",
            subtitle = "World's iconic modern Islamic architecture",
            description = "Nestled dramatically at the foot of Margalla Hills, the Faisal Mosque is Pakistan's national mosque and an architectural marvel designed by Turkish architect Vedat Dalokay. It features eight-sided concrete shells inspired by a Bedouin tent with four 260-ft minarets.",
            highlights = listOf(
                "Striking white tent design & four towering minarets",
                "Spectacular mountain backdrop of Margalla National Park",
                "Spacious marble courtyard overlooking Islamabad",
                "Respectful dress code: head coverings & shoe deposit"
            ),
            distanceFromIslamabad = "Central (Sector E-7/Margalla)",
            bestSeason = "All year round (Sunset & Evening)",
            rating = 4.9,
            recommendedAgent = AgentType.CITY_GUIDE
        ),
        Destination(
            id = "daman_e_koh",
            name = "Daman-e-Koh & Pir Sohawa",
            category = "Islamabad City",
            subtitle = "Panoramic hilltop terraces & dining",
            description = "Daman-e-Koh is a viewing point and hilltop garden in the middle of Margalla Hills, offering breathtaking aerial views over the planned sectors of Islamabad, Faisal Mosque, and Rawal Lake. Continuing further up leads to Pir Sohawa and the famed mountain restaurants.",
            highlights = listOf(
                "Bird's-eye panoramic views of the entire capital",
                "Cool mountain breezes even during summer afternoons",
                "Chic mountain dining & open-air barbecue at sunset",
                "Margalla wildlife spotting (monkeys, mountain birds)"
            ),
            distanceFromIslamabad = "15 min scenic uphill drive",
            bestSeason = "Spring, Autumn & Evenings",
            rating = 4.8,
            recommendedAgent = AgentType.CITY_GUIDE
        ),
        Destination(
            id = "pakistan_monument",
            name = "Pakistan Monument & Lok Virsa",
            category = "Islamabad City",
            subtitle = "National heritage, history & folk crafts",
            description = "Located on Shakarparian Hills, the Pakistan Monument's blooming petal structure symbolizes the four provinces and three territories. Adjacent is the Lok Virsa Museum, one of the finest cultural heritage museums in Asia showcasing living folk traditions.",
            highlights = listOf(
                "Architectural granite petals with historical reliefs",
                "Lok Virsa Heritage Museum with artisan craft galleries",
                "Vibrant Shakarparian botanical gardens & amphitheater",
                "Illuminated night views over Islamabad highway"
            ),
            distanceFromIslamabad = "10 min from Blue Area",
            bestSeason = "All year round",
            rating = 4.7,
            recommendedAgent = AgentType.CITY_GUIDE
        ),
        Destination(
            id = "saidpur_village",
            name = "Saidpur Heritage Village",
            category = "Islamabad City",
            subtitle = "Centuries-old stone village & culinary hub",
            description = "A historical Mughal-era village settled in the Margalla foothills, restored into a cultural village with cobblestone pathways, ancient Hindu temple, gurdwara, and traditional rooftop Pakistani cuisine restaurants.",
            highlights = listOf(
                "Cobblestone alleys & preserved ancient shrine",
                "Traditional charpai seating with Karahi & live music",
                "Artisan pottery shops and painting exhibitions",
                "Stunning view of the illuminated mountain ridge"
            ),
            distanceFromIslamabad = "Sector F-6 (Foothills)",
            bestSeason = "Autumn, Winter & Evening",
            rating = 4.6,
            recommendedAgent = AgentType.CITY_GUIDE
        ),
        Destination(
            id = "margalla_trails",
            name = "Margalla Hills Trails 3 & 5",
            category = "Islamabad City",
            subtitle = "Pristine hiking trails into Margalla National Park",
            description = "Margalla Hills National Park offers world-class hiking trails right inside the capital. Trail 3 is steep and exhilarating leading to the ridge, while Trail 5 features lush freshwater streams, shaded pine trees, and gentle family paths.",
            highlights = listOf(
                "Fresh mountain streams & lush pine canopies",
                "Safe, well-marked trails popular with locals and diplomats",
                "Reaches Pir Sohawa summit overlooking Haripur valley",
                "Margalla birdwatching & morning fitness spot"
            ),
            distanceFromIslamabad = "Trailheads in Sector F-5/F-6",
            bestSeason = "October to April (Morning hours)",
            rating = 4.9,
            recommendedAgent = AgentType.CAMPING_ADVENTURE
        ),
        Destination(
            id = "rawal_lake",
            name = "Rawal Lake & Lake View Park",
            category = "Islamabad City",
            subtitle = "Waterfront boating, aviary & green lawns",
            description = "Rawal Lake is an artificial reservoir providing the water supply for Rawalpindi and Islamabad. Lake View Park features boating, bird aviary, water sports, rock climbing walls, and sprawling picnic lawns by the lake shore.",
            highlights = listOf(
                "Speedboating and paddle boat rides on the lake",
                "Pakistan's largest walk-in bird aviary",
                "Family picnic spots with fresh fried fish stalls",
                "Paintball, quad biking, and adventure games"
            ),
            distanceFromIslamabad = "12 min from Serena Hotel",
            bestSeason = "Spring & Autumn afternoons",
            rating = 4.5,
            recommendedAgent = AgentType.CITY_GUIDE
        ),

        // Murree & Galyat
        Destination(
            id = "murree_mall_road",
            name = "Murree Hill Station & Mall Road",
            category = "Murree & Galyat",
            subtitle = "Colonial era queen of the hills & vibrant bazaars",
            description = "Murree is Pakistan's most famous hill station, situated at 7,500 ft elevation. Mall Road is the bustling heartbeat filled with handcrafted wool shawls, steaming chicken corn soup, grilled corn, and colonial heritage spots like Pindi Point and Kashmir Point.",
            highlights = listOf(
                "Vibrant evening walk on Mall Road with street food",
                "Historic Holy Trinity Church built in 1857",
                "Kashmir Point & Pindi Point chairlifts",
                "Heavy snowfall destination in December–February"
            ),
            distanceFromIslamabad = "55 km (1 hour via Murree Expressway)",
            bestSeason = "Summer for coolness, Winter for snow",
            rating = 4.7,
            recommendedAgent = AgentType.HILL_STATIONS
        ),
        Destination(
            id = "nathia_gali",
            name = "Nathia Gali & Governor House",
            category = "Murree & Galyat",
            subtitle = "Alpine pine forests & cool mountain serenity",
            description = "The jewel of the Abbottabad Galyat region at 8,200 ft elevation. Known for its thick pine, cedar, and oak forests, refreshing alpine climate, charming British colonial buildings, and the famous hot fried red chicken.",
            highlights = listOf(
                "Dense evergreen alpine forests and cool breezes",
                "Historic wooden Saint Matthew's Church (1914)",
                "Starting point for the famous Miranjani and Mushkpuri treks",
                "Authentic spicy Patakha chicken and walnut honey"
            ),
            distanceFromIslamabad = "85 km (2 hours scenic mountain drive)",
            bestSeason = "May to October (Summer) & Winter snow",
            rating = 4.9,
            recommendedAgent = AgentType.HILL_STATIONS
        ),
        Destination(
            id = "mushkpuri_peak",
            name = "Mushkpuri Peak Trek (9,200 ft)",
            category = "Murree & Galyat",
            subtitle = "Majestic meadow summit & alpine wildflowers",
            description = "The second highest peak in Galyat, Mushkpuri offers an unforgettable 3-hour trek through dense pine woods from Dungagali or Nathiagali, opening up to a breathtaking grassy hilltop with 360-degree views of Kashmir and Haripur.",
            highlights = listOf(
                "Gentle to moderate trek suitable for nature lovers",
                "Carpet of yellow alpine wildflowers in spring & summer",
                "Snow-covered winter paradise for winter hikers",
                "Sweeping vistas of Jhelum River & Kashmir ranges"
            ),
            distanceFromIslamabad = "80 km + 2.5 hour hike",
            bestSeason = "May to September & Snow trekking in Dec–Feb",
            rating = 4.9,
            recommendedAgent = AgentType.CAMPING_ADVENTURE
        ),
        Destination(
            id = "ayubia_pipeline",
            name = "Ayubia National Park & Pipeline Track",
            category = "Murree & Galyat",
            subtitle = "Historic 4 km cliffside nature walk & chairlift",
            description = "Ayubia features a 4 km historic Pipeline Walking Track built in 1891 connecting Dunga Gali to Ayubia. It is flat, accessible, and runs along cliffs with dramatic canyon drops, dense forest canopies, and the Ayubia Chairlift.",
            highlights = listOf(
                "Completely flat, easy 4 km nature walk suitable for all ages",
                "Scenic Ayubia chairlift gliding above pine treetops",
                "Ayubia National Park leopards and flying squirrels sanctuary",
                "Cozy cliffside tea stalls with fresh mountain tea"
            ),
            distanceFromIslamabad = "78 km (1 hr 45 min drive)",
            bestSeason = "April to November",
            rating = 4.8,
            recommendedAgent = AgentType.HILL_STATIONS
        ),
        Destination(
            id = "patriata_cable_car",
            name = "Patriata (New Murree) Cable Car",
            category = "Murree & Galyat",
            subtitle = "World-class chairlift & cable car above cloud level",
            description = "Patriata is the highest point in the Murree hills (over 7,500 ft). The modern 3.5 km chairlift and cable car system carries passengers from the middle hill over deep pine ravines all the way to the top summit.",
            highlights = listOf(
                "Exhilarating dual chairlift and gondola cable car ride",
                "Breathtaking mountain ridges and cloud forests",
                "Horse riding and pine forest trails at top summit",
                "Family restaurants with outdoor mountain view terraces"
            ),
            distanceFromIslamabad = "60 km (1 hr 15 min drive)",
            bestSeason = "Year round",
            rating = 4.7,
            recommendedAgent = AgentType.HILL_STATIONS
        ),

        // Northern Gateways
        Destination(
            id = "hunza_valley",
            name = "Hunza Valley (via Karakoram Hwy)",
            category = "Northern Gateway",
            subtitle = "Legendary Shangri-La of the Karakoram",
            description = "Starting from Islamabad via the Hazara Motorway and world-famous Karakoram Highway (N-35), Hunza is a fairytale valley guarded by 7,000m+ giants including Rakaposhi, Ultar Sar, and Ladyfinger Peak.",
            highlights = listOf(
                "Ancient Baltit Fort (700 yrs) & Altit Fort",
                "Attabad Lake turquoise waters and boating",
                "Passu Cones and Hussaini Suspension Bridge",
                "Hospitable culture, apricot orchards & cherry blossoms"
            ),
            distanceFromIslamabad = "Hazara Motorway -> KKH (Road / 1 hr flight to Gilgit)",
            bestSeason = "April (Blossoms) to October (Autumn colors)",
            rating = 5.0,
            recommendedAgent = AgentType.NORTHERN_GATEWAY
        ),
        Destination(
            id = "skardu_baltistan",
            name = "Skardu & Deosai Plains",
            category = "Northern Gateway",
            subtitle = "Land of Karakoram giants & K2 gateway",
            description = "The capital of Baltistan, accessible directly via scenic flights from Islamabad Airport (ISB) or via Jaglot-Skardu road. Famous for the Shangrila Resort, Katpana cold desert dunes, and the high-altitude Deosai National Park.",
            highlights = listOf(
                "Direct 45-min flights from Islamabad Airport (ISB)",
                "Deosai Plains: second highest alpine plateau in the world",
                "Shangrila Resort on Lower Kachura Lake & Upper Kachura Lake",
                "Cold Desert Katpana with sand dunes against snow peaks"
            ),
            distanceFromIslamabad = "45-min flight from ISB or 14-hr scenic road drive",
            bestSeason = "May to October",
            rating = 5.0,
            recommendedAgent = AgentType.NORTHERN_GATEWAY
        ),
        Destination(
            id = "naran_kaghan",
            name = "Naran Kaghan & Saif-ul-Malook",
            category = "Northern Gateway",
            subtitle = "Emerald lakes, river rafting & Babusar Pass",
            description = "A favorite mountain escape through the scenic Kaghan valley. Highlights include legendary Lake Saif-ul-Malook, River Kunhar trout fishing and rafting, and the breathtaking 13,700 ft Babusar Pass crossing into Gilgit-Baltistan.",
            highlights = listOf(
                "Jeep ride to the legendary crystalline Lake Saif-ul-Malook",
                "River Kunhar white water rafting & riverside camping",
                "Babusar Top (13,700 ft) panoramic mountain crossing",
                "Lulusar Lake and Ansoo Lake trekking"
            ),
            distanceFromIslamabad = "240 km (5 hours via Hazara Motorway M-15)",
            bestSeason = "June to September (Babusar opens in summer)",
            rating = 4.8,
            recommendedAgent = AgentType.NORTHERN_GATEWAY
        )
    )

    val travelServices = listOf(
        // Pick & Drop
        TravelService(
            id = "isb_airport_transfer",
            title = "Airport ISB 24/7 Pick & Drop",
            category = "Pick & Drop",
            subtitle = "Islamabad Airport to Hotels / Sectors / Murree",
            description = "Punctual meet & greet pickup service right outside Islamabad International Airport (ISB) arrivals terminal. Professional driver holding your name placard, air-conditioned vehicle, luggage assistance, and direct transfer to your hotel or guest house.",
            pricePkr = 5500,
            priceUsd = 20,
            priceUnit = "per trip",
            includedFeatures = listOf(
                "Flight tracking & zero wait-time delay fees",
                "Clean AC Sedan (Toyota Yaris / Corolla)",
                "Toll taxes & airport parking included",
                "Direct transfers available to Murree & Galyat on request"
            ),
            vehicleOrType = "Toyota Sedan / Hiace Van",
            isPopular = true
        ),
        TravelService(
            id = "prado_4x4_mountain",
            title = "Luxury 4x4 SUV with Chauffeur",
            category = "Pick & Drop",
            subtitle = "Toyota Prado / Fortuner for Murree & North",
            description = "Premium 4-wheel drive SUV with an experienced mountain chauffeur. Perfect for families, executive travelers, and foreign tourists heading to Murree, Nathia Gali, or northern mountain tracks in total comfort and safety.",
            pricePkr = 22000,
            priceUsd = 80,
            priceUnit = "per day",
            includedFeatures = listOf(
                "Toyota Prado Tx / Fortuner 4x4",
                "Senior mountain-certified vetted driver",
                "12 hours daily city/hill station service",
                "Emergency first-aid kit & winter chains equipped"
            ),
            vehicleOrType = "Toyota Prado 4x4 / Fortuner",
            isPopular = true
        ),
        TravelService(
            id = "hiace_family_van",
            title = "Toyota Grand Cabin (12-Seater)",
            category = "Pick & Drop",
            subtitle = "Spacious van for family & group travel",
            description = "High-roof Toyota Hiace Grand Cabin with reclining captain seats and large luggage boot. The best choice for overseas family delegations arriving together with multiple bags exploring Islamabad, Murree, and Northern valleys.",
            pricePkr = 18000,
            priceUsd = 65,
            priceUnit = "per day",
            includedFeatures = listOf(
                "12 comfortable reclining velvet seats",
                "Dual AC climate control for mountains",
                "Experienced polite driver fluent in Urdu & English",
                "Huge luggage space for international luggage"
            ),
            vehicleOrType = "Toyota Hiace Grand Cabin",
            isPopular = false
        ),

        // Hotels & Stays
        TravelService(
            id = "serena_luxury_stay",
            title = "Islamabad 5-Star Luxury Booking",
            category = "Hotels & Stays",
            subtitle = "Islamabad Serena Hotel / Marriott Experience",
            description = "Experience the epitome of Pakistani hospitality and Islamic architecture at Islamabad Serena Hotel or Marriott. Landscaped Mughal gardens, heated swimming pools, Maisha spa, and security clearance for foreign dignitaries and tourists.",
            pricePkr = 75000,
            priceUsd = 270,
            priceUnit = "per night",
            includedFeatures = listOf(
                "Deluxe Executive Room with Margalla view",
                "Lavish buffet breakfast included",
                "Maisha Spa & Health Club access",
                "VIP Airport transfer & 24/7 concierge"
            ),
            vehicleOrType = "5-Star Luxury Hotel",
            isPopular = true
        ),
        TravelService(
            id = "f_sector_boutique_residence",
            title = "Executive F-Sector Boutique Villa",
            category = "Hotels & Stays",
            subtitle = "Private serene guest house in F-6 / F-7 / E-7",
            description = "Quiet, high-security boutique guest residences located in the elite diplomatic and residential sectors of Islamabad. Within walking distance to Super Market, cafes, and embassy avenues. Homely warmth with hotel-grade amenities.",
            pricePkr = 16000,
            priceUsd = 58,
            priceUnit = "per night",
            includedFeatures = listOf(
                "Spacious King suite with private balcony",
                "Complimentary high-speed WiFi & breakfast",
                "Walking distance to F-6 / F-7 cafes & markets",
                "24/7 dedicated butler & generator backup"
            ),
            vehicleOrType = "Boutique Executive Villa",
            isPopular = true
        ),
        TravelService(
            id = "nathia_gali_pine_chalet",
            title = "Nathia Gali Pine Wood Resort Chalet",
            category = "Hotels & Stays",
            subtitle = "Romantic mountain lodge among pine trees",
            description = "Cozy wooden suites nestled deep inside the pine forests of Nathia Gali and Murree hills. Wake up to misty mountain views, birds singing, wood-burning fireplaces, and warm steaming tea on your wooden terrace.",
            pricePkr = 25000,
            priceUsd = 90,
            priceUnit = "per night",
            includedFeatures = listOf(
                "Log chalet suite with panoramic pine valley view",
                "Evening bonfire on the private patio",
                "Fresh hot breakfast & local organic honey",
                "Heater / fireplace amenities for cold mountain nights"
            ),
            vehicleOrType = "Alpine Mountain Chalet",
            isPopular = true
        ),

        // Traveling & Tours
        TravelService(
            id = "islamabad_city_guided_tour",
            title = "Islamabad Full-Day Heritage & Sights",
            category = "Tours & Sightseeing",
            subtitle = "Faisal Mosque, Monument, Saidpur & Monal",
            description = "A curated full-day guided tour across the capital. Includes pickup from your hotel, guided exploration of Faisal Mosque, Lok Virsa, Pakistan Monument, lunch at Saidpur Village, and a picturesque sunset dinner at Monal / Pir Sohawa on Margalla Hills.",
            pricePkr = 15000,
            priceUsd = 55,
            priceUnit = "per person",
            includedFeatures = listOf(
                "Dedicated AC vehicle with fuel & driver",
                "Certified English & Urdu speaking historian guide",
                "All monument and museum entry tickets included",
                "Mineral water, tea refreshments & sunset stop at Pir Sohawa"
            ),
            vehicleOrType = "Guided Day Tour",
            isPopular = true
        ),
        TravelService(
            id = "murree_galyat_weekend_tour",
            title = "2-Day Murree & Galyat Alpine Escape",
            category = "Tours & Sightseeing",
            subtitle = "Mall Road, Patriata Cable Car & Nathia Gali",
            description = "The ultimate weekend retreat from Islamabad. Day 1 covers Murree Mall Road, Patriata cable car, and evening stay in Nathia Gali. Day 2 features Ayubia Pipeline nature track, Dunga Gali, and scenic return to Islamabad.",
            pricePkr = 38000,
            priceUsd = 140,
            priceUnit = "per person",
            includedFeatures = listOf(
                "Complete 2-day private transport with driver & fuel",
                "1-night stay at top-rated Nathia Gali hotel",
                "Patriata chairlift tickets & Ayubia pipeline entry",
                "Breakfast and dinner included"
            ),
            vehicleOrType = "2-Day Private Tour Package",
            isPopular = true
        ),

        // Camping & Adventure
        TravelService(
            id = "mushkpuri_alpine_camping",
            title = "Mushkpuri & Galyat Camping Trek",
            category = "Camping & Adventure",
            subtitle = "Stargazing under pine trees with bonfire & BBQ",
            description = "A magical wilderness experience for adventure lovers. Includes certified trek leaders, waterproof canvas tents, down sleeping bags, evening campfire barbecue under star-filled mountain skies, and morning sunrise over Kashmir peaks.",
            pricePkr = 14000,
            priceUsd = 50,
            priceUnit = "per person",
            includedFeatures = listOf(
                "Waterproof dome tents & thermal sleeping bags",
                "Evening live bonfire and chicken tikka BBQ dinner",
                "Guided summit hike to Mushkpuri Peak (9,200 ft)",
                "Breakfast, hot tea, and safety first-aid escort"
            ),
            vehicleOrType = "All-inclusive Camping Package",
            isPopular = true
        ),
        TravelService(
            id = "camping_gear_rental",
            title = "Complete Camping Gear Rental Kit",
            category = "Camping & Adventure",
            subtitle = "Tents, sleeping mats, stove & rucksacks delivered",
            description = "Traveling independently to Margalla ridge, Mushkpuri, or the northern areas? Rent high-grade mountaineering tents, sleeping bags (-5°C rated), portable gas stoves, trekking poles, and headlamps delivered straight to your hotel in Islamabad.",
            pricePkr = 4500,
            priceUsd = 16,
            priceUnit = "per day kit",
            includedFeatures = listOf(
                "2-person or 4-person windproof Coleman/Quechua tent",
                "2 thermal sleeping bags & inflatable sleeping mats",
                "Portable butane stove with cooking mess kit",
                "Free delivery to any Islamabad sector or hotel"
            ),
            vehicleOrType = "Equipment Rental Kit",
            isPopular = false
        )
    )

    val emergencyContacts = listOf(
        Pair("Islamabad Police Emergency", "15"),
        Pair("Rescue & Ambulance Service", "1122"),
        Pair("Islamabad Tourist Police Help Desk", "+92 51 9100008"),
        Pair("Islamabad Airport (ISB) Flight Inquiry", "+92 51 95551000"),
        Pair("Murree Emergency Assistance Helpline", "051-9269016"),
        Pair("Margalla Wildlife Rescue Squad", "051-9253258")
    )

    val officialForums = listOf(
        com.example.model.SocialForum(
            id = "whatsapp",
            name = "Official WhatsApp Desk",
            handleOrTarget = "03457059286 (+923457059286)",
            category = "Instant Messaging",
            description = "Direct 24/7 client booking, AI automation & concierge support",
            actionType = com.example.model.ActionType.OPEN_WHATSAPP,
            actionUrl = "https://api.whatsapp.com/send?phone=923457059286&text=" + android.net.Uri.encode("Hello explore Islmbd! I would like to inquire about tourist spots, hotels, or transport."),
            isPrimarySupport = true
        ),
        com.example.model.SocialForum(
            id = "call_support",
            name = "Official Phone Call",
            handleOrTarget = "03457059286",
            category = "Direct Contact",
            description = "Immediate direct cellular line for tourist queries across Pakistan & abroad",
            actionType = com.example.model.ActionType.CALL_PHONE,
            actionUrl = "tel:03457059286",
            isPrimarySupport = true
        ),
        com.example.model.SocialForum(
            id = "sms_support",
            name = "Official SMS Helpline",
            handleOrTarget = "03457059286",
            category = "Direct Contact",
            description = "Quick SMS inquiries and automated reservation requests",
            actionType = com.example.model.ActionType.SEND_SMS,
            actionUrl = "smsto:03457059286",
            isPrimarySupport = true
        ),
        com.example.model.SocialForum(
            id = "official_email",
            name = "Official Email Desk",
            handleOrTarget = "info.explore.islmbd@gmail.com",
            category = "Direct Contact",
            description = "Official communications, itineraries, business inquiries & quotes",
            actionType = com.example.model.ActionType.SEND_EMAIL,
            actionUrl = "mailto:info.explore.islmbd@gmail.com?subject=explore%20Islmbd%20Travel%20Inquiry",
            isPrimarySupport = true
        ),
        com.example.model.SocialForum(
            id = "telegram",
            name = "Telegram Channel & Bot",
            handleOrTarget = "@exploreislmbd / +923457059286",
            category = "Instant Messaging",
            description = "Instant messaging with 24/7 AI response automation and travel updates",
            actionType = com.example.model.ActionType.OPEN_TELEGRAM,
            actionUrl = "https://t.me/exploreislmbd",
            isPrimarySupport = false
        ),
        com.example.model.SocialForum(
            id = "facebook",
            name = "Facebook Official Page",
            handleOrTarget = "facebook.com/exploreislmbd",
            category = "Social Media",
            description = "Verified community, customer reviews, photo galleries & travel stories",
            actionType = com.example.model.ActionType.OPEN_URL,
            actionUrl = "https://www.facebook.com/exploreislmbd",
            isPrimarySupport = false
        ),
        com.example.model.SocialForum(
            id = "tiktok",
            name = "TikTok Travel Stream",
            handleOrTarget = "@exploreislmbd",
            category = "Video & Social",
            description = "Short videos of Margalla trails, Murree snowfall, and Northern valleys",
            actionType = com.example.model.ActionType.OPEN_URL,
            actionUrl = "https://www.tiktok.com/@exploreislmbd",
            isPrimarySupport = false
        ),
        com.example.model.SocialForum(
            id = "instagram",
            name = "Instagram Visuals",
            handleOrTarget = "@exploreislmbd",
            category = "Social Media",
            description = "Stunning photography of Islamabad, pine resorts, luxury chalets & trips",
            actionType = com.example.model.ActionType.OPEN_URL,
            actionUrl = "https://www.instagram.com/exploreislmbd",
            isPrimarySupport = false
        ),
        com.example.model.SocialForum(
            id = "discord",
            name = "Discord Community",
            handleOrTarget = "discord.gg/exploreislmbd",
            category = "Community & Chat",
            description = "Travelers forum, route sharing, trekking buddies & automated help desks",
            actionType = com.example.model.ActionType.OPEN_URL,
            actionUrl = "https://discord.gg/exploreislmbd",
            isPrimarySupport = false
        ),
        com.example.model.SocialForum(
            id = "pinterest",
            name = "Pinterest Travel Boards",
            handleOrTarget = "pinterest.com/exploreislmbd",
            category = "Social Media",
            description = "Moodboards, packing guides, and itinerary inspirations for Galyat & North",
            actionType = com.example.model.ActionType.OPEN_URL,
            actionUrl = "https://www.pinterest.com/exploreislmbd",
            isPrimarySupport = false
        ),
        com.example.model.SocialForum(
            id = "twitter_x",
            name = "X (Twitter) Feed",
            handleOrTarget = "@exploreislmbd",
            category = "Social Media",
            description = "Live road alerts, weather updates for Murree & northern passes",
            actionType = com.example.model.ActionType.OPEN_URL,
            actionUrl = "https://x.com/exploreislmbd",
            isPrimarySupport = false
        ),
        com.example.model.SocialForum(
            id = "google_search_business",
            name = "Google Search & Business Profile",
            handleOrTarget = "explore Islmbd on Google",
            category = "Search & Discovery",
            description = "Google Business listing, verified reviews, directions & ratings",
            actionType = com.example.model.ActionType.OPEN_URL,
            actionUrl = "https://www.google.com/search?q=explore+Islmbd+Islamabad+Travel+and+Tourism",
            isPrimarySupport = false
        ),
        com.example.model.SocialForum(
            id = "google_meet",
            name = "Google Meet Virtual Concierge",
            handleOrTarget = "meet.google.com / info.explore.islmbd@gmail.com",
            category = "Video & Consultations",
            description = "Live video consultation for overseas tourists and corporate groups",
            actionType = com.example.model.ActionType.OPEN_URL,
            actionUrl = "https://meet.google.com/new",
            isPrimarySupport = false
        )
    )
}

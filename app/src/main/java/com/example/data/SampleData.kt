package com.example.data

import com.example.model.ActionType
import com.example.model.AgentType
import com.example.model.BudgetTier
import com.example.model.Destination
import com.example.model.OfficialContacts
import com.example.model.SocialForum
import com.example.model.TravelService
import com.example.model.VisitorPurpose

object SampleData {

    val destinations = listOf(
        // --- 1. Health & Medical Checkups ---
        Destination(
            id = "shifa_hospital",
            name = "Shifa International Hospital",
            category = "Health & Hospitals",
            purpose = VisitorPurpose.HEALTH_MEDICAL,
            subtitle = "JCI-accredited tertiary care, organ transplant & specialist clinics",
            description = "Pakistan's leading JCI-accredited hospital located in Sector H-8/4. Features renowned specialists in cardiology, oncology, liver/kidney transplants, neurology, and pediatrics. Dedicated international & outstation patient coordination desk.",
            highlights = listOf(
                "24/7 Emergency & trauma center with critical care specialists",
                "Advanced diagnostics: 3T MRI, PET-CT Scan, cardiac catheterization",
                "Dedicated patient guest houses & pharmacy complexes on Pitras Bukhari Rd",
                "Metro Bus connectivity via H-8 / I-8 feeder routes"
            ),
            locationSector = "Sector H-8/4",
            distanceFromCenter = "8 min from Blue Area",
            metroBusNear = "I-8 Metro Station (Orange/Red feeder)",
            rating = 4.8,
            budgetLevel = "Private Tertiary Care (Direct & Insurance)"
        ),
        Destination(
            id = "pims_hospital",
            name = "PIMS - Pakistan Institute of Medical Sciences",
            category = "Health & Hospitals",
            purpose = VisitorPurpose.HEALTH_MEDICAL,
            subtitle = "National public medical institute, Children Hospital & Burn Center",
            description = "The premier public healthcare institution in the capital, located in Sector G-8/3. Includes Islamabad Specialists Hospital, Children's Hospital, Maternal & Child Health Center, and National Burn Center serving patients from across Pakistan.",
            highlights = listOf(
                "Affordable & subsidized state healthcare for all Pakistani citizens",
                "Specialist OPDs with top professors and surgeons",
                "Directly adjacent to Metro Bus PIMS Station on Red Line",
                "24/7 Emergency, blood bank & trauma wards"
            ),
            locationSector = "Sector G-8/3",
            distanceFromCenter = "5 min from Blue Area",
            metroBusNear = "PIMS Metro Bus Station (Red Line)",
            rating = 4.4,
            budgetLevel = "Subsidized Public Healthcare"
        ),
        Destination(
            id = "maroof_hospital",
            name = "Maroof International Hospital",
            category = "Health & Hospitals",
            purpose = VisitorPurpose.HEALTH_MEDICAL,
            subtitle = "Modern multi-specialty boutique hospital & executive clinics",
            description = "Located in Sector F-10 Markaz, Maroof International is favored by international diplomats, corporate executives, and visitors seeking expedited specialist appointments, private executive suites, and advanced surgical procedures.",
            highlights = listOf(
                "Executive health screening packages for overseas travelers",
                "Surrounded by F-10 Markaz hotels, serviced apartments & cafes",
                "Fast-track appointment scheduling and dedicated patient attendants",
                "State-of-the-art dialysis & minimally invasive surgical units"
            ),
            locationSector = "Sector F-10 Markaz",
            distanceFromCenter = "7 min from F-7",
            metroBusNear = "I-9 / F-10 EV Feeder Bus",
            rating = 4.7,
            budgetLevel = "Premium Executive Private Care"
        ),
        Destination(
            id = "kulsum_hospital",
            name = "Kulsum International Hospital",
            category = "Health & Hospitals",
            purpose = VisitorPurpose.HEALTH_MEDICAL,
            subtitle = "Heart & general healthcare hub in the heart of Blue Area",
            description = "A project of Saif Group located right on Jinnah Avenue, Blue Area. Celebrated for its comprehensive cardiac sciences, bariatric surgery, orthopedics, and central location with direct access to Blue Area hotels and transit.",
            highlights = listOf(
                "Prime location on Jinnah Avenue, Blue Area",
                "Walking distance to Stock Exchange Metro Station",
                "Dedicated cardiac emergency and cardiac rehabilitation",
                "Affordable outpatient diagnostic packages"
            ),
            locationSector = "Blue Area (Jinnah Avenue)",
            distanceFromCenter = "Central Blue Area",
            metroBusNear = "Stock Exchange Metro Station (Red Line)",
            rating = 4.6,
            budgetLevel = "Mid-to-High Private Care"
        ),

        // --- 2. Courts & Official Affairs ---
        Destination(
            id = "supreme_court",
            name = "Supreme Court of Pakistan",
            category = "Courts & Official",
            purpose = VisitorPurpose.LEGAL_COURTS,
            subtitle = "Apex judicial authority on Constitution Avenue",
            description = "The highest appellate court of Pakistan, situated on Constitution Avenue facing the Margalla backdrop. Visitors appearing for constitutional petitions, civil/criminal appeals, or legal internships must comply with formal dress codes and security clearance protocols.",
            highlights = listOf(
                "Apex court hearings and landmark constitutional benches",
                "Visitor pass required with CNIC at Reception Gate 1",
                "Adjacent to Parliament House, Prime Minister Secretariat & Aiwan-e-Sadr",
                "Lawyers lounge, legal research library, and bar facilities"
            ),
            locationSector = "Constitution Avenue, Red Zone",
            distanceFromCenter = "3 min from Serena Hotel",
            metroBusNear = "Pak Secretariat Metro Station (Red Line)",
            rating = 4.9,
            budgetLevel = "Official / Legal Visitor Entry"
        ),
        Destination(
            id = "islamabad_high_court",
            name = "Islamabad High Court (IHC)",
            category = "Courts & Official",
            purpose = VisitorPurpose.LEGAL_COURTS,
            subtitle = "Modern judicial complex in Sector G-10",
            description = "The principal provincial high court for the Islamabad Capital Territory, housed in a state-of-the-art judicial complex in Sector G-10. Handles writ petitions, corporate, tax, and criminal litigation.",
            highlights = listOf(
                "Modern multi-story judicial building with specialized courtrooms",
                "Easy access via Kashmir Highway / Srinagar Highway",
                "Close to G-10 and G-11 Markaz budget hotels & law chambers",
                "Streamlined case tracking biometric counters"
            ),
            locationSector = "Sector G-10",
            distanceFromCenter = "10 min from Blue Area",
            metroBusNear = "Srinagar Highway Orange Metro Feeder",
            rating = 4.7,
            budgetLevel = "Official / Legal Visitor Entry"
        ),
        Destination(
            id = "diplomatic_enclave",
            name = "Diplomatic Enclave & Foreign Embassies",
            category = "Courts & Official",
            purpose = VisitorPurpose.LEGAL_COURTS,
            subtitle = "Visa interviews, attestations & diplomatic missions",
            description = "Secure zone housing foreign embassies (US, UK, Schengen, UAE, Saudi Arabia, China, etc.). General visitors for visa interviews, attestation of degrees/documents, and consular services must enter via the official Diplomatic Shuttle Service at Avenue 3, G-5.",
            highlights = listOf(
                "Entry exclusively via Diplomatic Shuttle Service (Avenue 3 Gate)",
                "Visa interviews for study abroad, tourism, business & work permits",
                "Foreign Ministry (MOFA) attestation complex nearby",
                "High security area; valid appointment slip & original CNIC/Passport mandatory"
            ),
            locationSector = "Sector G-5 (Diplomatic Enclave)",
            distanceFromCenter = "5 min from Serena / Red Zone",
            metroBusNear = "Pak Secretariat Station + Shuttle",
            rating = 4.8,
            budgetLevel = "Official Consular & Visa Affairs"
        ),

        // --- 3. Education, Exams & Study ---
        Destination(
            id = "nust_university",
            name = "NUST - National University of Sciences & Tech",
            category = "Universities & Study",
            purpose = VisitorPurpose.EDUCATION_EXAMS,
            subtitle = "Pakistan's #1 engineering & technology university in H-12",
            description = "Ranked among the top universities globally, NUST spans a sprawling 700-acre smart campus in H-12. Hosts thousands of visitors for NET entry tests, international conferences, robotics expos, and convocations.",
            highlights = listOf(
                "National Science & Technology Park (NSTP) innovation hub",
                "Sprawling campus with sports complex, central library & lakes",
                "Convenient campus gate directly off Kashmir / Srinagar Highway",
                "Student cafes, guest rooms, and academic testing centers"
            ),
            locationSector = "Sector H-12",
            distanceFromCenter = "12 min from Blue Area",
            metroBusNear = "NUST Orange Line Metro Station",
            rating = 4.9,
            budgetLevel = "Academic / Entry Test / Conference"
        ),
        Destination(
            id = "qau_university",
            name = "Quaid-i-Azam University (QAU)",
            category = "Universities & Study",
            purpose = VisitorPurpose.EDUCATION_EXAMS,
            subtitle = "Premier research institution under Margalla Foothills",
            description = "Consistently ranked top for natural and social sciences in Pakistan, QAU covers over 1,700 scenic acres next to Lake View and Margalla Hills. Major center for MPhil, PhD research, CSS prep, and cultural events.",
            highlights = listOf(
                "Scenic campus surrounded by wild Margalla flora and pine hills",
                "Renowned departments of Physics, Economics, Chemistry & International Relations",
                "Central library with rare manuscripts and research archives",
                "Close to Bari Imam shrine and Lake View Park"
            ),
            locationSector = "Near Diplomatic Enclave & Lake View",
            distanceFromCenter = "10 min from Blue Area",
            metroBusNear = "Murree Road EV Shuttle",
            rating = 4.8,
            budgetLevel = "Research & Student Visits"
        ),
        Destination(
            id = "fast_university",
            name = "FAST-NUCES Islamabad",
            category = "Universities & Study",
            purpose = VisitorPurpose.EDUCATION_EXAMS,
            subtitle = "Premier computer science & software engineering campus",
            description = "Located in Sector H-9, FAST is the premier hub for software engineers and tech talent. Frequently visited by aspiring students from across Pakistan for NU admission tests, hackathons, and corporate job fairs.",
            highlights = listOf(
                "Center of excellence in artificial intelligence, software & data science",
                "Walking distance from H-9 / I-9 industrial and academic belt",
                "Surrounded by budget student hostels and metro bus connectivity",
                "Active alumni network and tech incubator"
            ),
            locationSector = "Sector H-9",
            distanceFromCenter = "10 min from Blue Area",
            metroBusNear = "Potohar Metro Station (Red Line)",
            rating = 4.7,
            budgetLevel = "Student Admissions & Hackathons"
        ),

        // --- 4. Shopping & Bazaars ---
        Destination(
            id = "centaurus_mall",
            name = "The Centaurus Mall",
            category = "Shopping & Malls",
            purpose = VisitorPurpose.SHOPPING_LIFESTYLE,
            subtitle = "Iconic 3-tower luxury shopping, cinema & food court hub",
            description = "The most visited shopping destination in Islamabad, featuring over 250 national and international brands, modern hypermarket, multi-screen cineplex, family entertainment zone, and panoramic rooftop dining with views of Margalla.",
            highlights = listOf(
                "Top fashion brands (Khaadi, Sapphire, J., Gul Ahmed, Zara, Nike)",
                "Huge food court with local Pakistani & international cuisines",
                "Al-Fatah Hypermarket & Centaurus Cineplex 3D theatre",
                "Directly linked via Jinnah Avenue & adjacent to 7th Avenue"
            ),
            locationSector = "Sector F-8/4 (Jinnah Avenue)",
            distanceFromCenter = "Central Islamabad",
            metroBusNear = "Katchery / Centaurus Metro Station (Red Line)",
            rating = 4.8,
            budgetLevel = "All Budgets (Bargains to Luxury)"
        ),
        Destination(
            id = "jinnah_super_f7",
            name = "Jinnah Super Market (F-7 Markaz)",
            category = "Shopping & Malls",
            purpose = VisitorPurpose.SHOPPING_LIFESTYLE,
            subtitle = "Islamabad's vibrant heart of fashion, cafes & handicrafts",
            description = "The quintessential circular shopping district of Islamabad. Packed with boutique clothiers, bookshops (Saeed Book Bank), jewelers, traditional leather shoe shops, and beloved outdoor cafes serving shawarma and karak chai.",
            highlights = listOf(
                "Famous open-air circular promenade with lively evening buzz",
                "Home to Saeed Book Bank, the largest bookstore in South Asia",
                "Traditional Pakistani handloom, pashmina shawls & Northern handicrafts",
                "Eclectic dining: street roll parathas to artisan European bistros"
            ),
            locationSector = "Sector F-7 Markaz",
            distanceFromCenter = "Heart of F-Sectors",
            metroBusNear = "F-7 EV Feeder Bus",
            rating = 4.8,
            budgetLevel = "Moderate to High Boutique"
        ),
        Destination(
            id = "giga_mall",
            name = "Giga Mall & World Trade Center",
            category = "Shopping & Malls",
            purpose = VisitorPurpose.SHOPPING_LIFESTYLE,
            subtitle = "Mega retail paradise on Grand Trunk Road / DHA Phase II",
            description = "One of Pakistan's largest shopping complexes, Giga Mall houses over 300 international brands, Carrefour hypermarket, high-tech amusement park (Fun City), and endless dining options. Ideal for visitors entering from GT Road or Motorway.",
            highlights = listOf(
                "Massive indoor shopping with multi-tier Carrefour & Fun City",
                "Prime location on GT Road, gateway for Lahore & Rawalpindi commuters",
                "Spacious multi-level underground parking and high security",
                "Vast banquet and food court halls"
            ),
            locationSector = "DHA Phase II (GT Road)",
            distanceFromCenter = "20 min from Blue Area",
            metroBusNear = "Rawalpindi Metro Feeder Route",
            rating = 4.7,
            budgetLevel = "Complete Family Shopping"
        ),

        // --- 5. Tourism & Landmarks ---
        Destination(
            id = "faisal_mosque",
            name = "Faisal Mosque",
            category = "Tourism & Heritage",
            purpose = VisitorPurpose.TOURISM_EXPLORE,
            subtitle = "National architectural monument at the foot of Margalla",
            description = "Nestled dramatically under the Margalla Hills, the Faisal Mosque is Pakistan's national mosque and an architectural marvel designed by Turkish architect Vedat Dalokay. It features an eight-sided concrete shell inspired by a Bedouin desert tent.",
            highlights = listOf(
                "Striking white tent design & four towering 260-ft minarets",
                "Spectacular mountain backdrop of Margalla National Park",
                "Spacious marble courtyard overlooking Islamabad city",
                "Respectful dress code: head coverings & shoe deposit"
            ),
            locationSector = "Sector E-7 (Margalla Foothills)",
            distanceFromCenter = "5 min from F-7",
            metroBusNear = "Faisal Mosque EV Shuttle",
            rating = 4.9,
            budgetLevel = "Free Public Landmark"
        ),
        Destination(
            id = "daman_e_koh",
            name = "Daman-e-Koh & Monal Pir Sohawa",
            category = "Tourism & Heritage",
            purpose = VisitorPurpose.TOURISM_EXPLORE,
            subtitle = "Panoramic hilltop terraces & mountain dining",
            description = "Daman-e-Koh is a viewing terrace halfway up Margalla Hills, offering breathtaking views over the sectors of Islamabad, Faisal Mosque, and Rawal Lake. Continuing further up leads to Pir Sohawa and famed hilltop viewpoints.",
            highlights = listOf(
                "Bird's-eye panoramic views of the entire planned capital",
                "Cool mountain breezes even during summer afternoons",
                "Chic mountain dining & open-air barbecue at sunset",
                "Margalla wildlife spotting (monkeys, mountain birds)"
            ),
            locationSector = "Margalla Hills National Park",
            distanceFromCenter = "15 min scenic uphill drive",
            metroBusNear = "Taxi / inDrive / 4x4 recommended",
            rating = 4.8,
            budgetLevel = "Sightseeing Terraces"
        ),
        Destination(
            id = "pakistan_monument",
            name = "Pakistan Monument & Lok Virsa Museum",
            category = "Tourism & Heritage",
            purpose = VisitorPurpose.TOURISM_EXPLORE,
            subtitle = "National history, granite petals & living folk heritage",
            description = "Located on Shakarparian Hills, the Pakistan Monument's blooming petal structure symbolizes the four provinces and territories. Adjacent is the Lok Virsa Museum showcasing living folk crafts from Sindh, Punjab, Balochistan, KPK, GB, and Kashmir.",
            highlights = listOf(
                "Architectural granite petals with historical reliefs",
                "Lok Virsa Heritage Museum with artisan craft galleries",
                "Vibrant Shakarparian botanical gardens & amphitheater",
                "Illuminated night views over Islamabad Expressway"
            ),
            locationSector = "Shakarparian Hills",
            distanceFromCenter = "10 min from Blue Area",
            metroBusNear = "Shakarparian Tourist Shuttle",
            rating = 4.8,
            budgetLevel = "Low Entry Ticket (PKR 50-100)"
        ),
        Destination(
            id = "saidpur_village",
            name = "Saidpur Heritage Village",
            category = "Tourism & Heritage",
            purpose = VisitorPurpose.TOURISM_EXPLORE,
            subtitle = "Mughal-era cobblestone village & rooftop culinary hub",
            description = "A historical village settled in the Margalla foothills, restored into a cultural enclave with cobblestone alleys, preserved ancient Hindu temple, Sikh gurdwara, and traditional rooftop Pakistani cuisine restaurants.",
            highlights = listOf(
                "Cobblestone alleys & preserved ancient heritage structures",
                "Traditional charpai seating with Karahi & live instrumental music",
                "Artisan pottery workshops and painting exhibitions",
                "Stunning view of the illuminated mountain ridge"
            ),
            locationSector = "Sector F-6 (Foothills)",
            distanceFromCenter = "5 min from Super Market F-6",
            metroBusNear = "F-6 EV Feeder Bus",
            rating = 4.6,
            budgetLevel = "Cultural Dining & Heritage"
        ),

        // --- 6. Northern Gateway & Hill Stations ---
        Destination(
            id = "murree_hills",
            name = "Murree Hill Station & Mall Road",
            category = "Northern Gateway",
            purpose = VisitorPurpose.NORTHERN_GATEWAY,
            subtitle = "Colonial hill station just 50 minutes via Murree Expressway",
            description = "Pakistan's most popular hill station perched at 7,500 ft. A quick drive from Islamabad via the 4-lane Murree Expressway. Features bustling Mall Road, colonial churches, pine walks, and snowfall in winter.",
            highlights = listOf(
                "Quick 50-minute drive on smooth 4-lane Murree Expressway",
                "Iconic pedestrian Mall Road with street food, handicrafts & souvenirs",
                "Patriata New Murree chairlift & cable car offering aerial forest views",
                "Sublime escape from summer heat (15°C to 20°C in peak summer)"
            ),
            locationSector = "Murree Hills (Rawalpindi District)",
            distanceFromCenter = "50 km from Islamabad Toll Plaza",
            metroBusNear = "Faizabad Murree Transit Coaches",
            rating = 4.6,
            budgetLevel = "Affordable to Luxury Stays"
        ),
        Destination(
            id = "nathia_gali",
            name = "Nathia Gali & Mushkpuri Alpine Peak",
            category = "Northern Gateway",
            purpose = VisitorPurpose.NORTHERN_GATEWAY,
            subtitle = "Dense pine sanctuaries, hiking tracks & alpine meadows (8,200 ft)",
            description = "The crown jewel of the Galyat range, Nathia Gali is renowned for dense walnut and pine forests, cool mountain air, colonial Governor House, and the famous alpine hiking trail to the wildflower meadows of Mushkpuri Peak (9,200 ft).",
            highlights = listOf(
                "Mushkpuri Peak & Miranjani Peak alpine summit hikes",
                "Ayubia Pipeline Track (ancient walking trail carved into pine cliffs)",
                "Cozy wooden pine lodges, fireplaces, and fresh trout fish",
                "Pleasant 1.5 to 2 hour drive from Islamabad via Murree or Abbottabad"
            ),
            locationSector = "Abbottabad / Galyat District",
            distanceFromCenter = "75 km from Islamabad",
            metroBusNear = "4x4 / Chauffeur Car Recommended",
            rating = 4.9,
            budgetLevel = "Alpine Retreat & Family Stays"
        ),
        Destination(
            id = "hazara_motorway_gateway",
            name = "Hazara Motorway (M-15) Corridor",
            category = "Northern Gateway",
            purpose = VisitorPurpose.NORTHERN_GATEWAY,
            subtitle = "Express high-speed highway to Abbottabad, Naran & Swat",
            description = "Starting right outside Islamabad, the M-15 Hazara Motorway cuts travel time in half to Abbottabad, Mansehra, Shinkiari, and links seamlessly to the Kaghan Valley, Babusar Pass, and Swat Motorway (M-16).",
            highlights = listOf(
                "World-class 6-lane scenic mountain motorway with modern tunnels",
                "Gateway to Naran, Saif-ul-Malook, Babusar Pass & Gilgit",
                "Direct connection to Swat Expressway towards Kalam and Malam Jabba",
                "Reliable year-round all-weather travel"
            ),
            locationSector = "M-15 Motorway Interchange (Islamabad)",
            distanceFromCenter = "25 min from Central Islamabad",
            metroBusNear = "Motorway Transit Hub",
            rating = 4.9,
            budgetLevel = "Express Highway Transit"
        )
    )

    // Comprehensive multi-budget transport, hotel, dining, and tour services
    val travelServices = listOf(
        // --- Transport Services for ALL Budgets ---
        TravelService(
            id = "metro_bus_pass",
            title = "Metro Bus & EV Bus Smart Travel Guide",
            category = "Transport & Transit",
            subtitle = "Red Line, Orange Line (Airport) & Blue/Green Routes",
            description = "The most affordable, air-conditioned rapid transit network in the twin cities. Red Line connects Rawalpindi Saddar to Pak Secretariat via Blue Area, Orange Line connects Peshawar Morr to Islamabad Airport, and new Electric Buses (EV) cover all sectors.",
            pricePkr = 50,
            priceUsd = 1,
            priceUnit = "per trip",
            budgetTier = BudgetTier.BUDGET_SAVER,
            includedFeatures = listOf(
                "Full AC rapid bus transit connecting all major sectors",
                "Red Line (Saddar to Secretariat), Orange Line (Airport ISB link)",
                "New Green & Blue Line feeder EV buses to universities and hospitals",
                "Token or Metro Smart Card reload assistance"
            ),
            vehicleOrType = "Metro Rapid Bus Transit",
            isPopular = true
        ),
        TravelService(
            id = "indrive_yango_assist",
            title = "inDrive / Yango App Cab Booking Concierge",
            category = "Transport & Transit",
            subtitle = "On-demand city cabs, bikes, mini cars & AC rides",
            description = "Seamless point-to-point travel across Islamabad & Rawalpindi. We help you choose the best ride tier (Ride Mini, Ride AC, Sedan, or Bike) on inDrive or Yango with fair pricing and verified drivers, avoiding overcharging.",
            pricePkr = 800,
            priceUsd = 3,
            priceUnit = "average city trip",
            budgetTier = BudgetTier.COMFORT_STANDARD,
            includedFeatures = listOf(
                "Direct door-to-door pickup anywhere in Islamabad",
                "Transparent fair pricing with no meter tampering",
                "Choice of economy mini, air-conditioned sedans or rapid motorbikes",
                "Available 24 hours a day, 7 days a week"
            ),
            vehicleOrType = "App-based Ride Hailing (inDrive/Yango)",
            isPopular = true
        ),
        TravelService(
            id = "chauffeur_city_sedan",
            title = "Full-Day Chauffeur Driven Sedan (Corolla / Yaris)",
            category = "Transport & Transit",
            subtitle = "12 hours dedicated city car with professional driver",
            description = "Enjoy private, air-conditioned convenience for court hearings, hospital rounds, university visits, or shopping sprees. Your polite, vetted driver stays on call all day, handles parking, and knows every Islamabad sector.",
            pricePkr = 6500,
            priceUsd = 24,
            priceUnit = "per day (12 hrs)",
            budgetTier = BudgetTier.COMFORT_STANDARD,
            includedFeatures = listOf(
                "Clean, air-conditioned Toyota Corolla or Yaris sedan",
                "Experienced, courteous local driver familiar with all sectors",
                "Flexible timing for hospital visits, court appearances & shopping",
                "Fuel packages available; inter-city rates on request"
            ),
            vehicleOrType = "Private Sedan with Driver",
            isPopular = true
        ),
        TravelService(
            id = "airport_isb_transfer",
            title = "Islamabad Airport (ISB) Round-Trip Transfer",
            category = "Transport & Transit",
            subtitle = "Reliable 24/7 meet & greet flight transfers",
            description = "Direct, stress-free transfers between Islamabad International Airport (ISB) and your hotel or residence. We track flight arrival delays so your driver is waiting at the terminal exit with your name board.",
            pricePkr = 3500,
            priceUsd = 13,
            priceUnit = "per transfer",
            budgetTier = BudgetTier.COMFORT_STANDARD,
            includedFeatures = listOf(
                "24/7 flight arrival monitoring with zero wait charges for delays",
                "Luggage assistance and comfortable AC executive sedan",
                "Direct express drop to any sector, hotel or hospital",
                "Round-trip discount packages available"
            ),
            vehicleOrType = "Airport Shuttle Sedan",
            isPopular = true
        ),
        TravelService(
            id = "luxury_4x4_suv",
            title = "Luxury 4x4 SUV (Toyota Prado / Fortuner)",
            category = "Transport & Transit",
            subtitle = "VIP mountain transit for Murree, Galyat & Northern routes",
            description = "High-clearance four-wheel-drive luxury SUVs driven by certified mountain chauffeurs. Perfect for executive delegations, diplomats, or families exploring the steep winding roads of Murree, Nathia Gali, Babusar, and Hunza.",
            pricePkr = 18000,
            priceUsd = 65,
            priceUnit = "per day with driver",
            budgetTier = BudgetTier.EXECUTIVE_VIP,
            includedFeatures = listOf(
                "Toyota Prado TX or Fortuner 4x4 with leather interior",
                "Expert mountain driver skilled in alpine road navigation",
                "All toll taxes, parking permits & driver allowances included",
                "High ground clearance and smooth power for alpine terrain"
            ),
            vehicleOrType = "Luxury 4x4 Prado / Fortuner",
            isPopular = true
        ),
        TravelService(
            id = "hiace_grand_cabin",
            title = "Toyota Hiace Grand Cabin (14-Seater Group)",
            category = "Transport & Transit",
            subtitle = "Spacious group van for large families, wedding & study tours",
            description = "Spacious high-roof 14-seater Grand Cabin with dual air conditioning, reclining velvet seats, and abundant luggage space. Ideal for large families coming from Karachi, Lahore, or Peshawar for group events or Northern excursions.",
            pricePkr = 16000,
            priceUsd = 58,
            priceUnit = "per day with driver",
            budgetTier = BudgetTier.COMFORT_STANDARD,
            includedFeatures = listOf(
                "14 passenger high-roof cabin with individual AC vents",
                "Reclining executive seats & large rear luggage trunk",
                "Professional long-distance highway and mountain driver",
                "Perfect for university delegations, family weddings & tours"
            ),
            vehicleOrType = "Group Van (14 Passengers)",
            isPopular = false
        ),

        // --- Accommodation for ALL Budgets ---
        TravelService(
            id = "budget_guest_house",
            title = "Budget Patient & Student Guest House",
            category = "Stays & Hotels",
            subtitle = "Clean, safe rooms near hospitals, universities & courts",
            description = "Affordable, clean guest rooms in G-8, H-8, I-8, and F-8 sectors. Tailored for families visiting patients at PIMS or Shifa, students taking exams, and lawyers appearing in court. Includes clean linen, attached bath, and WiFi.",
            pricePkr = 3200,
            priceUsd = 12,
            priceUnit = "per night",
            budgetTier = BudgetTier.BUDGET_SAVER,
            includedFeatures = listOf(
                "Furnished room with attached clean bathroom & hot water",
                "High-speed WiFi and standby backup generator / UPS",
                "Walking or short ride distance to major hospitals and courts",
                "Homestyle kitchen food options available on order"
            ),
            vehicleOrType = "Budget Guest House",
            isPopular = true
        ),
        TravelService(
            id = "midscale_boutique_hotel",
            title = "3-Star Boutique Hotel & Serviced Suite",
            category = "Stays & Hotels",
            subtitle = "Modern executive comfort in F-7, F-8 & Blue Area",
            description = "Stylish 3-star boutique hotels and Centaurus serviced residences. Perfect for visiting business professionals, shoppers, and families seeking central comfort, complimentary buffet breakfast, and 24/7 room service.",
            pricePkr = 8500,
            priceUsd = 30,
            priceUnit = "per night",
            budgetTier = BudgetTier.COMFORT_STANDARD,
            includedFeatures = listOf(
                "Executive king/twin room with LED TV, mini fridge & desk",
                "Complimentary daily buffet breakfast for 2 guests",
                "Prime location close to Centaurus Mall and Jinnah Super",
                "Free high-speed WiFi, laundry services & 24/7 concierge"
            ),
            vehicleOrType = "Boutique Executive Hotel",
            isPopular = true
        ),
        TravelService(
            id = "luxury_5star_hotel",
            title = "5-Star Luxury Retreat (Serena Hotel / Marriott)",
            category = "Stays & Hotels",
            subtitle = "World-class hospitality, spa, security & diplomacy hub",
            description = "Experience legendary Pakistani hospitality and world-class luxury at Islamabad Serena Hotel or Islamabad Marriott. Features exquisite Islamic architecture, gourmet restaurants (Zamana, Dawat, Wild Rice), heated pool, and presidential security.",
            pricePkr = 38000,
            priceUsd = 135,
            priceUnit = "per night",
            budgetTier = BudgetTier.EXECUTIVE_VIP,
            includedFeatures = listOf(
                "Deluxe luxury room with garden or Margalla mountain view",
                "Lavish international buffet breakfast at Zamana / Nadia",
                "Complimentary access to Maisha Spa, gym & heated pool",
                "Airport executive limousine shuttle transfer"
            ),
            vehicleOrType = "5-Star Luxury Hotel",
            isPopular = true
        ),
        TravelService(
            id = "nathia_pine_chalet",
            title = "Nathia Gali Pine Chalet & Alpine Lodge",
            category = "Stays & Hotels",
            subtitle = "Cozy wooden suites nestled deep in pine forests",
            description = "Charming alpine log chalets nestled among the towering pine and deodar trees of Nathia Gali and Murree. Enjoy misty mountain morning views, crackling fireplaces, and steaming tea on private balconies.",
            pricePkr = 18000,
            priceUsd = 65,
            priceUnit = "per night",
            budgetTier = BudgetTier.COMFORT_STANDARD,
            includedFeatures = listOf(
                "Wooden chalet suite with panoramic pine valley view",
                "Evening bonfire and hot Pakistani breakfast included",
                "Room heating / fireplace for chilly mountain nights",
                "Walking distance to Nathia Gali club & hiking tracks"
            ),
            vehicleOrType = "Alpine Mountain Chalet",
            isPopular = true
        ),

        // --- Guided Custom Packages & Dining ---
        TravelService(
            id = "tailored_full_trip",
            title = "Tailor-Made Round-Trip Visitor Package",
            category = "Guided Packages",
            subtitle = "Customized transport, hotel, meals & schedule for your budget",
            description = "Whatever your reason for visiting Islamabad—a 2-day medical checkup, a high court case, university admissions, or a family holiday—we tailor your entire schedule, transport, and hotel to match your exact budget and dates.",
            pricePkr = 12000,
            priceUsd = 45,
            priceUnit = "custom package from",
            budgetTier = BudgetTier.COMFORT_STANDARD,
            includedFeatures = listOf(
                "Custom itinerary designed around your appointments & schedule",
                "Pre-arranged transport (Metro pass, inDrive, or private car)",
                "Confirmed hotel or guest house matching your budget tier",
                "24/7 WhatsApp & helpline support throughout your stay"
            ),
            vehicleOrType = "Bespoke Custom Visit Package",
            isPopular = true
        ),
        TravelService(
            id = "melody_food_tour",
            title = "Melody & Rawalpindi Food Street Experience",
            category = "Dining & Food",
            subtitle = "Authentic Chapli Kabab, Shinwari Karahi, Sajji & Falooda",
            description = "Explore the legendary culinary flavors of Islamabad and Rawalpindi. From sizzling Namak Mandi Shinwari Karahi in Blue Area to Melody Food Street savories and Kartarpura breakfast Siri Paye.",
            pricePkr = 2500,
            priceUsd = 9,
            priceUnit = "per person",
            budgetTier = BudgetTier.BUDGET_SAVER,
            includedFeatures = listOf(
                "Guided food trail with local foodie host",
                "Tasting of famous Shinwari Karahi, Chapli Kabab & Dum Pukht",
                "Traditional Kashmiri Chai with pistachios & fresh Falooda",
                "Hygiene vetted food stalls and iconic dining spots"
            ),
            vehicleOrType = "Food & Dining Experience",
            isPopular = false
        )
    )

    val emergencyContacts = listOf(
        Pair("Islamabad Police Emergency", "15"),
        Pair("Rescue & Ambulance Service", "1122"),
        Pair("PIMS Hospital Emergency", "+92 51 9261170"),
        Pair("Shifa International Hospital Emergency", "+92 51 8463000"),
        Pair("Islamabad High Court Info Desk", "+92 51 9108000"),
        Pair("Islamabad Tourist Police Help Desk", "+92 51 9100008"),
        Pair("Islamabad Airport (ISB) Flight Inquiry", "+92 51 95551000"),
        Pair("Islamabad Metro Bus Helpline", "+92 51 111-222-628"),
        Pair("Murree Highway & Emergency Desk", "051-9269016"),
        Pair("Islamabad Traffic Police Helpline", "1915")
    )

    val officialForums = listOf(
        SocialForum(
            id = "call_support",
            name = "24/7 Helpline & Cell",
            handleOrTarget = OfficialContacts.OFFICIAL_PHONE,
            category = "Instant Support",
            description = "Direct telephone line for emergency bookings, visitor advice, hospital transit and custom trip schedules.",
            actionType = ActionType.CALL_PHONE,
            actionUrl = "tel:${OfficialContacts.OFFICIAL_PHONE_INTL}",
            isPrimarySupport = true
        ),
        SocialForum(
            id = "whatsapp",
            name = "WhatsApp Official Desk",
            handleOrTarget = OfficialContacts.OFFICIAL_PHONE_INTL,
            category = "Instant Messaging",
            description = "Connect directly with our 24/7 visitor concierge on WhatsApp. Get instant quotes, hotel bookings, and transit assistance.",
            actionType = ActionType.OPEN_WHATSAPP,
            actionUrl = "https://wa.me/923457059286?text=Hi%20Islamabad%20Gateway!%20I%20am%20planning%20a%20visit%20to%20Islamabad%20and%20need%20assistance.",
            isPrimarySupport = true
        ),
        SocialForum(
            id = "telegram",
            name = "Telegram Channel & Bot",
            handleOrTarget = "@islamabadgateway",
            category = "Instant Messaging",
            description = "Instant Telegram support for domestic and international travelers. Live agent inquiries and booking dispatch.",
            actionType = ActionType.OPEN_TELEGRAM,
            actionUrl = "https://t.me/+923457059286",
            isPrimarySupport = true
        ),
        SocialForum(
            id = "email_official",
            name = "Official Email Desk",
            handleOrTarget = OfficialContacts.OFFICIAL_EMAIL,
            category = "Corporate & Inquiries",
            description = "Official communication channel for corporate bookings, hospital itineraries, legal tour reservations and formal receipts.",
            actionType = ActionType.SEND_EMAIL,
            actionUrl = "mailto:${OfficialContacts.OFFICIAL_EMAIL}?subject=Islamabad%20Visitor%20Inquiry%20-%20Custom%20Plan",
            isPrimarySupport = true
        ),
        SocialForum(
            id = "sms_support",
            name = "Direct SMS Dispatch",
            handleOrTarget = OfficialContacts.OFFICIAL_PHONE,
            category = "Instant Support",
            description = "Send instant SMS text with your arrival date, purpose of visit, and required services.",
            actionType = ActionType.SEND_SMS,
            actionUrl = "sms:${OfficialContacts.OFFICIAL_PHONE_INTL}?body=Hi%20Islamabad%20Gateway,%20I%20am%20visiting%20Islamabad%20and%20need%20assistance."
        ),
        SocialForum(
            id = "instagram",
            name = "Instagram Community",
            handleOrTarget = "@islamabadgateway",
            category = "Social Media",
            description = "Follow daily updates on Islamabad city life, weather forecasts, sector guides, hospital updates & Margalla trails.",
            actionType = ActionType.OPEN_URL,
            actionUrl = "https://instagram.com/exploreislmbd"
        ),
        SocialForum(
            id = "facebook",
            name = "Facebook Official Page",
            handleOrTarget = "Visit Islamabad",
            category = "Social Media",
            description = "Official Facebook page featuring traveler reviews, transport rate guides, hospital info, and visitor updates.",
            actionType = ActionType.OPEN_URL,
            actionUrl = "https://facebook.com/visitislamabad"
        ),
        SocialForum(
            id = "tiktok",
            name = "TikTok Travel Clips",
            handleOrTarget = "@visitislamabad",
            category = "Social Media",
            description = "Quick video guides of metro bus rides, hospital corridors, shopping markets, hiking trails, and Murree routes.",
            actionType = ActionType.OPEN_URL,
            actionUrl = "https://tiktok.com/@visitislamabad"
        ),
        SocialForum(
            id = "google_maps",
            name = "Google Maps & Routes",
            handleOrTarget = "Islamabad Capital Territory",
            category = "Navigation & Maps",
            description = "Live navigation to Faisal Mosque, Supreme Court, PIMS, Shifa, Centaurus, and Northern expressway routes.",
            actionType = ActionType.OPEN_URL,
            actionUrl = "https://www.google.com/maps/dir/?api=1&destination=Faisal+Mosque+Islamabad"
        ),
        SocialForum(
            id = "google_search",
            name = "Google Search & Profile",
            handleOrTarget = "Visit Islamabad Hub",
            category = "Search & Discovery",
            description = "Verified online presence and Google business profile for visitor assistance across Pakistan.",
            actionType = ActionType.OPEN_URL,
            actionUrl = "https://www.google.com/search?q=Visit+Islamabad+gateway+visitors+all+purposes"
        )
    )

    val testimonials = listOf(
        com.example.model.VisitorTestimonial(
            id = "test_medical",
            name = "Haji Muhammad Rafiq & Family",
            origin = "Multan, Southern Punjab",
            purpose = "Shifa International Hospital OPD Follow-up",
            category = "Medical Checkup",
            avatarInitials = "MR",
            rating = 5,
            review = "We traveled from Multan for my father's cardiac procedure at Shifa. Finding accessible lodging and punctual transport used to be so stressful. Visit Islamabad booked us into a clean, ground-floor guest house just 6 minutes away and coordinated wheelchair-ready cab pickups right on time. A true blessing for medical visitors.",
            tripSummaryPills = listOf("🏥 Shifa Hospital", "♿ Wheelchair Cab", "🏡 Family Guest House")
        ),
        com.example.model.VisitorTestimonial(
            id = "test_legal",
            name = "Advocate M. Tariq Qureshi",
            origin = "Lahore High Court Bar",
            purpose = "Supreme Court of Pakistan & IHC Appearance",
            category = "Courts & Legal",
            avatarInitials = "TQ",
            rating = 5,
            review = "Appearing before the Supreme Court at Constitution Avenue demands zero logistical friction. The app arranged a dedicated executive sedan with a driver who understood red zone security cordons and gate entry timings. Having our itinerary and stay near the Secretariat coordinated in advance made the hearing day completely smooth.",
            tripSummaryPills = listOf("🏛️ Supreme Court / IHC", "🚗 Executive Sedan", "💼 Red Zone Hotel")
        ),
        com.example.model.VisitorTestimonial(
            id = "test_student",
            name = "Zoya Khan",
            origin = "Peshawar, KPK",
            purpose = "NUST (H-12) & FAST NET Entrance Exam",
            category = "University Entrance",
            avatarInitials = "ZK",
            rating = 5,
            review = "Traveling solo from KPK for the NUST NET exam at H-12 was daunting on a student budget. Visit Islamabad explained how to take the PKR 50 Orange Line Metro Bus from Daewoo terminal right to the campus gates and guided me to a safe, verified girls' hostel nearby. It saved me thousands of rupees and kept me totally safe.",
            tripSummaryPills = listOf("🎓 NUST H-12 / FAST", "🚌 PKR 50 Metro Bus", "🔒 Safe Girls' Hostel")
        ),
        com.example.model.VisitorTestimonial(
            id = "test_tourism",
            name = "Farhan & Ayesha Sheikh",
            origin = "Karachi, Sindh",
            purpose = "Capital Sightseeing & 4x4 Hunza Excursion",
            category = "Tourism & North",
            avatarInitials = "FS",
            rating = 5,
            review = "We flew into Islamabad with our children before heading to Hunza. The team picked us up from ISB Airport, arranged 2 days of relaxed sightseeing at Faisal Mosque, Monal, and Lok Virsa, then provided a rugged 4x4 Prado with an expert mountain driver for the Karakoram Highway. Transparent pricing and warm hospitality!",
            tripSummaryPills = listOf("✈️ Airport Pick & Drop", "🚙 4x4 Prado Fleet", "⭐ Family Tour")
        )
    )
}

package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold

data class FaqItem(
    val id: String,
    val question: String,
    val answer: String,
    val category: String, // "Logistics & Transit", "Visa & Consular", "Hospital & Emergency", "General Support"
    val icon: ImageVector,
    val keyPoints: List<String> = emptyList(),
    val actionButtonText: String? = null,
    val actionIntentUrl: String? = null
)

/**
 * Accordion-style Frequently Asked Questions section.
 * Shows detailed, professional answers regarding logistics, visa assistance,
 * and hospital emergency protocols when clicked.
 */
@Composable
fun FaqAccordionSection(
    modifier: Modifier = Modifier,
    onNavigateToAI: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedFaqIds by remember { mutableStateOf(setOf<String>("faq_logistics_metro", "faq_visa_diplomatic", "faq_hospital_emergency")) }

    val categories = listOf("All", "Logistics & Transit", "Visa & Consular", "Hospital & Emergency", "General Support")

    val faqList = remember {
        listOf(
            // --- 1. Logistics & Transit ---
            FaqItem(
                id = "faq_logistics_metro",
                question = "How do I navigate Islamabad via the Metro Bus and electric feeder fleet?",
                category = "Logistics & Transit",
                icon = Icons.Default.DirectionsBus,
                answer = "Islamabad features a comprehensive, state-of-the-art Bus Rapid Transit (BRT) network with flat fares starting from PKR 50. The Red Line connects Rawalpindi Saddar to Pak Secretariat through Blue Area and PIMS Hospital. The Orange Line links Peshawar Morr directly to Islamabad International Airport in 35 minutes. CDA Electric Feeder Buses circulate throughout Sectors F-6, F-7, F-8, F-10, G-9, G-11, and I-8.",
                keyPoints = listOf(
                    "Red Line: Pak Secretariat to Rawalpindi Saddar (stops at PIMS & Stock Exchange)",
                    "Orange Line: Rapid direct route to Islamabad International Airport (ISB)",
                    "Flat Fare: PKR 50 per trip with integrated electronic ticketing",
                    "CDA Electric Fleet: Clean feeder connectivity linking inner sector markets"
                ),
                actionButtonText = "View Metro Route Guide",
                actionIntentUrl = "https://visitislamabad.web.app/#partners-section"
            ),
            FaqItem(
                id = "faq_logistics_airport",
                question = "What are the most reliable airport transfer options to central sectors?",
                category = "Logistics & Transit",
                icon = Icons.Default.DirectionsBus,
                answer = "Islamabad International Airport (ISB) is situated 25 km west of the central sectors. You can reach the city center via the Orange Line Metro (Terminal Level 1 departure, PKR 50), on-demand inDrive and Yango app rides (PKR 1,800 – 2,800), or by booking a dedicated chauffeur sedan through our 24/7 concierge for luggage care and meet-and-greet.",
                keyPoints = listOf(
                    "Orange Line Metro: Economical PKR 50 terminal transit to Srinagar Highway",
                    "inDrive & Yango: Available 24/7 at International & Domestic arrival lanes",
                    "Dedicated Chauffeur: Pre-booked executive pickup with round-the-clock greeting"
                ),
                actionButtonText = "Call Airport Desk (0345-7059286)",
                actionIntentUrl = "tel:+923457059286"
            ),
            FaqItem(
                id = "faq_logistics_northern",
                question = "How do outstation departures to Murree, Galyat, and Northern Areas work?",
                category = "Logistics & Transit",
                icon = Icons.Default.LocationCity,
                answer = "Islamabad is the premier launching point for expeditions to Murree (1 hr via N-75 Expressway), Nathia Gali, Kaghan Valley, Swat, Hunza, and Skardu. Inspected 4x4 Prado SUVs and Toyota Grand Cabin Hiace vans with experienced mountain chauffeurs can be arranged through our round-trip services desk.",
                keyPoints = listOf(
                    "Murree Expressway (N-75): Scenic dual carriageway from Islamabad to Murree & Galyat",
                    "Hazara Motorway (M-15): Direct route towards Abbottabad, Naran & Babusar Pass",
                    "Mountain Fleet: Inspected 4x4 vehicles with high-altitude licensed drivers"
                ),
                actionButtonText = "Chat on WhatsApp for 4x4 Fleet",
                actionIntentUrl = "https://wa.me/923457059286?text=Hello!%20I%20need%204x4%20transport%20for%20Northern%20Areas."
            ),

            // --- 2. Visa & Consular Assistance ---
            FaqItem(
                id = "faq_visa_diplomatic",
                question = "What is the procedure for entering the Diplomatic Enclave for embassy visa interviews?",
                category = "Visa & Consular",
                icon = Icons.Default.Gavel,
                answer = "Foreign embassies (US, UK, Schengen, UAE, Saudi Arabia, etc.) are located within the secured Diplomatic Enclave in Sector G-5. General private vehicles are strictly restricted. Visitors must enter exclusively via the official Diplomatic Shuttle Service situated at Avenue 3 Gate.",
                keyPoints = listOf(
                    "Entry Location: Diplomatic Shuttle Service Terminal, Avenue 3, Sector G-5",
                    "Mandatory Documents: Original CNIC or Passport + printed embassy appointment confirmation",
                    "Shuttle Ticket: PKR 500 – 1,000 for standard or executive air-conditioned shuttle buses",
                    "Locker Facilities: Secure electronic storage available for mobile phones and luggage"
                ),
                actionButtonText = "Open Shuttle Gate on Maps",
                actionIntentUrl = "https://www.google.com/maps/search/?api=1&query=Diplomatic+Shuttle+Service+Islamabad"
            ),
            FaqItem(
                id = "faq_visa_attestation",
                question = "Where are academic degrees, legal documents, and police certificates attested?",
                category = "Visa & Consular",
                icon = Icons.Default.Public,
                answer = "Key government attestation authorities are located in central capital sectors: HEC in Sector H-9 attests higher education degrees; MOFA on Constitution Avenue handles marriage, birth, and non-encumbrance certificates; and IBCC in G-10/4 verifies intermediate and secondary school certificates.",
                keyPoints = listOf(
                    "HEC (Higher Education Commission): Sector H-9 (Degree attestation & equivalence)",
                    "MOFA (Ministry of Foreign Affairs): Constitution Avenue (Consular legalization)",
                    "IBCC (Inter Board Coordination): Sector G-10/4 (Matric & Intermediate certificates)"
                ),
                actionButtonText = "Consult AI Consular Advisor",
                actionIntentUrl = null
            ),

            // --- 3. Hospital Emergency Protocols ---
            FaqItem(
                id = "faq_hospital_emergency",
                question = "What are the immediate protocols for hospital emergencies and ambulance dispatch?",
                category = "Hospital & Emergency",
                icon = Icons.Default.LocalHospital,
                answer = "In medical emergencies, dial 1122 immediately for free, rapid government paramedic and ambulance dispatch throughout Islamabad. For specialized care, direct your driver to PIMS Hospital (Sector G-8) for public tertiary emergency trauma, or Shifa International Hospital (Sector H-8) for private critical resuscitation.",
                keyPoints = listOf(
                    "Emergency Hotline: Dial 1122 for 24/7 free paramedic ambulance dispatch",
                    "PIMS Hospital: National Burn Center, Children's Hospital & Trauma Center (Red Line Metro)",
                    "Shifa International: JCI-accredited tertiary care, emergency cardiac catheterization & stroke bay",
                    "Maroof International: Sector F-10 Markaz executive emergency department"
                ),
                actionButtonText = "Call Emergency 1122 Immediately",
                actionIntentUrl = "tel:1122"
            ),
            FaqItem(
                id = "faq_hospital_patient_stays",
                question = "Are wheelchair-friendly accommodations and 24/7 pharmacies available near hospitals?",
                category = "Hospital & Emergency",
                icon = Icons.Default.LocalHospital,
                answer = "Yes. Major medical hubs feature extensive patient support infrastructure. Wheelchairs and patient porters are stationed at hospital OPD gates. Proximity patient guest houses with ground-floor accessible rooms operate along Pitras Bukhari Road (H-8) and F-10 Markaz. Shaheen Chemist and D.Watson operate 24/7 pharmacies across central sectors.",
                keyPoints = listOf(
                    "Pitras Bukhari Road (H-8): Patient lodging complexes within 3 minutes of Shifa Hospital",
                    "Sector F-10 Markaz: Serviced apartments with lift access and kitchen amenities",
                    "24/7 Pharmacy Hubs: Shaheen Chemist, D.Watson & Clinix in Blue Area and F-10"
                ),
                actionButtonText = "Call Patient Coordinator",
                actionIntentUrl = "tel:+923457059286"
            ),

            // --- 4. General Support ---
            FaqItem(
                id = "faq_general_concierge",
                question = "How does the Visit Islamabad 24/7 Concierge and helpline assist visitors?",
                category = "General Support",
                icon = Icons.AutoMirrored.Filled.HelpOutline,
                answer = "Visit Islamabad connects visitors with round-the-clock telephone and WhatsApp guidance at 0345 7059286 (+92 345 7059286), multi-agent AI assistants, customized itinerary architects, and verified transit/hotel partners. Travelers can also share inquiries via WhatsApp or use the web platform at visitislamabad.web.app.",
                keyPoints = listOf(
                    "Official Concierge Hotline: 0345 7059286 (Call, WhatsApp & Telegram)",
                    "Official Email: info.letsselll@gmail.com",
                    "Live Web Platform: https://visitislamabad.web.app/"
                ),
                actionButtonText = "Open WhatsApp Helpline",
                actionIntentUrl = "https://wa.me/923457059286?text=Hello%20Visit%20Islamabad!%20I%20have%20an%20inquiry."
            )
        )
    }

    val filteredFaqs = if (selectedCategory == "All") {
        faqList
    } else {
        faqList.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("faq_accordion_section")
    ) {
        // Section Header
        Surface(
            color = PinePrimary.copy(alpha = 0.12f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Quiz,
                    contentDescription = null,
                    tint = PinePrimary,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "VISITOR KNOWLEDGE BASE & PROTOCOLS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PinePrimary,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Frequently Asked Questions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Tap on any question below to view verified answers regarding city logistics, embassy visa protocols, hospital emergency procedures, and transit routes.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp),
            lineHeight = 18.sp
        )

        // Category Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories) { cat ->
                FilterChip(
                    selected = (cat == selectedCategory),
                    onClick = { selectedCategory = cat },
                    label = {
                        Text(
                            text = cat,
                            fontSize = 11.5.sp,
                            fontWeight = if (cat == selectedCategory) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PinePrimary,
                        selectedLabelColor = Color.White
                    ),
                    modifier = Modifier.testTag("faq_chip_$cat")
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Accordion Cards List
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            filteredFaqs.forEach { faq ->
                val isExpanded = expandedFaqIds.contains(faq.id)

                FaqAccordionCard(
                    faq = faq,
                    isExpanded = isExpanded,
                    onToggle = {
                        expandedFaqIds = if (isExpanded) {
                            expandedFaqIds - faq.id
                        } else {
                            expandedFaqIds + faq.id
                        }
                    },
                    onActionClick = {
                        if (faq.actionIntentUrl != null) {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(faq.actionIntentUrl))
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                // fallback
                            }
                        } else if (onNavigateToAI != null) {
                            onNavigateToAI()
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FaqAccordionCard(
    faq: FaqItem,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    onActionClick: () -> Unit
) {
    val rotationAngle by animateFloatAsState(targetValue = if (isExpanded) 180f else 0f, label = "arrowRotation")

    val categoryColor = when (faq.category) {
        "Logistics & Transit" -> Color(0xFF0288D1)
        "Visa & Consular" -> Color(0xFF455A64)
        "Hospital & Emergency" -> Color(0xFFD32F2F)
        else -> PinePrimary
    }

    val softCategoryBg = when (faq.category) {
        "Logistics & Transit" -> Color(0xFFE1F5FE)
        "Visa & Consular" -> Color(0xFFECEFF1)
        "Hospital & Emergency" -> Color(0xFFFFEBEE)
        else -> Color(0xFFE8F5E9)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle)
            .testTag("faq_item_${faq.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = if (isExpanded) 1.5.dp else 1.dp,
            color = if (isExpanded) PinePrimary else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isExpanded) 3.dp else 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header Row: Icon + Category + Question + Chevron
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(softCategoryBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = faq.icon,
                        contentDescription = null,
                        tint = categoryColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Surface(
                        color = softCategoryBg,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = faq.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = categoryColor,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = faq.question,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = if (isExpanded) PinePrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )
            }

            // Accordion Collapsible Body
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f))
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = faq.answer,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f),
                        lineHeight = 20.sp
                    )

                    if (faq.keyPoints.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Key Takeaways & Action Points:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.5.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                faq.keyPoints.forEach { point ->
                                    Row(
                                        verticalAlignment = Alignment.Top,
                                        modifier = Modifier.padding(vertical = 1.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = null,
                                            tint = PinePrimary,
                                            modifier = Modifier
                                                .size(14.dp)
                                                .padding(top = 2.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = point,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    if (faq.actionButtonText != null) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = onActionClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (faq.category == "Hospital & Emergency") Color(0xFFD32F2F) else PinePrimary,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = if (faq.actionIntentUrl?.startsWith("tel:") == true) Icons.Default.Call else Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = faq.actionButtonText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

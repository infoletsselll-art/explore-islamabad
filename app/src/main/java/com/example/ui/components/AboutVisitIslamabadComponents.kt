package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AboutVisitIslamabad
import com.example.model.OfficialContacts
import com.example.ui.theme.MountainMint
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold
import com.example.ui.viewmodel.IslamabadViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutVisitIslamabadHeroCard(
    viewModel: IslamabadViewModel,
    modifier: Modifier = Modifier,
    showInteractiveAiGrid: Boolean = true
) {
    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = PinePrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("about_visit_islamabad_hero_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Top Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = WarmGold,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "24/7 AI-POWERED GATEWAY",
                            color = Color.Black,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Surface(
                    color = Color.White.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Global & Local",
                        color = Color.White,
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Title & Subtitle from user provided text
            Text(
                text = AboutVisitIslamabad.TITLE,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 1.sp
            )

            Text(
                text = AboutVisitIslamabad.SUBTITLE,
                style = MaterialTheme.typography.bodyMedium,
                color = WarmGold,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Gateway Slogans Section
            Surface(
                color = Color.White.copy(alpha = 0.12f),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "OUR VISION & PLEDGE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmGold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    AboutVisitIslamabad.GATEWAY_SLOGANS.forEach { slogan ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = WarmGold,
                                modifier = Modifier
                                    .size(15.dp)
                                    .padding(top = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = slogan,
                                color = Color.White,
                                fontSize = 12.5.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 24/7 Global Services
            Surface(
                color = Color(0xFF0D3D2C),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = null,
                            tint = MountainMint,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "24/7 Global Services & Tailor-Based Pre-Booking",
                            color = MountainMint,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = AboutVisitIslamabad.GLOBAL_SERVICES,
                        color = Color.White.copy(alpha = 0.95f),
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // AI Guidance & Task Execution
            Surface(
                color = Color(0xFF1E3A8A).copy(alpha = 0.45f),
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = null,
                            tint = WarmGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Our AI Agents Do Each & Every Task in Minutes",
                            color = WarmGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = AboutVisitIslamabad.AI_AGENTS_DESCRIPTION,
                        color = Color.White,
                        fontSize = 12.sp,
                        lineHeight = 17.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Serving Every Purpose
            Surface(
                color = Color.White.copy(alpha = 0.12f),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "FOR EVERY VISITOR & PURPOSE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = WarmGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = AboutVisitIslamabad.MULTI_PURPOSE_SUMMARY,
                        color = Color.White,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }

            if (showInteractiveAiGrid) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "🤖 ASK LIVE AI AGENT TO PLAN & BOOK (1-CLICK TASK EXECUTION):",
                    color = WarmGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Action FlowRow with all tasks mentioned in user text
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AiTaskChip(
                        icon = Icons.Default.DirectionsCar,
                        label = "Transportation",
                        onClick = {
                            viewModel.askLiveAgentToPlanAndBookTask(
                                "Transportation",
                                "Hello! I need transportation in Islamabad (Metro Bus, inDrive/Yango, chauffeur car, or 4x4). Please provide complete rates and plan my transit."
                            )
                        }
                    )

                    AiTaskChip(
                        icon = Icons.Default.Hotel,
                        label = "Hotels & Stays",
                        onClick = {
                            viewModel.askLiveAgentToPlanAndBookTask(
                                "Stays",
                                "Hello! I need feasible staying facilities in Islamabad (guest house near hospitals, student hostels, or boutique suites). Please suggest and help me book."
                            )
                        }
                    )

                    AiTaskChip(
                        icon = Icons.Default.ShoppingBag,
                        label = "Shopping & Malls",
                        onClick = {
                            viewModel.askLiveAgentToPlanAndBookTask(
                                "Shopping",
                                "Hello! Please give me all information about shopping in Islamabad: Centaurus Mall, Giga Mall, F-6/F-7 Super Markets, and Raja Bazaar wholesale."
                            )
                        }
                    )

                    AiTaskChip(
                        icon = Icons.Default.Restaurant,
                        label = "Dining & Food",
                        onClick = {
                            viewModel.askLiveAgentToPlanAndBookTask(
                                "Dining",
                                "Hello! I want dining recommendations in Islamabad: Melody Food Street, Saidpur Village, clifftop Monal, and Blue Area cafes."
                            )
                        }
                    )

                    AiTaskChip(
                        icon = Icons.Default.LocalHospital,
                        label = "Medical Visits",
                        onClick = {
                            viewModel.askLiveAgentToPlanAndBookTask(
                                "Medical",
                                "Hello! I am visiting Islamabad for medical consultations (Shifa, PIMS, Maroof, Kulsum). Please plan my appointments, patient stays, and transport."
                            )
                        }
                    )

                    AiTaskChip(
                        icon = Icons.Default.AccountBalance,
                        label = "Courts & Hearings",
                        onClick = {
                            viewModel.askLiveAgentToPlanAndBookTask(
                                "Courts",
                                "Hello! I am visiting Islamabad for Supreme Court or High Court proceedings. Please guide me on Red Zone entry, law chambers, and quiet stays."
                            )
                        }
                    )

                    AiTaskChip(
                        icon = Icons.Default.School,
                        label = "University Exams",
                        onClick = {
                            viewModel.askLiveAgentToPlanAndBookTask(
                                "Exams",
                                "Hello! I am coming to Islamabad for university entrance examinations (NUST, FAST, QAU). Please plan my test route and budget hostel stay."
                            )
                        }
                    )

                    AiTaskChip(
                        icon = Icons.Default.Landscape,
                        label = "Majestic Northern Areas",
                        onClick = {
                            viewModel.askLiveAgentToPlanAndBookTask(
                                "Northern",
                                "Hello! I am travelling onward to the majestic Northern Areas (Murree, Nathia Gali, Kaghan, Swat, Hunza). Please plan my 4x4 expedition."
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Direct Call & WhatsApp 24/7 hotline bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Official Web: ${OfficialContacts.DOMAIN_NAME}",
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                Button(
                    onClick = { viewModel.selectTab(1) },
                    colors = ButtonDefaults.buttonColors(containerColor = WarmGold, contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Chat,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Talk to Live AI Agent",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 11.5.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AiTaskChip(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.White.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.3f)),
        modifier = Modifier
            .clickable(onClick = onClick)
            .testTag("ai_task_chip_${label.lowercase().replace(" ", "_")}")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = WarmGold,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 11.5.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun AboutVisitIslamabadDialog(
    viewModel: IslamabadViewModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag("about_visit_islamabad_dialog"),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(WarmGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = AboutVisitIslamabad.TITLE,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = PinePrimary
                        )
                        Text(
                            text = "Introduction & Purpose",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                AboutVisitIslamabadHeroCard(
                    viewModel = viewModel,
                    showInteractiveAiGrid = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                    viewModel.selectTab(1) // Move to live AI chat
                },
                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ask Live AI Agent")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Close")
            }
        }
    )
}

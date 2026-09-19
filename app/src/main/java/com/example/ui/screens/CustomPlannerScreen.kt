package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MountainMint
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold
import com.example.ui.viewmodel.IslamabadViewModel

@Composable
fun CustomPlannerScreen(viewModel: IslamabadViewModel) {
    val form by viewModel.customTourForm.collectAsState()
    val generatedPlan by viewModel.generatedCustomPlan.collectAsState()
    val isGenerating by viewModel.isGeneratingPlan.collectAsState()
    val context = LocalContext.current

    val origins = listOf(
        "Overseas Tourist (UK/US/Gulf)",
        "Karachi / Sindh",
        "Lahore / Punjab",
        "Peshawar / KPK",
        "Other International"
    )

    val durations = listOf(1, 2, 3, 5, 7, 10)

    val groupTypes = listOf(
        "Family with Kids",
        "Honeymoon Couple",
        "Solo Explorer",
        "Adventure Friends",
        "Corporate Group"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("custom_planner_screen"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Banner
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PinePrimary)
                    .padding(20.dp)
            ) {
                Surface(
                    color = WarmGold,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "TAILORED TOUR ARCHITECT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "explore Islmbd • Custom Tour Generator",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Design a bespoke travel package customized exactly to your needs, budget, traveling party, and dates. Backed by 24/7 AI Automation and dedicated travel desks across Pakistan (Hotline: 03457059286).",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        // Form Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "1. Tourist Origin / Arrival City",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(origins) { orig ->
                            FilterChip(
                                selected = (form.origin == orig),
                                onClick = { viewModel.updateTourRequirement(form.copy(origin = orig)) },
                                label = { Text(orig, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Text(
                        text = "2. Duration (Days)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(durations) { days ->
                            FilterChip(
                                selected = (form.durationDays == days),
                                onClick = { viewModel.updateTourRequirement(form.copy(durationDays = days)) },
                                label = { Text("$days ${if (days == 1) "Day" else "Days"}", fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Text(
                        text = "3. Traveling Party Type",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(groupTypes) { grp ->
                            FilterChip(
                                selected = (form.travelGroupType == grp),
                                onClick = { viewModel.updateTourRequirement(form.copy(travelGroupType = grp)) },
                                label = { Text(grp, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    Text(
                        text = "4. Desired Tailored Services",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = form.includePickAndDrop,
                                onCheckedChange = { viewModel.updateTourRequirement(form.copy(includePickAndDrop = it)) },
                                colors = CheckboxDefaults.colors(checkedColor = PinePrimary)
                            )
                            Text("Airport Pick & Drop (Islamabad Airport ISB)", fontSize = 13.sp)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = form.includeHotel,
                                onCheckedChange = { viewModel.updateTourRequirement(form.copy(includeHotel = it)) },
                                colors = CheckboxDefaults.colors(checkedColor = PinePrimary)
                            )
                            Text("Hotel / Mountain Pine Chalet Booking", fontSize = 13.sp)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = form.includeDedicatedCar,
                                onCheckedChange = { viewModel.updateTourRequirement(form.copy(includeDedicatedCar = it)) },
                                colors = CheckboxDefaults.colors(checkedColor = PinePrimary)
                            )
                            Text("Dedicated 4x4 / AC Chauffeur Car with Driver", fontSize = 13.sp)
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = form.includeCamping,
                                onCheckedChange = { viewModel.updateTourRequirement(form.copy(includeCamping = it)) },
                                colors = CheckboxDefaults.colors(checkedColor = PinePrimary)
                            )
                            Text("Alpine Camping, Bonfire & Trekking Guide", fontSize = 13.sp)
                        }
                    }

                    OutlinedTextField(
                        value = form.clientName,
                        onValueChange = { viewModel.updateTourRequirement(form.copy(clientName = it)) },
                        label = { Text("Your Name") },
                        modifier = Modifier.fillMaxWidth().testTag("planner_client_name"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = form.phoneOrContact,
                        onValueChange = { viewModel.updateTourRequirement(form.copy(phoneOrContact = it)) },
                        label = { Text("WhatsApp / Contact Phone") },
                        placeholder = { Text("+92 300 0000000") },
                        modifier = Modifier.fillMaxWidth().testTag("planner_contact_phone"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = form.travelDates,
                        onValueChange = { viewModel.updateTourRequirement(form.copy(travelDates = it)) },
                        label = { Text("Planned Travel Dates (e.g. October 15-18)") },
                        modifier = Modifier.fillMaxWidth().testTag("planner_dates"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = form.specialNotes,
                        onValueChange = { viewModel.updateTourRequirement(form.copy(specialNotes = it)) },
                        label = { Text("Special Desires / Dietary / Halal / Preferences") },
                        placeholder = { Text("e.g. Want quiet mountain views, elder accessible, local food stops...") },
                        modifier = Modifier.fillMaxWidth().testTag("planner_notes"),
                        maxLines = 3
                    )

                    Button(
                        onClick = { viewModel.generateCustomItinerary() },
                        enabled = !isGenerating,
                        colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("generate_custom_plan_button")
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Agent Architecting Plan...", fontWeight = FontWeight.Bold)
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Tailored Tour with AI", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Generated Itinerary Display
        generatedPlan?.let { plan ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .testTag("generated_plan_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Surface(
                                color = WarmGold,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "TAILORED PLAN READY",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "${form.durationDays}-Day Customized Itinerary",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = PinePrimary
                        )

                        Text(
                            text = "Crafted for ${form.travelGroupType} • Origin: ${form.origin}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = plan,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { viewModel.saveCustomPlanAsInquiry() },
                                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("save_custom_plan_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Save to Inquiries")
                            }
                        }
                    }
                }
            }
        }
    }
}

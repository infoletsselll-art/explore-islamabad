package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Terrain
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
import com.example.model.BudgetTier
import com.example.model.OfficialContacts
import com.example.model.VisitorPurpose
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
        "Punjab (Lahore/FSD/Multan)",
        "Sindh (Karachi/Hyd)",
        "KPK (Peshawar/Abbottabad)",
        "Balochistan (Quetta)",
        "GB & AJK",
        "Overseas Tourist (UK/US/Gulf)"
    )

    val durations = listOf(1, 2, 3, 5, 7, 10)

    val groupTypes = listOf(
        "Solo Visitor",
        "Patient & Family Attendants",
        "Family with Kids",
        "Lawyer / Corporate Delegation",
        "Students / Exam Takers",
        "Couple / Honeymoon"
    )

    val transportModes = listOf(
        "Metro Bus & EV Buses (PKR 50)",
        "inDrive / Yango App Cab",
        "Full-Day Chauffeur Sedan",
        "Luxury 4x4 Prado / Fortuner",
        "Hiace 14-Seater Group Van"
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
                        text = "TAILORED VISIT ARCHITECT & STRATEGY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${OfficialContacts.APP_NAME} • Custom Itinerary Architect",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Whether traveling for specialized hospital care, supreme court proceedings, academic examinations, diplomatic affairs, or northern expeditions — orchestrate your custom schedule, transit, and lodging with professional precision.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(top = 4.dp),
                    lineHeight = 16.sp
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
                    // 1. Purpose of Visit
                    Text(
                        text = "1. Purpose of Your Visit",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(VisitorPurpose.values()) { purp ->
                            FilterChip(
                                selected = (form.visitorPurpose == purp),
                                onClick = { viewModel.updateTourRequirement(form.copy(visitorPurpose = purp)) },
                                label = { Text(purp.title, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    // 2. Budget Tier
                    Text(
                        text = "2. Financial Budget & Style",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BudgetTier.values().forEach { tier ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (form.budgetTier == tier) PinePrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.updateTourRequirement(form.copy(budgetTier = tier)) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(tier.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = if (form.budgetTier == tier) PinePrimary else MaterialTheme.colorScheme.onSurface)
                                        Text(tier.subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text(tier.estimatedDailyPkr, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = PinePrimary)
                                    }
                                    if (form.budgetTier == tier) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = PinePrimary, modifier = Modifier.size(20.dp))
                                    }
                                }
                            }
                        }
                    }

                    // 3. Preferred Transport
                    Text(
                        text = "3. Preferred Transport Mode",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(transportModes) { mode ->
                            FilterChip(
                                selected = (form.preferredTransport == mode),
                                onClick = { viewModel.updateTourRequirement(form.copy(preferredTransport = mode)) },
                                label = { Text(mode, fontSize = 12.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PinePrimary,
                                    selectedLabelColor = Color.White
                                )
                            )
                        }
                    }

                    // 4. Origin
                    Text(
                        text = "4. Arriving From",
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

                    // 5. Duration
                    Text(
                        text = "5. Stay Duration",
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

                    // 6. Traveling Group
                    Text(
                        text = "6. Party / Group Type",
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

                    // 7. Checkboxes
                    Text(
                        text = "7. Inclusive Services Required",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = form.includePickAndDrop,
                                onCheckedChange = { viewModel.updateTourRequirement(form.copy(includePickAndDrop = it)) },
                                colors = CheckboxDefaults.colors(checkedColor = PinePrimary)
                            )
                            Text("Airport ISB / Daewoo Station Pick & Drop", fontSize = 13.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = form.includeHotel,
                                onCheckedChange = { viewModel.updateTourRequirement(form.copy(includeHotel = it)) },
                                colors = CheckboxDefaults.colors(checkedColor = PinePrimary)
                            )
                            Text("Stay / Guest House matching budget tier", fontSize = 13.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = form.includeDedicatedCar,
                                onCheckedChange = { viewModel.updateTourRequirement(form.copy(includeDedicatedCar = it)) },
                                colors = CheckboxDefaults.colors(checkedColor = PinePrimary)
                            )
                            Text("Dedicated City Car / Cab Assistance", fontSize = 13.sp)
                        }
                    }

                    // 8. Contact & Details
                    OutlinedTextField(
                        value = form.clientName,
                        onValueChange = { viewModel.updateTourRequirement(form.copy(clientName = it)) },
                        label = { Text("Your Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = form.phoneOrContact,
                        onValueChange = { viewModel.updateTourRequirement(form.copy(phoneOrContact = it)) },
                        label = { Text("WhatsApp or Cell Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = form.specialNotes,
                        onValueChange = { viewModel.updateTourRequirement(form.copy(specialNotes = it)) },
                        label = { Text("Specific Needs (e.g. Shifa OPD, High Court timing, wheelchair)") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    // Generate Button
                    Button(
                        onClick = { viewModel.generateCustomItinerary() },
                        enabled = !isGenerating,
                        colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp).testTag("generate_custom_plan_button")
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generating Tailored Visit Strategy...")
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Tailored Visit Plan", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Generated Plan Display
        generatedPlan?.let { plan ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("generated_plan_result_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Your Tailored Itinerary & Strategy",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = PinePrimary
                            )
                            Surface(
                                color = WarmGold.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = form.budgetTier.title,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF6B4E00),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = plan,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.saveCustomPlanAsInquiry() },
                                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).testTag("save_plan_inquiry_button")
                            ) {
                                Icon(Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Save Plan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = {
                                    val sendIntent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(android.content.Intent.EXTRA_SUBJECT, "My Islamabad Tailored Visit Plan")
                                        putExtra(android.content.Intent.EXTRA_TEXT, plan)
                                    }
                                    context.startActivity(android.content.Intent.createChooser(sendIntent, "Share Itinerary"))
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp), tint = PinePrimary)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Share", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PinePrimary)
                            }
                        }
                    }
                }
            }
        }
    }
}

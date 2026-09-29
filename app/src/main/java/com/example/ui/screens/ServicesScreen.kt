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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.BudgetTier
import com.example.model.OfficialContacts
import com.example.model.TravelService
import com.example.ui.theme.MountainMint
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold
import com.example.ui.viewmodel.IslamabadViewModel

@Composable
fun ServicesScreen(viewModel: IslamabadViewModel) {
    val selectedCategory by viewModel.selectedServiceCategory.collectAsState()
    val useUsd by viewModel.useUsd.collectAsState()
    val targetService by viewModel.bookingTargetService.collectAsState()

    val categories = listOf("All", "Transport & Transit", "Stays & Hotels", "Guided Packages", "Dining & Food")

    val filteredServices = if (selectedCategory == "All") {
        SampleData.travelServices
    } else {
        SampleData.travelServices.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("services_screen_list"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Services Header
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
                        text = "CURATED SERVICES & VERIFIED RESERVATIONS",
                        fontSize = 10.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Transport, Accommodation & Guided Itineraries",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Text(
                    text = "Accessible transit starting from PKR 50 via the Metro Bus network to on-demand inDrive/Yango, executive chauffeurs, and 4x4 mountain SUVs. Vetted patient guest houses, student hostels, and 5-star suites.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { viewModel.toggleCurrency() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = PinePrimary),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.CurrencyExchange, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (useUsd) "Show in PKR (Rs)" else "Show in USD ($)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Surface(
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "24/7 Hotline: ${OfficialContacts.OFFICIAL_PHONE}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }

        // Category Filter Row
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { cat ->
                    FilterChip(
                        selected = (cat == selectedCategory),
                        onClick = { viewModel.setServiceCategory(cat) },
                        label = { Text(cat, fontWeight = if (cat == selectedCategory) FontWeight.Bold else FontWeight.Normal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PinePrimary,
                            selectedLabelColor = Color.White
                        ),
                        modifier = Modifier.testTag("service_chip_$cat")
                    )
                }
            }
        }

        // Service Items
        items(filteredServices, key = { it.id }) { service ->
            val isBookmarked by remember(service.id) {
                derivedStateOf { viewModel.isItemBookmarked(service.id) }
            }
            ServiceCard(
                service = service,
                useUsd = useUsd,
                isBookmarked = isBookmarked,
                onToggleBookmark = { viewModel.toggleBookmarkService(service) },
                onBookNow = { viewModel.openBookingDialog(service) }
            )
        }
    }

    // Booking Inquiry Dialog
    targetService?.let { service ->
        BookingInquiryDialog(
            service = service,
            useUsd = useUsd,
            onDismiss = { viewModel.openBookingDialog(null) },
            onSubmit = { name, phone, origin, dates, details ->
                viewModel.submitServiceInquiry(service, name, phone, origin, dates, details)
            }
        )
    }
}

@Composable
fun ServiceCard(
    service: TravelService,
    useUsd: Boolean,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onBookNow: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("service_card_${service.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = when (service.budgetTier) {
                            BudgetTier.BUDGET_SAVER -> MountainMint.copy(alpha = 0.25f)
                            BudgetTier.COMFORT_STANDARD -> PinePrimary.copy(alpha = 0.15f)
                            BudgetTier.EXECUTIVE_VIP -> WarmGold.copy(alpha = 0.25f)
                        },
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = service.budgetTier.title,
                            color = when (service.budgetTier) {
                                BudgetTier.BUDGET_SAVER -> PinePrimary
                                BudgetTier.COMFORT_STANDARD -> PinePrimary
                                BudgetTier.EXECUTIVE_VIP -> Color(0xFF8B6508)
                            },
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "• ${service.category}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(32.dp).testTag("bookmark_service_${service.id}")
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) WarmGold else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = service.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = service.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = service.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Included features
            service.includedFeatures.take(3).forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PinePrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Pricing and Book button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (useUsd) "$${service.priceUsd}" else "PKR ${"%,d".format(service.pricePkr)}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = PinePrimary
                    )
                    Text(
                        text = service.priceUnit,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = onBookNow,
                    colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("book_button_${service.id}")
                ) {
                    Text("Book / Inquire", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun BookingInquiryDialog(
    service: TravelService,
    useUsd: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (name: String, phone: String, origin: String, dates: String, details: String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("Domestic (Punjab/Sindh/KPK/Balochistan)") }
    var dates by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Book: ${service.title}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${if (useUsd) "$${service.priceUsd}" else "PKR ${"%,d".format(service.pricePkr)}"} ${service.priceUnit} (${service.budgetTier.title})",
                    fontSize = 13.sp,
                    color = PinePrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("inquiry_input_name")
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Cell Number or WhatsApp") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("inquiry_input_phone")
                )

                OutlinedTextField(
                    value = origin,
                    onValueChange = { origin = it },
                    label = { Text("City of Origin or Country") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dates,
                    onValueChange = { dates = it },
                    label = { Text("Arrival / Travel Dates") },
                    placeholder = { Text("e.g. Next weekend / 15th Oct") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text("Purpose & Special Notes") },
                    placeholder = { Text("e.g. Hospital checkup, court hearing, family luggage, wheelchair") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(name, phone, origin, dates, details)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                modifier = Modifier.testTag("inquiry_submit_confirm")
            ) {
                Text("Confirm & Inquire")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

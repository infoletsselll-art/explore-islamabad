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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Hotel
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

    val categories = listOf("All", "Pick & Drop", "Hotels & Stays", "Tours & Sightseeing", "Camping & Adventure")

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
                        text = "VERIFIED TOURIST SERVICES",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "explore Islmbd • Client & Traveler Services",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Airport pick & drop, luxury hotel reservations, chauffeur fleet, camping gear, and customized tour packages for domestic and overseas visitors. 24/7 AI Automation & Hotline: 03457059286.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.9f),
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Currency switcher banner
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Prices shown in: ${if (useUsd) "USD ($)" else "PKR (Rs)"}",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        fontWeight = FontWeight.Medium
                    )

                    Button(
                        onClick = { viewModel.toggleCurrency() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.2f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.testTag("currency_toggle_button")
                    ) {
                        Text(
                            text = if (useUsd) "Switch to PKR" else "Switch to USD",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
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

        // Service Cards
        items(filteredServices, key = { it.id }) { service ->
            val isBookmarked by remember(service.id) {
                androidx.compose.runtime.derivedStateOf { viewModel.isItemBookmarked(service.id) }
            }
            ServiceCard(
                service = service,
                useUsd = useUsd,
                isBookmarked = isBookmarked,
                onToggleBookmark = { viewModel.toggleBookmarkService(service) },
                onRequestBooking = { viewModel.openBookingDialog(service) }
            )
        }
    }

    // Booking Dialog
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
    onRequestBooking: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
                        color = PinePrimary.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = service.category,
                            color = PinePrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    if (service.isPopular) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = WarmGold.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ElectricBolt,
                                    contentDescription = null,
                                    tint = WarmGold,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "POPULAR CHOICE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WarmGold
                                )
                            }
                        }
                    }
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier.size(32.dp).testTag("bookmark_service_${service.id}")
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = if (isBookmarked) "Bookmarked" else "Bookmark",
                        tint = if (isBookmarked) WarmGold else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

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

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = service.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Features list
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                service.includedFeatures.forEach { feature ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = MountainMint,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = feature,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Price and Action
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
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = onRequestBooking,
                    colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("book_service_${service.id}")
                ) {
                    Text(
                        text = "Book / Request Quote",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
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
    var clientName by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var originCity by remember { mutableStateOf("Overseas Tourist (UK/USA/Gulf)") }
    var preferredDates by remember { mutableStateOf("") }
    var specialRequests by remember { mutableStateOf("") }

    val originOptions = listOf(
        "Overseas Tourist (UK/USA/Gulf/EU)",
        "Domestic Traveler (Karachi/Sindh)",
        "Domestic Traveler (Lahore/Punjab)",
        "Domestic Traveler (KPK/Balochistan)"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Request Booking Inquiry",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    color = PinePrimary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = service.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = PinePrimary
                        )
                        Text(
                            text = "Estimated: ${if (useUsd) "$${service.priceUsd}" else "PKR ${"%,d".format(service.pricePkr)}"} (${service.priceUnit})",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Your Full Name") },
                    modifier = Modifier.fillMaxWidth().testTag("input_client_name"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = contactPhone,
                    onValueChange = { contactPhone = it },
                    label = { Text("WhatsApp / Phone Number") },
                    placeholder = { Text("+92 300 1234567 or international") },
                    modifier = Modifier.fillMaxWidth().testTag("input_contact_phone"),
                    singleLine = true
                )

                OutlinedTextField(
                    value = preferredDates,
                    onValueChange = { preferredDates = it },
                    label = { Text("Travel / Arrival Date(s)") },
                    placeholder = { Text("e.g. Next weekend or 24th Oct") },
                    modifier = Modifier.fillMaxWidth().testTag("input_travel_dates"),
                    singleLine = true
                )

                Column {
                    Text(
                        text = "Traveling From:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        items(originOptions) { opt ->
                            FilterChip(
                                selected = (opt == originCity),
                                onClick = { originCity = opt },
                                label = { Text(opt.take(24) + "...", fontSize = 11.sp) }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = specialRequests,
                    onValueChange = { specialRequests = it },
                    label = { Text("Custom Needs / Passenger count / Luggage") },
                    modifier = Modifier.fillMaxWidth().testTag("input_special_requests"),
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit(clientName, contactPhone, originCity, preferredDates, specialRequests)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("submit_inquiry_button")
            ) {
                Text("Confirm & Submit Inquiry")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss, shape = RoundedCornerShape(12.dp)) {
                Text("Cancel")
            }
        },
        shape = RoundedCornerShape(20.dp)
    )
}

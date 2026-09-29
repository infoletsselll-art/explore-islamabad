package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SampleData
import com.example.model.Destination
import com.example.model.OfficialContacts
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold
import com.example.ui.viewmodel.IslamabadViewModel

import com.example.model.SocialForum
import com.example.ui.components.FaqAccordionSection
import com.example.ui.components.PurposeSelectionColumn
import androidx.compose.material.icons.filled.Public
import androidx.compose.ui.platform.LocalContext

@Composable
fun ExploreScreen(viewModel: IslamabadViewModel) {
    val context = LocalContext.current
    val selectedPurpose by viewModel.selectedPurpose.collectAsState()
    val selectedCategory by viewModel.selectedDestinationCategory.collectAsState()
    val detailDestination by viewModel.selectedDestinationDetail.collectAsState()

    val categories = listOf(
        "All",
        "Health & Hospitals",
        "Courts & Official",
        "Universities & Study",
        "Shopping & Malls",
        "Tourism & Heritage",
        "Northern Gateway"
    )

    val filteredList = if (selectedCategory == "All") {
        SampleData.destinations
    } else {
        SampleData.destinations.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("explore_screen_list"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Hero Image & Title Banner
        item {
            HeroHeader(
                onConsultAgents = { viewModel.selectTab(1) },
                onPlanVisit = { viewModel.selectTab(3) }
            )
        }

        // Purpose Selection & Essentials Column (Interactive Preview)
        item {
            PurposeSelectionColumn(
                selectedPurpose = selectedPurpose,
                onSelectPurpose = { purpose -> viewModel.selectPurpose(purpose) },
                onConsultAgent = { purpose ->
                    viewModel.selectPurpose(purpose)
                    viewModel.selectTab(1) // Switch to AI Concierge
                },
                onPlanVisit = { purpose ->
                    viewModel.selectPurpose(purpose)
                    viewModel.selectTab(3) // Switch to Custom Planner
                }
            )
        }

        // Category Filter Chips & Curated Section Header
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Surface(
                    color = PinePrimary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = if (selectedPurpose != null) "CURATED FOR YOUR VISIT" else "CAPITAL LANDMARKS DIRECTORY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PinePrimary,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = if (selectedPurpose != null) "Institutions for ${selectedPurpose?.badge ?: selectedCategory}" else "Explore Curated Capital Destinations",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (selectedPurpose != null)
                        "Verified locations, transit options, and direct facilities aligned with your selected purpose."
                    else
                        "Healthcare facilities, supreme & high court complexes, universities, shopping avenues, and northern routes.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp, bottom = 8.dp),
                    lineHeight = 18.sp
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { cat ->
                        FilterChip(
                            selected = (cat == selectedCategory),
                            onClick = { viewModel.setDestinationCategory(cat) },
                            label = { Text(cat, fontWeight = if (cat == selectedCategory) FontWeight.Bold else FontWeight.Normal) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PinePrimary,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.testTag("chip_$cat")
                        )
                    }
                }
            }
        }

        // Destination Cards
        items(filteredList, key = { it.id }) { destination ->
            val isBookmarked by remember(destination.id) {
                derivedStateOf { viewModel.isItemBookmarked(destination.id) }
            }
            DestinationCard(
                destination = destination,
                isBookmarked = isBookmarked,
                onToggleBookmark = { viewModel.toggleBookmarkDestination(destination) },
                onCardClick = { viewModel.openDestinationDetail(destination) },
                onAskAgent = { viewModel.askAgentAboutDestination(destination) }
            )
        }

        // Verified Partners Section (Transport, Hospitality & Hostels)
        item {
            Spacer(modifier = Modifier.height(16.dp))
            VerifiedPartnersSection()
        }

        // Testimonials / Verified Experiences Section
        item {
            Spacer(modifier = Modifier.height(16.dp))
            VerifiedVisitorTestimonialsSection()
        }

        // Channels & Forums Section
        item {
            Spacer(modifier = Modifier.height(20.dp))
            ChannelsAndForumsHomeSection(
                onOpenForum = { forum -> viewModel.openOfficialForum(context, forum) }
            )
        }

        // Frequently Asked Questions Section (Accordion UI)
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                FaqAccordionSection(
                    onNavigateToAI = { viewModel.selectTab(1) }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Detail Dialog
    detailDestination?.let { dest ->
        val isBookmarked by remember(dest.id) {
            derivedStateOf { viewModel.isItemBookmarked(dest.id) }
        }
        DestinationDetailDialog(
            destination = dest,
            isBookmarked = isBookmarked,
            onToggleBookmark = { viewModel.toggleBookmarkDestination(dest) },
            onDismiss = { viewModel.openDestinationDetail(null) },
            onAskAgent = {
                viewModel.openDestinationDetail(null)
                viewModel.askAgentAboutDestination(dest)
            }
        )
    }
}

@Composable
fun HeroHeader(
    onConsultAgents: () -> Unit,
    onPlanVisit: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_islamabad_hero),
            contentDescription = "Visit Islamabad Hero",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.88f)
                        )
                    )
                )
        )

        // Content
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Surface(
                color = WarmGold,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "A GATEWAY TO VISITORS OF ALL TYPES & FOR ALL PURPOSES",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            Text(
                text = OfficialContacts.APP_NAME,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                text = OfficialContacts.TAGLINE,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = WarmGold,
                modifier = Modifier.padding(vertical = 2.dp)
            )

            Text(
                text = "Healthcare • Supreme & High Court • Universities • Shopping • Northern Gateway\nCurated transit, vetted accommodation, and round-trip support for every budget tier.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f),
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onConsultAgents,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinePrimary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("hero_consult_agent_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("24/7 AI Concierge", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onPlanVisit,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Plan Custom Visit", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun DestinationCard(
    destination: Destination,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onCardClick: () -> Unit,
    onAskAgent: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onCardClick)
            .testTag("destination_card_${destination.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = PinePrimary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = destination.category,
                        color = PinePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onToggleBookmark,
                        modifier = Modifier.size(32.dp).testTag("bookmark_${destination.id}")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (isBookmarked) "Bookmarked" else "Bookmark",
                            tint = if (isBookmarked) WarmGold else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = WarmGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${destination.rating}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = destination.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = destination.subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Location & Metro Badges
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = PinePrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = destination.locationSector,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text("•", color = Color.Gray, fontSize = 12.sp)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DirectionsBus,
                        contentDescription = null,
                        tint = PinePrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = destination.metroBusNear.take(28),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Budget level badge
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Payments, contentDescription = null, tint = WarmGold, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = destination.budgetLevel,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View Location Profile",
                    fontSize = 12.sp,
                    color = PinePrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    onClick = onAskAgent,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinePrimary.copy(alpha = 0.15f),
                        contentColor = PinePrimary
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("ask_agent_${destination.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Consult AI Concierge",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun DestinationDetailDialog(
    destination: Destination,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onDismiss: () -> Unit,
    onAskAgent: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = destination.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onToggleBookmark) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) WarmGold else Color.Gray
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    color = PinePrimary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${destination.category} • ${destination.locationSector}",
                        color = PinePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = destination.description,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp
                )

                // Transit info box
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.DirectionsBus, contentDescription = null, tint = PinePrimary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Transit: ${destination.metroBusNear}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Payments, contentDescription = null, tint = WarmGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Budget Tier: ${destination.budgetLevel}", fontSize = 12.sp)
                        }
                    }
                }

                Text(
                    text = "Key Highlights & Facilities:",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )

                destination.highlights.forEach { highlight ->
                    Row(
                        modifier = Modifier.padding(vertical = 2.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = PinePrimary,
                            modifier = Modifier.size(16.dp).padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = highlight,
                            style = MaterialTheme.typography.bodySmall,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onAskAgent,
                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary)
            ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Ask AI Concierge")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun VerifiedVisitorTestimonialsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .testTag("testimonials_section")
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Surface(
                color = WarmGold.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFB78103),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "VERIFIED VISITOR STORIES",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF6A4A00)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Trusted by Visitors Across Pakistan",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Real experiences from patients, advocates, university applicants, and families visiting Islamabad.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(SampleData.testimonials, key = { it.id }) { item ->
                TestimonialCardItem(testimonial = item)
            }
        }
    }
}

@Composable
fun TestimonialCardItem(testimonial: com.example.model.VisitorTestimonial) {
    Card(
        modifier = Modifier
            .width(310.dp)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            when (testimonial.category) {
                                "Medical Checkup" -> Color(0xFF0288D1)
                                "Courts & Legal" -> Color(0xFF455A64)
                                "University Entrance" -> Color(0xFF7B1FA2)
                                else -> PinePrimary
                            },
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = testimonial.avatarInitials,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = testimonial.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = testimonial.origin,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    color = PinePrimary.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "Verified",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = PinePrimary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                color = when (testimonial.category) {
                    "Medical Checkup" -> Color(0xFFE1F5FE)
                    "Courts & Legal" -> Color(0xFFECEFF1)
                    "University Entrance" -> Color(0xFFF3E5F5)
                    else -> Color(0xFFE8F5E9)
                },
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = testimonial.category,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (testimonial.category) {
                        "Medical Checkup" -> Color(0xFF0277BD)
                        "Courts & Legal" -> Color(0xFF37474F)
                        "University Entrance" -> Color(0xFF6A1B9A)
                        else -> Color(0xFF2E7D32)
                    },
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // 5 Golden Stars
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(testimonial.rating) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = WarmGold,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "\"${testimonial.review}\"",
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.88f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Summary Pills
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                testimonial.tripSummaryPills.take(3).forEach { pill ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = pill,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

private data class VerifiedPartner(
    val id: String,
    val name: String,
    val category: String,
    val badgeLabel: String,
    val statusText: String,
    val brandColor: Color,
    val textColor: Color = Color.White
)

@Composable
fun VerifiedPartnersSection() {
    val partners = remember {
        listOf(
            VerifiedPartner("indrive", "inDrive Pakistan", "Ride-Hailing & Outstation", "inD", "✓ Peer-to-Peer Fares", Color(0xFF7BDE2A), Color(0xFF0B2404)),
            VerifiedPartner("yango", "Yango Pakistan", "Economy & Comfort Cabs", "Ya!", "✓ Fast App Pickup", Color(0xFFFF0000), Color.White),
            VerifiedPartner("metro", "Islamabad Metro Bus", "Red, Orange, Blue & Green", "MET", "✓ PKR 50 Flat Fare", Color(0xFF00875A), Color.White),
            VerifiedPartner("cda_ev", "CDA Electric Fleet", "Feeder Transit Across Sectors", "CDA", "✓ Eco-Friendly Bus", Color(0xFF00B8D9), Color.White),
            VerifiedPartner("fleet_4x4", "Northern 4x4 Fleet", "Prado, Fortuner & Hiace", "4x4", "✓ Mountain Drivers", Color(0xFF34495E), Color(0xFFF39C12)),
            VerifiedPartner("serena", "Serena Hotels", "5-Star Luxury Diplomatic", "SH", "✓ Executive Concierge", Color(0xFF1A1A1A), Color(0xFFD4AF37)),
            VerifiedPartner("marriott", "Marriott Islamabad", "5-Star Hospitality Red Zone", "M", "✓ Corporate Rates", Color(0xFF8B0000), Color.White),
            VerifiedPartner("centaurus", "Centaurus Suites", "Executive Residence F-8", "CT", "✓ Direct Mall Access", Color(0xFF0F2027), Color(0xFFF8B195)),
            VerifiedPartner("guesthouses", "Guest House Alliance", "Vetted Family & Patient Stays", "GH", "✓ Quality Inspected", Color(0xFF2D6A4F), Color.White),
            VerifiedPartner("hostels", "Capital Hostels", "Near NUST, FAST & QAU", "STU", "✓ Safe Student Stays", Color(0xFF5E35B1), Color.White)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .testTag("verified_partners_section")
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Surface(
                color = PinePrimary.copy(alpha = 0.12f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PinePrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "OFFICIAL MOBILITY & ACCOMMODATION PARTNERS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = PinePrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Verified Transit & Hospitality Networks",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Integrated coordination with inDrive, Yango, the Islamabad Metro Bus network, CDA Electric feeder fleet, 5-star luxury hotels, and inspected family guest houses.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(partners, key = { it.id }) { partner ->
                Card(
                    modifier = Modifier
                        .width(220.dp)
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .background(partner.brandColor, RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = partner.badgeLabel,
                                    fontWeight = FontWeight.Black,
                                    color = partner.textColor,
                                    fontSize = 14.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = partner.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                                Text(
                                    text = partner.category,
                                    fontSize = 10.5.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Surface(
                            color = Color(0xFFE8F5E9),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = partner.statusText,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChannelsAndForumsHomeSection(
    onOpenForum: (SocialForum) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .testTag("channels_and_forums_section")
    ) {
        Surface(
            color = PinePrimary.copy(alpha = 0.12f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Public,
                    contentDescription = null,
                    tint = PinePrimary,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "DIRECT REACH & FORUMS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PinePrimary,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Channels & Forums",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = "Connect directly with official social platforms, immediate assistance channels, and community inquiry desks across Pakistan and abroad.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 2.dp, bottom = 12.dp),
            lineHeight = 18.sp
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(SampleData.officialForums) { forum ->
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .clickable { onOpenForum(forum) }
                        .testTag("forum_chip_${forum.id}")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = forum.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                modifier = Modifier.weight(1f)
                            )
                            if (forum.isPrimarySupport) {
                                Surface(
                                    color = WarmGold.copy(alpha = 0.25f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "OFFICIAL",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PinePrimary,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = forum.handleOrTarget,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PinePrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = forum.description,
                            fontSize = 10.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}



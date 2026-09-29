package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OndemandVideo
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material.icons.filled.Videocam
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.data.local.BookmarkEntity
import com.example.model.OfficialContacts
import com.example.model.SocialForum
import com.example.ui.theme.MountainMint
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold
import com.example.ui.components.FaqAccordionSection
import com.example.ui.viewmodel.IslamabadViewModel

@Composable
fun BookmarksListContent(
    bookmarks: List<BookmarkEntity>,
    onRemoveBookmark: (String) -> Unit,
    onNavigateToExplore: () -> Unit
) {
    if (bookmarks.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(WarmGold.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        tint = WarmGold,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No Bookmarks Yet",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Bookmark favorite tourist spots, luxury hotels, airport fleets, and camping tours using the bookmark icon on any card.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )
                Button(
                    onClick = onNavigateToExplore,
                    colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Explore Spots & Stays")
                }
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Saved Bookmarks (${bookmarks.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Stored locally via Room for instant offline access and quick itinerary planning.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(bookmarks, key = { it.id }) { bookmark ->
                BookmarkItemCard(
                    bookmark = bookmark,
                    onRemove = { onRemoveBookmark(bookmark.id) }
                )
            }
        }
    }
}

@Composable
fun BookmarkItemCard(
    bookmark: BookmarkEntity,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().testTag("bookmark_card_${bookmark.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = when (bookmark.itemType) {
                        "DESTINATION" -> PinePrimary.copy(alpha = 0.12f)
                        "HOTEL" -> WarmGold.copy(alpha = 0.18f)
                        "TRANSPORT" -> MountainMint.copy(alpha = 0.18f)
                        else -> Color.Gray.copy(alpha = 0.15f)
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "${bookmark.itemType} • ${bookmark.category}",
                        color = when (bookmark.itemType) {
                            "DESTINATION" -> PinePrimary
                            "HOTEL" -> WarmGold
                            "TRANSPORT" -> MountainMint
                            else -> MaterialTheme.colorScheme.onSurface
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                IconButton(onClick = onRemove, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = Icons.Default.BookmarkRemove,
                        contentDescription = "Remove Bookmark",
                        tint = Color.Gray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = bookmark.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = bookmark.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (bookmark.detailSnippet.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = bookmark.detailSnippet,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 3
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bookmark.priceOrDistance,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = PinePrimary
                )

                Text(
                    text = bookmark.ratingOrVehicle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun OfficialContactsAndForumsContent(
    context: Context,
    viewModel: IslamabadViewModel
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.testTag("official_contacts_content")
    ) {
        // Hero Card for Direct Support & AI Automation
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PinePrimary),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = WarmGold,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "24/7 AI AUTOMATION & AGENTS",
                                color = Color.Black,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = OfficialContacts.APP_NAME,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        text = OfficialContacts.TAGLINE,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Direct Action Buttons (Call, WhatsApp, Email)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val callForum = SampleData.officialForums.first { it.id == "call_support" }
                                viewModel.openOfficialForum(context, callForum)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = PinePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Call Now", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                val waForum = SampleData.officialForums.first { it.id == "whatsapp" }
                                viewModel.openOfficialForum(context, waForum)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MountainMint, contentColor = Color.White),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("WhatsApp", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = {
                                val emailForum = SampleData.officialForums.first { it.id == "official_email" }
                                viewModel.openOfficialForum(context, emailForum)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = WarmGold, contentColor = Color.Black),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Email Us", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // Dynamic QR Code & Direct APK Share Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(PinePrimary.copy(alpha = 0.12f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QrCode,
                                    contentDescription = "QR Code",
                                    tint = PinePrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Scan & Download APK",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "Share app directly to any phone",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Surface(
                            color = MountainMint.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "v1.0 • 25 MB",
                                color = PinePrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Share with traveling companions or scan from your laptop screen to install on Android without Google Play Store login.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Direct APK Link & Copy
                    val apkDownloadUrl = "${OfficialContacts.WEBPAGE_URL}/visit-islamabad.apk"
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "visit-islamabad.apk",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = PinePrimary
                            )
                            Text(
                                text = "Android 8.0 - 15+",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = {
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_SUBJECT, "Download ${OfficialContacts.APP_NAME} Android App")
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "${OfficialContacts.APP_NAME} — A gateway to the visitors of all types and for all purposes.\n\nHealth, legal hearings, university exams, shopping, tourism & northern transit.\n\nDownload the Android APK:\n$apkDownloadUrl\n\nOr launch web version:\n${OfficialContacts.WEBPAGE_URL}"
                                    )
                                }
                                context.startActivity(Intent.createChooser(shareIntent, "Share ${OfficialContacts.APP_NAME} App"))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Share Link", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                val clip = android.content.ClipData.newPlainText("APK Link", apkDownloadUrl)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(context, "APK Download Link Copied!", Toast.LENGTH_SHORT).show()
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 10.dp)
                        ) {
                            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp), tint = PinePrimary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Copy URL", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = PinePrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("🍏 iPhone & iPad (iOS Web App)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "On iOS Safari, open ${OfficialContacts.WEBPAGE_URL}, tap the Share button [↑], and select 'Add to Home Screen' for an instant, full-screen app without needing the App Store.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // Official Identity & Domain Card
        item {
            Text(
                text = "Official Identity & Web Services",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ContactDetailRow(
                        icon = Icons.Default.Public,
                        label = "App, Webpage & Domain Name",
                        value = "${OfficialContacts.APP_NAME} (${OfficialContacts.DOMAIN_NAME})"
                    )
                    ContactDetailRow(
                        icon = Icons.Default.Language,
                        label = "Official Webpage URL",
                        value = OfficialContacts.WEBPAGE_URL
                    )
                    ContactDetailRow(
                        icon = Icons.Default.Email,
                        label = "Official Email (All Channels)",
                        value = OfficialContacts.OFFICIAL_EMAIL
                    )
                    ContactDetailRow(
                        icon = Icons.Default.Phone,
                        label = "Official Cell / WhatsApp / Telegram",
                        value = "${OfficialContacts.OFFICIAL_PHONE} (${OfficialContacts.OFFICIAL_PHONE_INTL})"
                    )
                }
            }
        }

        // All Searchable Forums & Social Platforms
        item {
            Text(
                text = "Channels & Forums",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Official social platforms, immediate assistance channels, and community inquiry desks across Pakistan and abroad.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        items(SampleData.officialForums, key = { it.id }) { forum ->
            ForumCardItem(
                forum = forum,
                onClick = { viewModel.openOfficialForum(context, forum) }
            )
        }

        // Frequently Asked Questions Section (Accordion UI)
        item {
            Spacer(modifier = Modifier.height(10.dp))
            FaqAccordionSection(
                onNavigateToAI = { viewModel.selectTab(1) }
            )
        }
    }
}

@Composable
fun ContactDetailRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(PinePrimary.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = PinePrimary, modifier = Modifier.size(18.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
fun ForumCardItem(
    forum: SocialForum,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("forum_card_${forum.id}")
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        when (forum.id) {
                            "whatsapp" -> MountainMint.copy(alpha = 0.15f)
                            "call_support", "sms_support" -> PinePrimary.copy(alpha = 0.15f)
                            "official_email" -> WarmGold.copy(alpha = 0.2f)
                            "telegram" -> Color(0xFF0088CC).copy(alpha = 0.15f)
                            "facebook" -> Color(0xFF1877F2).copy(alpha = 0.15f)
                            "instagram" -> Color(0xFFE4405F).copy(alpha = 0.15f)
                            "tiktok" -> Color(0xFF000000).copy(alpha = 0.12f)
                            "discord" -> Color(0xFF5865F2).copy(alpha = 0.15f)
                            "pinterest" -> Color(0xFFBD081C).copy(alpha = 0.15f)
                            "twitter_x" -> Color(0xFF14171A).copy(alpha = 0.15f)
                            "google_search_business" -> Color(0xFF4285F4).copy(alpha = 0.15f)
                            "google_meet" -> Color(0xFF00897B).copy(alpha = 0.15f)
                            else -> PinePrimary.copy(alpha = 0.15f)
                        },
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                val iconVector: ImageVector = when (forum.id) {
                    "whatsapp" -> Icons.AutoMirrored.Filled.Chat
                    "call_support" -> Icons.Default.Call
                    "sms_support" -> Icons.Default.Sms
                    "official_email" -> Icons.Default.Email
                    "telegram" -> Icons.Default.ChatBubble
                    "facebook" -> Icons.Default.Public
                    "instagram" -> Icons.Default.Public
                    "tiktok" -> Icons.Default.OndemandVideo
                    "discord" -> Icons.Default.ChatBubble
                    "pinterest" -> Icons.Default.Bookmark
                    "twitter_x" -> Icons.Default.Public
                    "google_search_business" -> Icons.Default.Search
                    "google_meet" -> Icons.Default.Videocam
                    else -> Icons.Default.Business
                }
                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = PinePrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = forum.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (forum.isPrimarySupport) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = WarmGold.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "OFFICIAL",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = PinePrimary,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                Text(
                    text = forum.handleOrTarget,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PinePrimary
                )
                Text(
                    text = forum.description,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                contentDescription = "Open",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

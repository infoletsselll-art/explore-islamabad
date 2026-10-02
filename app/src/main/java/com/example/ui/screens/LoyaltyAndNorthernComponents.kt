package com.example.ui.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Nfc
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleData
import com.example.model.NorthernDeal
import com.example.ui.theme.AccentCyan
import com.example.ui.theme.MountainMint
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold
import com.example.ui.viewmodel.IslamabadViewModel

@Composable
fun LoyaltyAndCardsContent(viewModel: IslamabadViewModel) {
    val loyaltyPoints by viewModel.loyaltyPoints.collectAsState()
    val prepaidBalance by viewModel.prepaidBalance.collectAsState()
    val postpaidCreditUsed by viewModel.postpaidCreditUsed.collectAsState()
    val postpaidCreditLimit by viewModel.postpaidCreditLimit.collectAsState()
    val referralsCount by viewModel.referralsCount.collectAsState()
    val referralEarnings by viewModel.referralEarnings.collectAsState()
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var selectedSection by remember { mutableStateOf("cards") } // "cards", "points", "refer"

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Selector Pills
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedSection == "cards",
                    onClick = { selectedSection = "cards" },
                    label = { Text("Prepaid & Postpaid Cards", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PinePrimary,
                        selectedLabelColor = Color.White
                    )
                )
                FilterChip(
                    selected = selectedSection == "points",
                    onClick = { selectedSection = "points" },
                    label = { Text("Points & Perks", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PinePrimary,
                        selectedLabelColor = Color.White
                    )
                )
                FilterChip(
                    selected = selectedSection == "refer",
                    onClick = { selectedSection = "refer" },
                    label = { Text("Refer & Earn", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                    leadingIcon = { Icon(Icons.Default.CardGiftcard, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = PinePrimary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        when (selectedSection) {
            "cards" -> {
                // 1. PREPAID LOYALTY CARD
                item {
                    Text(
                        text = "1. Prepaid Capital Green Card",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFF064E3B), Color(0xFF10B981), Color(0xFF059669))
                                    )
                                )
                                .padding(18.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "🇵🇰 Visit Islamabad",
                                        color = Color.White,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 16.sp
                                    )
                                    Surface(
                                        color = Color.Black.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = "PREPAID CASH PASS",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp, 26.dp)
                                            .background(Color(0xFFF59E0B), RoundedCornerShape(4.dp))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.Default.Nfc, contentDescription = null, tint = Color.White.copy(alpha = 0.8f))
                                }

                                Text(
                                    text = "4829 •••• •••• 1048",
                                    color = Color.White,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    letterSpacing = 2.sp
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Column {
                                        Text("CARDHOLDER", fontSize = 9.sp, color = Color.White.copy(alpha = 0.7f))
                                        Text("CAPITAL VISITOR PASS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("RELOADED BALANCE", fontSize = 9.sp, color = Color.White.copy(alpha = 0.7f))
                                        Text("PKR ${"%,d".format(prepaidBalance)}", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.reloadPrepaidCard(5000) },
                            colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+5K Reload", fontSize = 12.sp)
                        }
                        Button(
                            onClick = { viewModel.reloadPrepaidCard(15000) },
                            colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("+15K Reload", fontSize = 12.sp)
                        }
                    }
                }

                // 2. POSTPAID LOYALTY CARD
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "2. Postpaid Margalla Platinum Card",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.linearGradient(
                                        listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF090E11))
                                    )
                                )
                                .border(1.dp, WarmGold.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                                .padding(18.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "👑 Margalla Platinum",
                                        color = WarmGold,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 16.sp
                                    )
                                    Surface(
                                        color = WarmGold.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text(
                                            text = "POSTPAID CREDIT LINE",
                                            color = WarmGold,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp, 26.dp)
                                            .background(Color(0xFFE2E8F0), RoundedCornerShape(4.dp))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Icon(Icons.Default.Nfc, contentDescription = null, tint = WarmGold.copy(alpha = 0.8f))
                                }

                                Text(
                                    text = "9104 •••• •••• 7731",
                                    color = Color(0xFFFDE68A),
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    letterSpacing = 2.sp
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Column {
                                        Text("CORPORATE MEMBER", fontSize = 9.sp, color = Color.White.copy(alpha = 0.7f))
                                        Text("EXECUTIVE TRAVELER", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("AVAILABLE LIMIT", fontSize = 9.sp, color = WarmGold.copy(alpha = 0.8f))
                                        Text(
                                            "PKR ${"%,d".format(postpaidCreditLimit - postpaidCreditUsed)} / 100K",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = WarmGold
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "• 30-day 0% interest billing for apex court litigators and emergency hospital transfers.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            "points" -> {
                // Loyalty Points Score & Tier Progress
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Loyalty Reward Score", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        text = "${"%,d".format(loyaltyPoints)} pts",
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = PinePrimary
                                    )
                                }
                                Surface(
                                    color = WarmGold.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(
                                        text = "⭐ Gold Voyager",
                                        color = WarmGold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))
                            LinearProgressIndicator(
                                progress = { (loyaltyPoints % 2000) / 2000f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = PinePrimary,
                                trackColor = Color.Black.copy(alpha = 0.2f),
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Silver (0 pts)", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text("Target: Platinum (2,000 pts)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = PinePrimary)
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { viewModel.claimDailyCheckIn() },
                                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Claim Daily 25 Pts Check-In")
                            }
                        }
                    }
                }

                // Rewards Redemption Store
                item {
                    Text(
                        text = "Redeem Points for Travel Vouchers",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(SampleData.rewardVouchers, key = { it.id }) { voucher ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(voucher.iconEmoji, fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(voucher.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Text(voucher.desc, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text(
                                        "${voucher.pointsCost} Points",
                                        fontWeight = FontWeight.ExtraBold,
                                        color = WarmGold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                            Button(
                                onClick = { viewModel.redeemRewardVoucher(voucher) },
                                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text("Redeem", fontSize = 11.sp)
                            }
                        }
                    }
                }
            }

            "refer" -> {
                // Refer a Friend Section
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "Refer a Friend & Both Earn Rewards!",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "• Your friend gets PKR 1,000 instant discount on their first booking.\n• You earn PKR 1,500 cash reward + 200 Loyalty Points when their trip is confirmed!",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(14.dp))
                            Surface(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, PinePrimary),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("YOUR REFERRAL CODE", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text(viewModel.referralCode, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = PinePrimary)
                                    }
                                    IconButton(onClick = {
                                        clipboardManager.setText(AnnotatedString(viewModel.referralCode))
                                        viewModel.claimDailyCheckIn() // friendly feedback trigger
                                    }) {
                                        Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = PinePrimary)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))
                            Button(
                                onClick = { viewModel.shareReferralIntent(context) },
                                colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Share Referral via WhatsApp / SMS")
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Friends Invited: $referralsCount", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text("Cash Earned: PKR ${"%,d".format(referralEarnings)}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MountainMint)
                            }
                            if (referralEarnings > 0) {
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedButton(
                                    onClick = { viewModel.transferReferralToPrepaid() },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Text("Transfer PKR ${"%,d".format(referralEarnings)} to Prepaid Card", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NorthernDealsContent(viewModel: IslamabadViewModel) {
    val season by viewModel.northernSeason.collectAsState()
    val isOffSeason by viewModel.isOffSeasonDeals.collectAsState()
    val context = LocalContext.current

    val filteredDeals = SampleData.northernDeals.filter {
        (season == "summer" && it.summerSeason) || (season == "winter" && it.winterSeason)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Season Switcher & Off-Season Deals Switch
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Northern Areas Season & Tariffs",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = season == "summer",
                            onClick = { viewModel.setNorthernSeason("summer") },
                            label = { Text("☀️ Summer (May-Oct)", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PinePrimary,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = season == "winter",
                            onClick = { viewModel.setNorthernSeason("winter") },
                            label = { Text("❄️ Winter (Nov-Apr)", fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PinePrimary,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("⚡ Off-Season Deals (Up to 50% OFF)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = WarmGold)
                            Text("Lower crowds & discounted luxury resort tariffs", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = isOffSeason,
                            onCheckedChange = { viewModel.toggleOffSeasonDeals() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = PinePrimary
                            )
                        )
                    }
                }
            }
        }

        if (isOffSeason) {
            item {
                Surface(
                    color = WarmGold.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmGold.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔥", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Off-Season Promo Active: Enjoy massive savings on Hunza, Skardu, Naran, and Malam Jabba luxury hotels & 4x4 Prado fleets!",
                            style = MaterialTheme.typography.bodySmall,
                            color = WarmGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        items(filteredDeals, key = { it.id }) { deal ->
            val regularPrice = if (season == "summer") deal.summerOnPrice else deal.winterOnPrice
            val activePrice = if (isOffSeason) {
                if (season == "summer") deal.summerOffPrice else deal.winterOffPrice
            } else regularPrice
            val discountPercent = (((regularPrice - activePrice).toDouble() / regularPrice) * 100).toInt()

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = AccentCyan.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = deal.destination,
                                color = AccentCyan,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        if (isOffSeason && discountPercent > 0) {
                            Surface(
                                color = Color(0xFFEF4444),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "$discountPercent% OFF",
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.sp,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = deal.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = deal.desc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        deal.amenities.take(3).forEach { amenity ->
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "✓ $amenity",
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            if (isOffSeason && discountPercent > 0) {
                                Text(
                                    text = "PKR ${"%,d".format(regularPrice)}",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                            Text(
                                text = "PKR ${"%,d".format(activePrice)}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = PinePrimary
                            )
                        }

                        Button(
                            onClick = { viewModel.bookNorthernDeal(deal, context) },
                            colors = ButtonDefaults.buttonColors(containerColor = PinePrimary),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Book Deal", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.LocalTaxi
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AgentType
import com.example.ui.screens.AgentsChatScreen
import com.example.ui.screens.CustomPlannerScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.InquiriesAndGuideScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.theme.MountainMint
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold
import com.example.ui.viewmodel.IslamabadViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                IslamabadAIApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IslamabadAIApp(viewModel: IslamabadViewModel = viewModel()) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val useUsd by viewModel.useUsd.collectAsState()
    val activeAgent by viewModel.activeAgent.collectAsState()
    val savedInquiries by viewModel.savedInquiries.collectAsState()
    val savedBookmarks by viewModel.savedBookmarks.collectAsState()
    val toastMessage by viewModel.userMessageToast.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(toastMessage) {
        toastMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearToast()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(WarmGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = com.example.model.OfficialContacts.APP_NAME,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "24/7 Concierge Hotline: ${com.example.model.OfficialContacts.OFFICIAL_PHONE}",
                                fontSize = 10.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                },
                actions = {
                    // Currency toggle
                    Surface(
                        color = Color.White.copy(alpha = 0.18f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = if (useUsd) "USD $" else "PKR Rs",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            IconButton(
                                onClick = { viewModel.toggleCurrency() },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Text("⇄", color = WarmGold, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = PinePrimary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .navigationBarsPadding()
                    .testTag("bottom_nav_bar")
            ) {
                // Tab 0: Explore
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { viewModel.selectTab(0) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 0) Icons.Filled.Explore else Icons.Outlined.Explore,
                            contentDescription = "Explore"
                        )
                    },
                    label = { Text("Explore", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PinePrimary,
                        selectedTextColor = PinePrimary,
                        indicatorColor = PinePrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_explore")
                )

                // Tab 1: AI Agents
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { viewModel.selectTab(1) },
                    icon = {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = MountainMint) {
                                    Text("${AgentType.values().size}", fontSize = 9.sp, color = Color.White)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (selectedTab == 1) Icons.Filled.SmartToy else Icons.Outlined.SmartToy,
                                contentDescription = "AI Agents"
                            )
                        }
                    },
                    label = { Text("AI Agents", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PinePrimary,
                        selectedTextColor = PinePrimary,
                        indicatorColor = PinePrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_agents")
                )

                // Tab 2: Services
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { viewModel.selectTab(2) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 2) Icons.Filled.LocalTaxi else Icons.Outlined.LocalTaxi,
                            contentDescription = "Services"
                        )
                    },
                    label = { Text("Services", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PinePrimary,
                        selectedTextColor = PinePrimary,
                        indicatorColor = PinePrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_services")
                )

                // Tab 3: Custom Planner
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { viewModel.selectTab(3) },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == 3) Icons.Filled.Tune else Icons.Outlined.Tune,
                            contentDescription = "Planner"
                        )
                    },
                    label = { Text("Planner", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PinePrimary,
                        selectedTextColor = PinePrimary,
                        indicatorColor = PinePrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_planner")
                )

                // Tab 4: Inquiries, Bookmarks & Support Hub
                val hubBadgeCount = savedInquiries.size + savedBookmarks.size
                NavigationBarItem(
                    selected = selectedTab == 4,
                    onClick = { viewModel.selectTab(4) },
                    icon = {
                        if (hubBadgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(containerColor = WarmGold) {
                                        Text("$hubBadgeCount", fontSize = 9.sp, color = Color.Black)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (selectedTab == 4) Icons.AutoMirrored.Filled.ReceiptLong else Icons.AutoMirrored.Outlined.ReceiptLong,
                                    contentDescription = "Hub"
                                )
                            }
                        } else {
                            Icon(
                                imageVector = if (selectedTab == 4) Icons.AutoMirrored.Filled.ReceiptLong else Icons.AutoMirrored.Outlined.ReceiptLong,
                                contentDescription = "Hub"
                            )
                        }
                    },
                    label = { Text("Hub", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PinePrimary,
                        selectedTextColor = PinePrimary,
                        indicatorColor = PinePrimary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_inquiries")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> ExploreScreen(viewModel = viewModel)
                1 -> AgentsChatScreen(viewModel = viewModel)
                2 -> ServicesScreen(viewModel = viewModel)
                3 -> CustomPlannerScreen(viewModel = viewModel)
                4 -> InquiriesAndGuideScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

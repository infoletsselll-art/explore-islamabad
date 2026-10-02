package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.AiTaskComplexity
import com.example.model.AgentType
import com.example.model.ChatMessage
import com.example.ui.theme.MountainMint
import com.example.ui.theme.PinePrimary
import com.example.ui.theme.WarmGold
import com.example.ui.viewmodel.IslamabadViewModel

@Composable
fun AgentsChatScreen(viewModel: IslamabadViewModel) {
    val activeAgent by viewModel.activeAgent.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val isTyping by viewModel.isAgentTyping.collectAsState()
    val selectedComplexity by viewModel.selectedComplexity.collectAsState()
    val isLiveVoiceActive by viewModel.isLiveVoiceActive.collectAsState()
    val isSpeakingAudio by viewModel.isSpeakingAudio.collectAsState()

    var inputMessage by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Auto-scroll to bottom when messages update
    LaunchedEffect(messages.size, isTyping) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .testTag("agents_chat_screen")
    ) {
        // Horizontal Agent Role Selector
        AgentSelectorCarousel(
            selectedAgent = activeAgent,
            onSelectAgent = { agent ->
                if (agent != activeAgent) {
                    viewModel.setAgent(agent)
                }
            }
        )

        // Model Complexity Selector (Gemini 3.5 Flash, 3.1 Pro, 3.1 Flash Lite, 3.8 Live)
        ModelComplexityBar(
            selectedComplexity = selectedComplexity,
            onSelectComplexity = { viewModel.setComplexity(it) }
        )

        // Live Voice Conversation Banner (Gemini 3.8 Live API)
        AnimatedVisibility(visible = isLiveVoiceActive) {
            LiveVoiceConversationBanner(
                isSpeaking = isSpeakingAudio,
                onStopVoice = { viewModel.toggleLiveVoice() },
                onSendVoiceSample = { prompt -> viewModel.sendMessage(prompt) }
            )
        }

        // Active Agent Info Header
        ActiveAgentHeader(
            agent = activeAgent,
            complexity = selectedComplexity
        )

        // Messages list
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(
                    message = msg,
                    onListen = { viewModel.speakText(msg.text) }
                )
            }

            if (isTyping) {
                item {
                    AgentTypingIndicator(agent = activeAgent)
                }
            }
        }

        // Quick Suggestion Chips
        QuickPromptChips(
            prompts = activeAgent.defaultPrompts,
            onPromptClick = { prompt ->
                viewModel.sendMessage(prompt)
            }
        )

        // Input Field, Voice Mic Button, and Send Button
        ChatInputBar(
            value = inputMessage,
            onValueChange = { inputMessage = it },
            onSend = {
                if (inputMessage.isNotBlank()) {
                    viewModel.sendMessage(inputMessage)
                    inputMessage = ""
                }
            },
            isTyping = isTyping,
            isLiveVoiceActive = isLiveVoiceActive,
            onToggleLiveVoice = { viewModel.toggleLiveVoice() }
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ModelComplexityBar(
    selectedComplexity: AiTaskComplexity,
    onSelectComplexity: (AiTaskComplexity) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AI Model Engine:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = selectedComplexity.modelId,
                    fontSize = 10.5.sp,
                    fontWeight = FontWeight.Medium,
                    color = PinePrimary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(AiTaskComplexity.values()) { complexity ->
                    val isSelected = complexity == selectedComplexity
                    Surface(
                        color = if (isSelected) PinePrimary else MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp),
                        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .clickable { onSelectComplexity(complexity) }
                            .testTag("model_chip_${complexity.name}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = complexity.badge,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LiveVoiceConversationBanner(
    isSpeaking: Boolean,
    onStopVoice: () -> Unit,
    onSendVoiceSample: (String) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0B291E)
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .border(1.dp, PinePrimary, RoundedCornerShape(16.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .scale(scale)
                            .background(PinePrimary.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.GraphicEq else Icons.Default.Mic,
                            contentDescription = null,
                            tint = Color(0xFF34D399),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Gemini 3.8 Live (Live API)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.5.sp,
                            color = Color.White
                        )
                        Text(
                            text = if (isSpeaking) "Live AI speaking response..." else "Live microphone ready • Speak now",
                            fontSize = 11.5.sp,
                            color = Color(0xFFA7F3D0)
                        )
                    }
                }

                Surface(
                    color = Color.Red.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable { onStopVoice() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Stop,
                            contentDescription = "Stop",
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "End Live",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEF4444)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Tap to ask voice prompt:",
                    fontSize = 10.5.sp,
                    color = Color(0xFF9CA3AF),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Surface(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable { onSendVoiceSample("What is the fastest way to get to Shifa Hospital from Daewoo terminal?") }
                ) {
                    Text(
                        text = "Hospital Transit",
                        fontSize = 10.5.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Surface(
                    color = Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.clickable { onSendVoiceSample("What is the cost of renting a 4x4 Prado for 3 days to Murree and Hunza?") }
                ) {
                    Text(
                        text = "4x4 Rates",
                        fontSize = 10.5.sp,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AgentSelectorCarousel(
    selectedAgent: AgentType,
    onSelectAgent: (AgentType) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(
                text = "Specialized AI Chatbot Roles (${AgentType.values().size} Available):",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(AgentType.values()) { agent ->
                    val isSelected = agent == selectedAgent
                    Surface(
                        color = if (isSelected) PinePrimary else MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .clickable { onSelectAgent(agent) }
                            .testTag("agent_tab_${agent.name}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .background(
                                        if (isSelected) Color.White.copy(alpha = 0.2f) else PinePrimary.copy(alpha = 0.1f),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = agent.icon,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else PinePrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = agent.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = agent.subtitle,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActiveAgentHeader(agent: AgentType, complexity: AiTaskComplexity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MountainMint.copy(alpha = 0.25f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(PinePrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = agent.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = agent.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = PinePrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = WarmGold.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = complexity.title,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB45309),
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = agent.subtitle,
                    fontSize = 11.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage, onListen: () -> Unit) {
    val isUser = message.isFromUser

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isUser) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(PinePrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = message.agentType.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            color = if (isUser) PinePrimary else MaterialTheme.colorScheme.surface,
            tonalElevation = if (isUser) 0.dp else 2.dp,
            modifier = Modifier
                .widthIn(max = 310.dp)
                .testTag("chat_bubble_${if (isUser) "user" else "agent"}")
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                if (!isUser) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = message.agentType.title,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = WarmGold
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = PinePrimary.copy(alpha = 0.1f),
                            modifier = Modifier.clickable { onListen() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Listen",
                                    tint = PinePrimary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "Listen",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PinePrimary
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun AgentTypingIndicator(agent: AgentType) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(PinePrimary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = agent.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.padding(4.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(12.dp),
                    strokeWidth = 2.dp,
                    color = PinePrimary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${agent.title} is analyzing...",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun QuickPromptChips(
    prompts: List<String>,
    onPromptClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(
            text = "Suggested Questions:",
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(prompts) { prompt ->
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.clickable { onPromptClick(prompt) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = WarmGold,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = prompt,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    isTyping: Boolean,
    isLiveVoiceActive: Boolean,
    onToggleLiveVoice: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Live Voice API Toggle Button (Gemini 3.8 Live)
            IconButton(
                onClick = onToggleLiveVoice,
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        if (isLiveVoiceActive) Color(0xFF10B981) else MaterialTheme.colorScheme.surfaceVariant,
                        CircleShape
                    )
                    .testTag("chat_voice_mic_button")
            ) {
                Icon(
                    imageVector = if (isLiveVoiceActive) Icons.Default.Mic else Icons.Default.MicOff,
                    contentDescription = "Toggle Gemini 3.8 Live Voice Conversation",
                    tint = if (isLiveVoiceActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Ask about travel, hospitals, 4x4 fares...") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PinePrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onSend,
                enabled = value.isNotBlank() && !isTyping,
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        if (value.isNotBlank() && !isTyping) PinePrimary else Color.Gray.copy(alpha = 0.3f),
                        CircleShape
                    )
                    .testTag("chat_send_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send Message",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

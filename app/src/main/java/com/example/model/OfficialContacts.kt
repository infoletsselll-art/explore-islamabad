package com.example.model

import androidx.compose.ui.graphics.vector.ImageVector

data class SocialForum(
    val id: String,
    val name: String,
    val handleOrTarget: String,
    val category: String, // "Instant Messaging", "Social Media", "Video & Search", "Work & Meetings"
    val description: String,
    val actionType: ActionType,
    val actionUrl: String,
    val isPrimarySupport: Boolean = false
)

enum class ActionType {
    OPEN_URL,
    SEND_EMAIL,
    CALL_PHONE,
    SEND_SMS,
    OPEN_WHATSAPP,
    OPEN_TELEGRAM
}

object OfficialContacts {
    const val APP_NAME = "explore Islmbd"
    const val DOMAIN_NAME = "exploreislmbd.com"
    const val WEBPAGE_URL = "https://www.exploreislmbd.com"
    const val OFFICIAL_EMAIL = "info.explore.islmbd@gmail.com"
    const val OFFICIAL_PHONE = "03457059286"
    const val OFFICIAL_PHONE_INTL = "+923457059286"
    const val SUPPORT_HOURS = "24/7 AI Automation & Live Customer Support Agents"
    const val TAGLINE = "Direct Client Immediate Connection across Pakistan & Abroad"
}

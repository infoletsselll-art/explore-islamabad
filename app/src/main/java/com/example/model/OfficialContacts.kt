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
    const val APP_NAME = "Visit Islamabad"
    const val DOMAIN_NAME = "visitislamabad.web.app"
    const val WEBPAGE_URL = "https://visitislamabad.web.app"
    const val CUSTOM_DOMAIN = "visitislamabad.com"
    const val OFFICIAL_EMAIL = "info.letsselll@gmail.com"
    const val OFFICIAL_PHONE = "03457059286"
    const val OFFICIAL_PHONE_INTL = "+923457059286"
    const val SUPPORT_HOURS = "24/7 AI Automation & All-Purpose Visitor Support"
    const val TAGLINE = "A gateway to the visitors of all types and for all purposes."
}

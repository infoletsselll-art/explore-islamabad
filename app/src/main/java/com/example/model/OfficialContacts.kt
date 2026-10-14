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
    const val OFFICIAL_EMAIL = "info.visitislamabad@gmail.com"
    const val OFFICIAL_PHONE = "03457059286"
    const val OFFICIAL_PHONE_INTL = "+923457059286"
    const val SUPPORT_HOURS = "24/7 AI Automation & All-Purpose Visitor Support"
    const val TAGLINE = "A welcoming gateway for visitors of every kind, serving every purpose."
}

object AboutVisitIslamabad {
    const val TITLE = "VISIT ISLAMABAD"
    const val SUBTITLE = "(About the forum and purpose of that AI based services providing web app.)"

    val GATEWAY_SLOGANS = listOf(
        "A welcoming gateway for visitors of every kind, serving every purpose.",
        "A distinguished gateway, welcoming visitors from every walk of life and serving every purpose.",
        "A Gateway to Every Visitor, Every Purpose, Every Experience.",
        "Your Gateway to Islamabad — Connecting Every Visitor, Every Purpose"
    )

    const val GLOBAL_SERVICES = "Services available 24/7 round the Globe for the convenience of visitors all kinds and all purposes . national and international visitors can get customize tailor based services according to their needs and demands. Can plane and book their trip before starting their journey to Islamabad."

    const val AI_AGENTS_DESCRIPTION = "Our AI based services would provide them full guidance and support and Our AI agents would do each and every task ( transportation , hotel or feasible staying facilities , all information about shopping , dinning or any type service they need. we have make this as easy as we can for the Best experience of visitors. they can book or plan their visit by just asking to our live AI agent and it would be done in minutes)"

    const val MULTI_PURPOSE_SUMMARY = "Whether you are visiting Islamabad for medical consultations, Supreme Court or High Court proceedings, university entrance examinations, business and shopping, sightseeing, or travelling onward to the majestic Northern Areas, we make your stay seamless from arrival to departure."
}


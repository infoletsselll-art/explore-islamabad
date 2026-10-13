package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class UserProfileFirestore(
    val displayName: String = "",
    val email: String = "",
    val loyaltyPoints: Long = 0L,
    val prepaidBalance: Long = 0L,
    val referralCode: String = "",
    val referralEarnings: Long = 0L,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class InquiryFirestore(
    @DocumentId val id: String = "",
    val serviceTitle: String = "",
    val category: String = "",
    val clientName: String = "",
    val contactInfo: String = "",
    val dates: String = "",
    val origin: String = "",
    val details: String = "",
    val estimatedCostPkr: Long = 0L,
    val estimatedCostUsd: Long = 0L,
    val status: String = "Pending",
    @ServerTimestamp val createdAt: Timestamp? = null
)

@IgnoreExtraProperties
data class BookmarkFirestore(
    @DocumentId val id: String = "",
    val itemType: String = "",
    val title: String = "",
    val subtitle: String = "",
    val category: String = "",
    val detailSnippet: String = "",
    val priceOrDistance: String = "",
    val ratingOrVehicle: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null
)

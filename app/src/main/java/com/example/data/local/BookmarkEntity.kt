package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val id: String, // unique ID of destination, hotel, or transport service
    val itemType: String, // "DESTINATION", "HOTEL", "TRANSPORT", "CAMPING", "TOUR"
    val title: String,
    val subtitle: String,
    val category: String,
    val detailSnippet: String,
    val priceOrDistance: String,
    val ratingOrVehicle: String,
    val createdAt: Long = System.currentTimeMillis()
)

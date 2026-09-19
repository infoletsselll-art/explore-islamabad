package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inquiries")
data class InquiryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clientName: String,
    val contactInfo: String,
    val serviceTitle: String,
    val category: String,
    val origin: String,
    val dates: String,
    val details: String,
    val estimatedCostPkr: Long,
    val estimatedCostUsd: Int,
    val status: String = "Inquiry Sent",
    val createdAt: Long = System.currentTimeMillis()
)

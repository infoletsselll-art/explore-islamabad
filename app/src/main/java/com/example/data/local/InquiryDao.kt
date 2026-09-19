package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface InquiryDao {
    @Query("SELECT * FROM inquiries ORDER BY createdAt DESC")
    fun getAllInquiries(): Flow<List<InquiryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInquiry(inquiry: InquiryEntity): Long

    @Delete
    suspend fun deleteInquiry(inquiry: InquiryEntity)

    @Query("DELETE FROM inquiries WHERE id = :id")
    suspend fun deleteById(id: Long)
}

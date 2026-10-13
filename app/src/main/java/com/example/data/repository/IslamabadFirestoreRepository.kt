package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.OperationType
import com.example.data.handleFirestoreError
import com.example.data.model.BookmarkFirestore
import com.example.data.model.InquiryFirestore
import com.example.data.model.UserProfileFirestore
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class IslamabadFirestoreRepository(
    private val firestore: FirebaseFirestore
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    fun observeUserProfile(userId: String): Flow<UserProfileFirestore?> = callbackFlow {
        if (userId.isBlank()) {
            trySend(null)
            close()
            return@callbackFlow
        }
        val docRef = firestore.collection("users").document(userId)
        val listener = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                handleFirestoreError(error, OperationType.GET, docRef.path)
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                val profile = snapshot.toObject(
                    UserProfileFirestore::class.java,
                    DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
                )
                trySend(profile)
            } else {
                trySend(null)
            }
        }
        awaitClose { listener.remove() }
    }

    suspend fun saveUserProfile(userId: String, profile: UserProfileFirestore) {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        val docRef = firestore.collection("users").document(userId)
        try {
            val payload = hashMapOf<String, Any>(
                "displayName" to profile.displayName,
                "email" to profile.email,
                "loyaltyPoints" to profile.loyaltyPoints,
                "prepaidBalance" to profile.prepaidBalance,
                "referralCode" to profile.referralCode,
                "referralEarnings" to profile.referralEarnings,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            throw e
        }
    }

    fun observeInquiries(userId: String): Flow<List<InquiryFirestore>> {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        val collRef = firestore.collection("users").document(userId).collection("inquiries")
        return collRef.snapshots().map { snapshot ->
            snapshot.toObjects(
                InquiryFirestore::class.java,
                DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
            )
        }
    }

    suspend fun createInquiry(userId: String, inquiry: InquiryFirestore): String {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        val collRef = firestore.collection("users").document(userId).collection("inquiries")
        val docRef = collRef.document()
        try {
            val payload = hashMapOf<String, Any>(
                "serviceTitle" to inquiry.serviceTitle,
                "category" to inquiry.category,
                "clientName" to inquiry.clientName,
                "contactInfo" to inquiry.contactInfo,
                "dates" to inquiry.dates,
                "origin" to inquiry.origin,
                "details" to inquiry.details,
                "estimatedCostPkr" to inquiry.estimatedCostPkr,
                "estimatedCostUsd" to inquiry.estimatedCostUsd,
                "status" to inquiry.status,
                "createdAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            return docRef.id
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            throw e
        }
    }

    fun observeBookmarks(userId: String): Flow<List<BookmarkFirestore>> {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        val collRef = firestore.collection("users").document(userId).collection("bookmarks")
        return collRef.snapshots().map { snapshot ->
            snapshot.toObjects(
                BookmarkFirestore::class.java,
                DocumentSnapshot.ServerTimestampBehavior.ESTIMATE
            )
        }
    }

    suspend fun saveBookmark(userId: String, bookmark: BookmarkFirestore) {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        require(bookmark.id.isNotBlank()) { "Bookmark ID must not be blank" }
        val docRef = firestore.collection("users").document(userId).collection("bookmarks").document(bookmark.id)
        try {
            val payload = hashMapOf<String, Any>(
                "itemType" to bookmark.itemType,
                "title" to bookmark.title,
                "subtitle" to bookmark.subtitle,
                "category" to bookmark.category,
                "detailSnippet" to bookmark.detailSnippet,
                "priceOrDistance" to bookmark.priceOrDistance,
                "ratingOrVehicle" to bookmark.ratingOrVehicle,
                "createdAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            throw e
        }
    }

    suspend fun removeBookmark(userId: String, bookmarkId: String) {
        require(userId.isNotBlank()) { "User ID must not be blank" }
        require(bookmarkId.isNotBlank()) { "Bookmark ID must not be blank" }
        val docRef = firestore.collection("users").document(userId).collection("bookmarks").document(bookmarkId)
        try {
            docRef.delete().await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            throw e
        }
    }
}

package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.model.BookmarkFirestore
import com.example.data.model.InquiryFirestore
import com.example.data.model.UserProfileFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class IslamabadFirestoreRepositoryRuleTest : FirestoreEmulatorTestBase() {

  @Test
  fun userProfile_authenticatedOwner_createsAndReadsSuccessfully() = runBlocking {
    val userId = signInTestUser("traveler1@example.com")
    val repository = IslamabadFirestoreRepository(firestore)

    val profile = UserProfileFirestore(
      displayName = "Ahmed Khan",
      email = "traveler1@example.com",
      loyaltyPoints = 150L,
      prepaidBalance = 5000L,
      referralCode = "VI-AHMED-10",
      referralEarnings = 1000L
    )

    repository.saveUserProfile(userId, profile)
    val fetched = repository.observeUserProfile(userId).first()
    assertNotNull(fetched)
    assertEquals("Ahmed Khan", fetched?.displayName)
    assertEquals(150L, fetched?.loyaltyPoints)
  }

  @Test
  fun inquiry_authenticatedOwner_createsAndListsSuccessfully() = runBlocking {
    val userId = signInTestUser("guest2@example.com")
    val repository = IslamabadFirestoreRepository(firestore)

    val inquiry = InquiryFirestore(
      serviceTitle = "Executive Chauffeur Margalla",
      category = "Transport",
      clientName = "Ali Raza",
      contactInfo = "03001234567",
      dates = "Tomorrow 9am",
      origin = "Islamabad Airport",
      details = "Flight PA-201 arrival",
      estimatedCostPkr = 4500L,
      estimatedCostUsd = 16L,
      status = "Pending"
    )

    val inqId = repository.createInquiry(userId, inquiry)
    assertNotNull(inqId)

    val list = repository.observeInquiries(userId).first()
    assertTrue(list.isNotEmpty())
    assertEquals("Executive Chauffeur Margalla", list.first().serviceTitle)
  }

  @Test
  fun inquiry_unauthenticatedUser_rejectedBySecurityRules() = runBlocking {
    val userId = signInTestUser("user3@example.com")
    val repository = IslamabadFirestoreRepository(firestore)
    auth.signOut()

    val inquiry = InquiryFirestore(
      serviceTitle = "High Court Proceeding Escort",
      category = "Judicial",
      clientName = "Barrister Tariq",
      contactInfo = "03009998877",
      status = "Pending"
    )

    try {
      repository.createInquiry(userId, inquiry)
      fail("Expected FirebaseFirestoreException for unauthenticated write")
    } catch (e: FirebaseFirestoreException) {
      assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, e.code)
    }
  }

  @Test
  fun crossUser_write_rejectedBySecurityRules() = runBlocking {
    val aliceId = signInTestUser("alice_rule@example.com")
    val bobId = signInTestUser("bob_rule@example.com")
    val repository = IslamabadFirestoreRepository(firestore)

    // Alice is signed in, tries to write to Bob's inquiry collection
    signInTestUser("alice_rule@example.com")
    val inquiry = InquiryFirestore(
      serviceTitle = "Unauthorized Entry",
      category = "Transport",
      clientName = "Hacker",
      contactInfo = "000000",
      status = "Pending"
    )

    try {
      repository.createInquiry(bobId, inquiry)
      fail("Expected PERMISSION_DENIED when Alice writes to Bob's inquiries")
    } catch (e: FirebaseFirestoreException) {
      assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, e.code)
    }
  }

  @Test
  fun bookmark_authenticatedOwner_savesAndRemoves() = runBlocking {
    val userId = signInTestUser("bookmark_user@example.com")
    val repository = IslamabadFirestoreRepository(firestore)

    val bookmark = BookmarkFirestore(
      id = "dest_monument",
      itemType = "DESTINATION",
      title = "Pakistan Monument",
      subtitle = "Shakarparian Hills",
      category = "Landmarks"
    )

    repository.saveBookmark(userId, bookmark)
    val bookmarks = repository.observeBookmarks(userId).first()
    assertEquals(1, bookmarks.size)
    assertEquals("Pakistan Monument", bookmarks.first().title)

    repository.removeBookmark(userId, "dest_monument")
    val emptyBookmarks = repository.observeBookmarks(userId).first()
    assertTrue(emptyBookmarks.isEmpty())
  }
}

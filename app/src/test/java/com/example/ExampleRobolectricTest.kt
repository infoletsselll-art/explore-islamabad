package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.SampleData
import com.example.data.local.AppDatabase
import com.example.data.local.InquiryEntity
import com.example.model.AgentType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Islamabad AI", appName)
    }

    @Test
    fun `verify 7 AI agents are configured`() {
        val agents = AgentType.values()
        assertEquals(7, agents.size)
        assertTrue(agents.contains(AgentType.CITY_GUIDE))
        assertTrue(agents.contains(AgentType.HILL_STATIONS))
        assertTrue(agents.contains(AgentType.NORTHERN_GATEWAY))
        assertTrue(agents.contains(AgentType.HOTEL_CONCIERGE))
        assertTrue(agents.contains(AgentType.TRANSPORT_FLEET))
        assertTrue(agents.contains(AgentType.CAMPING_ADVENTURE))
        assertTrue(agents.contains(AgentType.CUSTOM_TOUR_PLANNER))
    }

    @Test
    fun `verify sample destinations and services exist`() {
        assertTrue(SampleData.destinations.isNotEmpty())
        assertTrue(SampleData.travelServices.isNotEmpty())
        assertTrue(SampleData.destinations.any { it.name.contains("Faisal Mosque") })
        assertTrue(SampleData.destinations.any { it.name.contains("Murree") || it.name.contains("Nathia") })
        assertTrue(SampleData.travelServices.any { it.category == "Pick & Drop" })
        assertTrue(SampleData.travelServices.any { it.category == "Camping & Adventure" })
    }

    @Test
    fun `verify room database inquiry insert and retrieval`() = runBlocking {
        val dao = database.inquiryDao()
        val inquiry = InquiryEntity(
            clientName = "Sarah Jenkins",
            contactInfo = "+447123456789",
            serviceTitle = "Airport ISB 24/7 Pick & Drop",
            category = "Pick & Drop",
            origin = "London, UK",
            dates = "2026-10-15",
            details = "Arrival at 04:30 AM via Qatar Airways",
            estimatedCostPkr = 5500,
            estimatedCostUsd = 20,
            status = "Inquiry Sent"
        )
        val id = dao.insertInquiry(inquiry)
        assertTrue(id > 0)

        val all = dao.getAllInquiries().first()
        assertEquals(1, all.size)
        assertEquals("Sarah Jenkins", all[0].clientName)
        assertEquals("London, UK", all[0].origin)
    }
}

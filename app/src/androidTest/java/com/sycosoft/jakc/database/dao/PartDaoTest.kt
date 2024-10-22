package com.sycosoft.jakc.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sycosoft.jakc.database.AppDatabase
import com.sycosoft.jakc.database.entities.Part
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

class PartDaoTest {
// region Test Setup

    private lateinit var db: AppDatabase
    private lateinit var dao: PartDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).allowMainThreadQueries().build()

        dao = db.partDao
    }

    @After
    fun teardown() {
        db.close()
    }

// endregion
// region Query Tests

    // region Get Parts By Project ID

    @Test
    fun whenGettingPartsByProjectId_givenValidProjectId_thenPartsShouldBeReturned() = runBlocking {
        val part1 = Part(id = 1, name = "Part 1", owningProjectId = 1)
        val part2 = Part(id = 2, name = "Part 2", owningProjectId = 2)

        dao.insertPart(part1)
        dao.insertPart(part2)

        val parts = dao.getPartsByProjectId(1)

        assertEquals(2, parts.size)
        assertEquals(part1, parts[0])
        assertEquals(part2, parts[1])
    }

    @Test
    fun whenGettingPartsByProjectId_givenInvalidProjectId_thenEmptyListShouldBeReturned() = runBlocking {
        val part = Part(id = 1, name = "Part 1", owningProjectId = 1)
        dao.insertPart(part)

        val parts = dao.getPartsByProjectId(2)

        assertEquals(0, parts.size)
    }

    @Test
    fun whenGettingPartsByProjectId_givenMultipleProjectParts_thenAllPartsShouldBeReturnedLinkedToProject() = runBlocking {
        val part1 = Part(id = 1, name = "Part 1", owningProjectId = 1)
        val part2 = Part(id = 2, name = "Part 2", owningProjectId = 2)

        dao.insertPart(part1)
        dao.insertPart(part2)

        val parts = dao.getPartsByProjectId(part1.id())

        assertEquals(1, parts.size)
        assertEquals(part1, parts[0])
        assertNotEquals(part2, parts[0])
    }

    @Test
    fun whenGettingPartsByProjectId_givenEmptyDatabase_thenEmptyListShouldBeReturned() = runBlocking {
        val parts = dao.getPartsByProjectId(1)

        assertEquals(0, parts.size)
    }

    // endregion
    // region Insert Part




    // endregion
    // region Update Part



    // endregion
    // region Does Part Exist



    // endregion
    // region Delete Part



    // endregion

// endregion
}
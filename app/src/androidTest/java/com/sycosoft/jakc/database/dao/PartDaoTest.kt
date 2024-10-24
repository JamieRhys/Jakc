package com.sycosoft.jakc.database.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sycosoft.jakc.database.AppDatabase
import com.sycosoft.jakc.database.entities.EntityPart
import com.sycosoft.jakc.database.entities.Part
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
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
        val part1 = EntityPart(id = 1, name = "Part 1", owningProjectId = 1)
        val part2 = EntityPart(id = 2, name = "Part 2", owningProjectId = 1)

        dao.insertPart(part1)
        dao.insertPart(part2)

        val parts = dao.getPartsByProjectId(1)

        assertEquals(2, parts.size)
        assertEquals("Expected Part 1 to have an ID of 1", part1, parts[0])
        assertEquals("Expected Part 2 to have an ID of 2", part2, parts[1])
    }

    @Test
    fun whenGettingPartsByProjectId_givenInvalidProjectId_thenEmptyListShouldBeReturned() = runBlocking {
        val part = EntityPart(id = 1, name = "Part 1", owningProjectId = 1)
        dao.insertPart(part)

        val parts = dao.getPartsByProjectId(2)

        assertEquals(0, parts.size)
    }

    @Test
    fun whenGettingPartsByProjectId_givenMultipleProjectParts_thenAllPartsShouldBeReturnedLinkedToProject() = runBlocking {
        val part1 = EntityPart(id = 1, name = "Part 1", owningProjectId = 1)
        val part2 = EntityPart(id = 2, name = "Part 2", owningProjectId = 2)

        dao.insertPart(part1)
        dao.insertPart(part2)

        val parts = dao.getPartsByProjectId(part1.id)

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

    @Test
    fun whenInsertingPart_givenValidPart_thenIdShouldBeReturned() = runBlocking {
        val id = dao.insertPart(EntityPart(name = "Test Part", owningProjectId = 1))

        val part = dao.getPartsByProjectId(id)

        assertEquals(1, id)
        assertEquals("Test Part", part[0].name)
    }

    @Test
    fun whenInsertingPart_givenPartWithoutId_thenIdShouldBeReturned() = runBlocking {
        val id = dao.insertPart(EntityPart(name = "Test Part", owningProjectId = 1))

        val part = dao.getPartsByProjectId(id)

        assertEquals(1, part[0].id)
    }

    @Test
    fun whenInsertingPart_givenMultipleIdLessParts_thenIdsShouldBeReturned() = runBlocking {
        val id1 = dao.insertPart(EntityPart(name = "Test Part 1", owningProjectId = 1))
        val id2 = dao.insertPart(EntityPart(name = "Test Part 2", owningProjectId = 1))

        assertTrue(id2 > id1)
    }

    @Test
    fun whenInsertingPart_givenDuplicateId_thenExceptionShouldBeThrown(): Unit = runBlocking {
        val part1 = EntityPart(id = 1, name = "Test Part 1", owningProjectId = 1)
        val part2 = EntityPart(id = 1, name = "Test Part 2", owningProjectId = 1)

        dao.insertPart(part1)

        assertThrows(SQLiteConstraintException::class.java) {
            runBlocking {
                dao.insertPart(part2) // Should throw exception.
            }
        }
    }

    // endregion
    // region Update Part

    @Test
    fun whenUpdatingPart_givenValidPart_thenPartShouldBeUpdated() = runBlocking {
        val part = EntityPart(id = 1, name = "Test Part", owningProjectId = 1)
        dao.insertPart(part)

        val rowUpdated = dao.updatePart(EntityPart(id = 1, name = "Updated Part", owningProjectId = 1))
        val updatedPart = dao.getPartsByProjectId(part.owningProjectId)[0]

        assertEquals(1, rowUpdated)
        assertEquals("Updated Part", updatedPart.name)
    }

    @Test
    fun whenUpdatingPart_givenInvalidPart_thenNoPartShouldBeUpdated() = runBlocking {
        val part = EntityPart(id = 1, name = "Test Part", owningProjectId = 1)
        dao.insertPart(part)

        val rowsUpdated = dao.updatePart(part.copy(id = 3, name = "Updated Part"))
        val updatedPart = dao.getPartsByProjectId(1)

        assertEquals(0, rowsUpdated)
        assertEquals("Test Part", updatedPart[0].name)
    }

    // endregion
    // region Does Part Exist

    @Test
    fun whenDoesPartExist_givenValidId_thenTrueShouldBeReturned() = runBlocking {
        val part = EntityPart(id = 1, name = "Test Part", owningProjectId = 1)
        dao.insertPart(part)

        assertTrue(dao.doesPartExist(part.id))
    }

    @Test
    fun whenDoesPartExist_givenInvalidId_thenFalseShouldBeReturned() = runBlocking {
        val part = EntityPart(id = 1, name = "Test Part", owningProjectId = 1)
        dao.insertPart(part)

        assertFalse(dao.doesPartExist(part.id + 1))
    }

    @Test
    fun whenDoesPartExist_givenEmptyDatabase_thenFalseShouldBeReturned() = runBlocking {
        assertFalse(dao.doesPartExist(1))
    }

    @Test
    fun whenDoesProjectExist_givenDeletedPart_thenFalseShouldBeReturned() = runBlocking {
        val part = EntityPart(id = 1, name = "Test Part", owningProjectId = 1)
        dao.insertPart(part)
        dao.deletePart(part.id)

        assertFalse(dao.doesPartExist(part.id))
    }

    // endregion
    // region Delete Part

    @Test
    fun whenDeletingPart_givenValidId_thenPartShouldBeDeleted() = runBlocking {
        val part = EntityPart(id = 1, name = "Test Part", owningProjectId = 1)
        dao.insertPart(part)

        val rowsDeleted = dao.deletePart(part.id)
        val parts = dao.getPartsByProjectId(part.owningProjectId)

        assertEquals(1, rowsDeleted)
        assertEquals(0, parts.size)
    }

    @Test
    fun whenDeletingPart_givenInvalidId_thenNoPartShouldBeDeleted() = runBlocking {
        val part = EntityPart(id = 1, name = "Test Part", owningProjectId = 1)
        dao.insertPart(part)

        val rowsDeleted = dao.deletePart(part.id + 1)
        val parts = dao.getPartsByProjectId(part.owningProjectId)

        assertEquals(0, rowsDeleted)
        assertEquals(1, parts.size)
        assertEquals("Test Part", parts[0].name)
    }

    @Test
    fun whenDeletingPart_givenEmptyDatabase_thenNoPartShouldBeDeleted() = runBlocking {
        val rowsDeleted = dao.deletePart(1)

        assertEquals(0, rowsDeleted)
    }

    @Test
    fun whenDeletingPart_givenMultipleParts_thenOnlyOnePartShouldBeDeleted() = runBlocking {
        val part1 = EntityPart(id = 1, name = "Test Part 1", owningProjectId = 1)
        val part2 = EntityPart(id = 2, name = "Test Part 2", owningProjectId = 1)

        dao.insertPart(part1)
        dao.insertPart(part2)

        val rowsDeleted = dao.deletePart(part1.id)
        val parts = dao.getPartsByProjectId(part1.owningProjectId)

        assertEquals(1, rowsDeleted)
        assertEquals(1, parts.size)
        assertEquals("Test Part 2", parts[0].name)
    }

    @Test
    fun whenDeletingPart_giveDeletedProject_thenNoProjectShouldBeDeleted() = runBlocking {
        val part = EntityPart(id = 1, name = "Test Part", owningProjectId = 1)
        dao.insertPart(part)
        dao.deletePart(part.id)

        val rowsDeleted = dao.deletePart(part.id)
        val parts = dao.getPartsByProjectId(part.owningProjectId)

        assertEquals(0, rowsDeleted)
        assertEquals(0, parts.size)
    }

    // endregion

// endregion
}
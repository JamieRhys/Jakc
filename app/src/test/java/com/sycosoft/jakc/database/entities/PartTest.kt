package com.sycosoft.jakc.database.entities

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PartTest {
// region Part Constructor Tests

    // Tests the constructor of the Part class with default values.
    @Test
    fun whenPartConstructor_givenDefaultValues_thenPropertiesAreSetCorrectly() {
        val part = Part(id = 1, name = "Test Part", owningProjectId = 1)

        assertEquals(1, part.id())
        assertEquals("Test Part", part.name())
        assertEquals(1, part.owningProjectId())
        assertEquals("", part.description())
        assertEquals(false, part.isCurrent())
    }

    // Tests to make sure that when the constructor is passed a blank name, it throws an exception.
    @Test(expected = IllegalArgumentException::class)
    fun whenPartConstructor_givenBlankName_thenExceptionIsThrow() {
        Part(id = 1, name = "", owningProjectId = 1)
    }

    // Tests to make sure that when the constructor is passed custom values, it correctly initialises the class.
    @Test
    fun whenPartConstructor_givenCustomValues_thenPartIsCorrectlyInitialised() {
        val part = Part(
            id = 1,
            name = "Test Part",
            description = "This is a test part",
            owningProjectId = 1,
            isCurrent = true,
        )

        assertEquals(1, part.id())
        assertEquals("Test Part", part.name())
        assertEquals("This is a test part", part.description())
        assertEquals(1, part.owningProjectId())
        assertEquals(true, part.isCurrent())
    }

// endregion
// region Project Property Tests

    @Test
    fun whenName_givenNewString_thenNameIsCorrectlySet() {
        val part = Part(id = 1, name = "Test Part", owningProjectId = 1)

        part.name("New Name")
        assertEquals("New Name", part.name())
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenName_isGivenBlankName_thenExceptionIsThrown() {
        val part = Part(id = 1, name = "Test Part", owningProjectId = 1)

        part.name("")
    }

    @Test
    fun whenDescription_givenNewString_thenDescriptionIsCorrectlySet() {
        val part = Part(id = 1, name = "Test Part", owningProjectId = 1)

        part.description("New Description")
        assertEquals("New Description", part.description())
    }

    @Test
    fun whenIsCurrent_givenNewBooleanValue_thenIsCurrentIsCorrectlySet() {
        val part = Part(id = 1, name = "Test Part", owningProjectId = 1)

        part.isCurrent(true)
        assertEquals(true, part.isCurrent())
    }

// endregion
}
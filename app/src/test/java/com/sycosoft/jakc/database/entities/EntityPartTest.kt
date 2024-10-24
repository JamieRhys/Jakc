package com.sycosoft.jakc.database.entities

import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EntityPartTest {
    @Test
    fun whenCreatingEntityPart_givenValidData_thenObjectShouldBeCreated() {
        val part = EntityPart(
            id = 1,
            name = "Test Part",
            description = "This is a test Part",
            owningProjectId = 1,
            isCurrent = true,
            isComplete = true,
        )

        assertEquals(1, part.id)
        assertEquals("Test Part", part.name)
        assertEquals("This is a test Part", part.description)
        assertEquals(1, part.owningProjectId)
        assertEquals(true, part.isCurrent)
        assertEquals(true, part.isComplete)
    }

    @Test
    fun whenCreatingEntityPart_givenDefaultValues_thenObjectShouldBeCreated() {
        val part = EntityPart(
            name = "Test Part",
            owningProjectId = 1,
        )

        assertEquals(0, part.id)
        assertEquals("Test Part", part.name)
        assertEquals("", part.description)
        assertEquals(1, part.owningProjectId)
        assertEquals(false, part.isCurrent)
        assertEquals(false, part.isComplete)
    }

    @Test
    fun whenCreatingEntityPart_givenBlankName_thenExceptionShouldBeThrown() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Part(name = "", owningProjectId = 1,)
        }

        assertEquals(Part.ExceptionMessages.NAME_CANNOT_BE_BLANK, exception.message)
    }

    @Test
    fun whenCreatingEntityPart_givenNameLongerThanAllowedCharacters_thenExceptionShouldBeThrown() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            EntityPart(name = "a".repeat(EntityPart.NAME_LENGTH + 1), owningProjectId = 1)
        }

        assertEquals(EntityPart.ExceptionMessages.NAME_TOO_LONG, exception.message)
    }
}
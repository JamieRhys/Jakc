package com.sycosoft.jakc.database.entities

import com.sycosoft.jakc.utils.CounterType
import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EntityCounterTest {
    @Test
    fun whenCreatingEntityCounter_givenValidData_thenObjectShouldBeCreated() {
        val counter = EntityCounter(
            id = 1,
            name = "Test Counter",
            description = "This is a test counter",
            currentValue = 10,
            incrementBy = 5,
            type = CounterType.Global,
            isGloballyLinked = true,
            resetRow = 2,
            maxResets = 3,
            currentResets = 1,
            owningPartId = 1,
        )

        assertEquals(1, counter.id)
        assertEquals("Test Counter", counter.name)
        assertEquals("This is a test counter", counter.description)
        assertEquals(10, counter.currentValue)
        assertEquals(5, counter.incrementBy)
        assertEquals(CounterType.Global, counter.type)
        assertEquals(true, counter.isGloballyLinked)
        assertEquals(2, counter.resetRow)
        assertEquals(3, counter.maxResets)
        assertEquals(1, counter.currentResets)
        assertEquals(1, counter.owningPartId)
    }

    @Test
    fun whenCreatingEntityCounter_givenDefaultValues_thenObjectShouldBeCreated() {
        val counter = EntityCounter(
            name = "Test Counter",
            owningPartId = 1,
        )

        assertEquals(0, counter.id)
        assertEquals("Test Counter", counter.name)
        assertEquals("", counter.description)
        assertEquals(0, counter.currentValue)
        assertEquals(1, counter.incrementBy)
        assertEquals(CounterType.Normal, counter.type)
        assertEquals(true, counter.isGloballyLinked)
        assertEquals(0, counter.resetRow)
        assertEquals(0, counter.maxResets)
        assertEquals(0, counter.currentResets)
        assertEquals(1, counter.owningPartId)
    }

    @Test
    fun whenCreatingEntityCounter_givenBlankName_thenExceptionShouldBeThrown() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            EntityCounter(name = "", owningPartId = 1)
        }

        assertEquals(EntityCounter.ExceptionMessages.NAME_CANNOT_BE_BLANK, exception.message)
    }

    @Test
    fun whenCreatingEntityCounter_givenNameLongerThanAllowedCharacters_thenExceptionShouldBeThrown() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            EntityCounter(name = "a".repeat(EntityCounter.NAME_LENGTH + 1), owningPartId = 1)
        }

        assertEquals(EntityCounter.ExceptionMessages.NAME_TOO_LONG, exception.message)
    }
}
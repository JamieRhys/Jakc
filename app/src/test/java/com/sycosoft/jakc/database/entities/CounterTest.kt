package com.sycosoft.jakc.database.entities

import com.sycosoft.jakc.utils.CounterType
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CounterTest {
// region Counter Constructor Tests

    // Tests the constructor with default values.
    @Test
    fun whenCounterConstructor_givenDefaultValues_thenPropertiesAreSetCorrectly() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(1, counter.id())
        assertEquals("Test Counter", counter.name())
        assertEquals("", counter.description())
        assertEquals(0, counter.currentValue())
        assertEquals(false, counter.canGoNegative())
        assertEquals(1, counter.incrementBy())
        assertEquals(CounterType.Normal, counter.type())
        assertEquals(false, counter.isGloballyLinked())
        assertEquals(0, counter.resetRow())
        assertEquals(0, counter.maxResets())
        assertEquals(0, counter.currentResets())
        assertEquals(1, counter.owningPartId())
    }

    // Tests the constructor to make sure that when a blank name is passed, it throws an exception
    @Test(expected = IllegalArgumentException::class)
    fun whenCounterConstructor_givenBlankName_thenExceptionIsThrown() {
        Counter(id = 1, name = "", owningPartId = 1)
    }

    // Tests the constructor with custom values.
    @Test
    fun whenCounterConstructor_givenCustomValues_thenCounterIsCorrectlyInitialised() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            description = "This is a test counter",
            currentValue = 1,
            canGoNegative = true,
            incrementBy = 2,
            type = CounterType.Stitch,
            isGloballyLinked = true,
            resetRow = 2,
            maxResets = 3,
            currentResets = 3,
            owningPartId = 1,
        )

        assertEquals(1, counter.id())
        assertEquals("Test Counter", counter.name())
        assertEquals("This is a test counter", counter.description())
        assertEquals(1, counter.currentValue())
        assertEquals(true, counter.canGoNegative())
        assertEquals(2, counter.incrementBy())
        assertEquals(CounterType.Stitch, counter.type())
        assertEquals(true, counter.isGloballyLinked())
        assertEquals(2, counter.resetRow())
        assertEquals(3, counter.maxResets())
        assertEquals(3, counter.currentResets())
        assertEquals(1, counter.owningPartId())
    }

// endregion
// region Counter Property Tests

    @Test
    fun whenCounterIdRequested_thenIdIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(1, counter.id())
    }

    @Test
    fun whenCounterNameRequested_thenNameIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals("Test Counter", counter.name())
    }

    @Test
    fun whenCounterNameSet_givenValidString_thenNameIsCorrectlyChanged() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        counter.name("New Name")
        assertEquals("New Name", counter.name())
    }

    @Test(expected = IllegalArgumentException::class)
    fun whenCounterNameSet_givenBlankString_thenExceptionIsThrown() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        counter.name("")
    }

    @Test
    fun whenCounterDescriptionRequested_thenDescriptionIsReturned() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            description = "Test description"
        )

        assertEquals("Test description", counter.description())
    }

    @Test
    fun whenCounterDescription_givenValidString_thenDescriptionIsCorrectlyChanged() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            description = "Test description"
        )

        counter.description("New description")

        assertEquals("New description", counter.description())
    }

    @Test
    fun whenCounterCurrentValueRequested_thenCurrentValueIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(0, counter.currentValue())
    }

    @Test
    fun whenCounterCanGoNegativeRequested_thenCanGoNegativeIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(false, counter.canGoNegative())
    }

    @Test
    fun whenCounterCanGoNegative_givenValidValue_thenCanGoNegativeIsCorrectlyChanged() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        counter.canGoNegative(true)

        assertEquals(true, counter.canGoNegative())
    }

    @Test
    fun whenCounterIncremented_givenCurrentResetsEqualsMaxResets_thenCurrentValueIsNotIncremented() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 5,
            maxResets = 5,
            currentResets = 5,
        )

        counter.increment()

        assertEquals(0, counter.currentValue())
    }

    @Test
    fun whenCounterIncremented_givenCurrentValueEqualToResetRow_thenCurrentValueIsResetToZero() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 5,
            maxResets = 10,
            currentValue = 5,
        )

        counter.increment()

        assertEquals(0, counter.currentValue())
        assertEquals(1, counter.currentResets())
    }

    @Test
    fun whenCounterIncremented_givenCurrentValueIsFour_thenCurrentValueIsIncrementedButNotReset() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 5,
            maxResets = 10,
            currentValue = 4,
        )

        counter.increment()

        assertEquals(5, counter.currentValue())
        assertEquals(0, counter.currentResets())
    }

    @Test
    fun whenCounterIncremented_givenResetRowIsZero_thenCurrentValueIsIncremented() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
        )

        counter.increment()

        assertEquals(1, counter.currentValue())
    }

    @Test
    fun whenCounterDecrement_givenCurrentValueIsZeroAndSetToNotGoNegative_thenCurrentValueIsSetToZero() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
        )

        counter.decrement()

        assertEquals(0, counter.currentValue())
    }

    @Test
    fun whenCounterDecrement_givenCurrentValueIsOneAndSetToNotGoNegativeWithIncrementBySetToTen_thenCurrentValueIsSetToZero() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            incrementBy = 10,
            currentValue = 1
        )

        counter.decrement()

        assertEquals(0, counter.currentValue())
    }

    @Test
    fun whenCounterDecrement_givenCanGoNegative_thenCurrentValueIsSetToIncrementBy() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            canGoNegative = true,
        )

        counter.decrement()

        assertEquals(-1, counter.currentValue())
    }

    // TODO: Test decrement code thoroughly.

    @Test
    fun whenCounterIncrementByRequested_thenIncrementByIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(1, counter.incrementBy())
    }

    @Test
    fun whenCounterIncrementBy_givenValidValue_thenIncrementByIsCorrectlyChanged() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        counter.incrementBy(2)

        assertEquals(2, counter.incrementBy())
    }

    @Test
    fun whenCounterTypeRequested_thenTypeIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(CounterType.Normal, counter.type())
    }

    @Test
    fun whenCounterIsGloballyLinkedRequested_thenIsGloballyLinkedIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(false, counter.isGloballyLinked())
    }

    @Test
    fun whenCounterIsGloballyLinked_givenValidValue_thenIsGloballyLinkedIsCorrectlyChanged() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        counter.isGloballyLinked(true)

        assertEquals(true, counter.isGloballyLinked())
    }

    @Test
    fun whenCounterResetRowRequested_thenResetRowIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(0, counter.resetRow())
    }

    @Test
    fun whenCounterResetRow_givenValidValue_thenResetRowIsCorrectlyChanged() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        counter.resetRow(1)

        assertEquals(1, counter.resetRow())
    }

    @Test
    fun whenCounterMaxResetsRequested_thenMaxResetsIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(0, counter.maxResets())
    }

    @Test
    fun whenCounterMaxResets_givenValidValue_thenMaxResetsIsCorrectlyChanged() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        counter.maxResets(1)

        assertEquals(1, counter.maxResets())
    }

    @Test
    fun whenCounterCurrentResetsRequested_thenCurrentResetsIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(0, counter.currentResets())
    }

    @Test
    fun whenCurrentResetsIncremented_givenResetRowIsGreaterThanZero_butNotEqualToMaxResets_thenCurrentResetsIsIncremented() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 5,
            maxResets = 10,
        )

        counter.incrementCurrentResets()

        assertEquals(1, counter.currentResets())
    }

    @Test
    fun whenCurrentResetsIncremented_givenResetRowIsZero_thenCurrentResetsIsNotIncremented() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 0,
        )

        counter.incrementCurrentResets()

        assertEquals(0, counter.currentResets())
    }

    @Test
    fun whenCurrentResetsIncremented_givenCurrentRowAndMaxResetsAreEqual_thenCurrentResetsIsNotIncremented() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 5,
            maxResets = 5,
            currentResets = 5,
        )

        counter.incrementCurrentResets()

        assertEquals(5, counter.currentResets())
    }

    @Test
    fun whenCurrentResetsDecremented_givenResetRowIsGreaterThanZero_butNotEqualToMaxResets_thenCurrentResetsIsDecremented() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 5,
            maxResets = 10,
            currentResets = 2
        )

        counter.decrementCurrentResets()

        assertEquals(1, counter.currentResets())
    }

    @Test
    fun whenCurrentResetsDecremented_givenResetRowIsZero_thenCurrentResetsIsNotDecremented() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 0,
        )

        counter.decrementCurrentResets()

        assertEquals(0, counter.currentResets())
    }

    @Test
    fun whenCurrentResetsDecremented_givenCurrentRowIsEqualToZero_thenCurrentResetsIsNotDecremented() {
        val counter = Counter(
            id = 1,
            name = "Test Counter",
            owningPartId = 1,
            resetRow = 5,
            maxResets = 5,
            currentResets = 0,
        )

        counter.decrementCurrentResets()

        assertEquals(0, counter.currentResets())
    }

    @Test
    fun whenCounterOwningPartIdRequested_thenOwningPartIdIsReturned() {
        val counter = Counter(id = 1, name = "Test Counter", owningPartId = 1)

        assertEquals(1, counter.owningPartId())
    }

// endregion
}
package com.sycosoft.jakc.database.converters

import org.junit.Test
import org.junit.Assert.assertEquals
import java.time.LocalDate

class LocalDateConverterTest {
    private val converter = LocalDateConverter()

    @Test
    fun testFromTimestamp() {
        val date = LocalDate.of(2023, 12, 20)
        val timestamp = date.toEpochDay()

        val convertedDate = converter.fromTimestamp(timestamp)
        assertEquals(date, convertedDate)
    }

    @Test
    fun testDateToTimestamp() {
        val date = LocalDate.of(2023, 12,20)
        val expectedTimestamp = date.toEpochDay()

        val convertedDate = converter.dateToTimestamp(date)
        assertEquals(expectedTimestamp, convertedDate)
    }

    @Test
    fun testNullValues() {
        val convertedDate = converter.fromTimestamp(null)
        assertEquals(null, convertedDate)

        val timestamp = converter.dateToTimestamp(null)
        assertEquals(null, timestamp)
    }
}
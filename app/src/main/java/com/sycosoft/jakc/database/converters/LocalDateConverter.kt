package com.sycosoft.jakc.database.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class LocalDateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? = value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? = date?.toEpochDay()
}
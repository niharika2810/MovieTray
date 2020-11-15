package com.movietray.base.utils.typeconverter

import androidx.room.TypeConverter
import java.util.*

/**
 * @author Niharika.Arora
 */
class TypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

}
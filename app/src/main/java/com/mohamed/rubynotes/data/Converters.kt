package com.mohamed.rubynotes.data

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

class Converters {

    @TypeConverter
    fun fromLocalDateToString(localDateTime: LocalDateTime?): String{
        return localDateTime.toString()
    }

    @TypeConverter
    fun fromStringToLocalDate(string: String): LocalDateTime{
        return LocalDateTime.parse(string)
    }
}
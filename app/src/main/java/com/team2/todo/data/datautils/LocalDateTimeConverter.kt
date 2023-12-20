package com.team2.todo.data.datautils

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeConverter {

    @TypeConverter
    fun timeStampToString(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }

    @TypeConverter
    fun stringToTimeStamp(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString)
    }
}
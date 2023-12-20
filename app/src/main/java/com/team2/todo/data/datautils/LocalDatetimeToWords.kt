package com.team2.todo.data.datautils

import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

object LocalDatetimeToWords {

    //Takes Localdatetime Object and returns datetime as a readable string (ex : 2023-12-05T17:38 -> December 6,2023 17:38)
    fun formatLocalDateTimeAsWords(localDateTime: LocalDateTime?): String {

        val dayOfWeek =
            localDateTime?.dayOfWeek?.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val month = localDateTime?.month?.getDisplayName(TextStyle.FULL, Locale.getDefault())
        val dayOfMonth = localDateTime?.dayOfMonth
        val year = localDateTime?.year
        val hour = localDateTime?.hour
        val minute = localDateTime?.minute
        return "$dayOfWeek, $month $dayOfMonth, $year $hour:$minute"
    }

}
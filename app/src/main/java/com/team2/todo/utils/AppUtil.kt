package com.team2.todo.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import com.team2.todo.data.entities.relations.TodoWithSubTodos
import com.team2.todo.ui.theme.PriorityHigh
import com.team2.todo.ui.theme.PriorityLow
import com.team2.todo.ui.theme.PriorityMedium

import java.time.LocalDateTime

import java.time.temporal.ChronoUnit

object AppUtil {
    fun getPriorityString(priorityIndex: Int): String {
        var priority = "Low";
        if (priorityIndex == 1) {
            priority = "Medium"
        }
        if (priorityIndex == 2) {
            priority = "High"
        }
        return priority;
    }

    fun getPriorityColor(priorityIndex: Int): Color {
        var color = PriorityLow
        if (priorityIndex == 1) {
            color = PriorityMedium
        }
        if (priorityIndex == 2) {
            color = PriorityHigh
        }
        return color
    }

    fun openMaps(lat: Double, lon: Double, context: Context) {
        val stringLat = lat.toString()
        val stringLon = lon.toString()


        val intentUri = Uri.parse("geo:$stringLat,$stringLon?q=$stringLat,$stringLon($stringLat,$stringLon)")

        val intent = Intent(Intent.ACTION_VIEW, intentUri)
        intent.setPackage("com.google.android.apps.maps")

        context.startActivity(intent)
    }

    const val OVERDUE = "OVERDUE!"
    const val DONE = "DONE"

    fun shouldShowVerified(property: TodoWithSubTodos): Boolean {
        var latitudeNotPresent = (property.todo.latitude == null || property.todo.latitude == 0.0)
        var longitudeNotPresent =
            (property.todo.longitude == null || property.todo.longitude == 0.0)
        return !(latitudeNotPresent && longitudeNotPresent)
    }

    fun getDueDateDifferentFromCurrentDate(dueDateTime: LocalDateTime): Long {
        val currentDateTime = LocalDateTime.now()
        return ChronoUnit.MILLIS.between(currentDateTime, dueDateTime);
    }

    fun isToday(dueDateTodo: LocalDateTime?): Boolean {
        val currentDateTime = LocalDateTime.now()
        return ChronoUnit.DAYS.between(currentDateTime, dueDateTodo) == 0L;
    }

}

fun checkOverdue(dateselected: LocalDateTime): Boolean {
    val currentDate = LocalDateTime.now()
    return dateselected.isBefore(currentDate)

}

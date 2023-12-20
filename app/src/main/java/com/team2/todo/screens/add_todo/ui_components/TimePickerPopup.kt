package com.team2.todo.screens.add_todo.ui_components

import android.app.TimePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

/**
 * Created by Atharva K on 11/16/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerPopup(): Pair<TimePickerDialog, MutableState<String>> {
    val currentcontext = LocalContext.current
    val calendarinstance = Calendar.getInstance()
    val hour = calendarinstance[Calendar.HOUR_OF_DAY]
    val minute = calendarinstance[Calendar.MINUTE]
    val time = rememberSaveable { mutableStateOf("") }

    val mTimePickerDialog = TimePickerDialog(
        currentcontext,
        { _, mHour: Int, mMinute: Int ->
            val formattedHour = if (mHour < 10) "0$mHour" else "$mHour"
            val formattedMinute = if (mMinute < 10) "0$mMinute" else "$mMinute"
            time.value = "$formattedHour:$formattedMinute"
        }, hour, minute, false
    )
    return Pair(mTimePickerDialog, time)
}
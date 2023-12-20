package com.team2.todo.screens.add_todo.ui_components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Date

/**
 * Created by Atharva K on 11/14/23.
 */

@Composable
fun DatePickerPopup(): Pair<DatePickerDialog, MutableState<String>> {
    val year: Int
    val month: Int
    val day: Int

    val mCalendar = Calendar.getInstance()
    val mContext = LocalContext.current

    year = mCalendar.get(Calendar.YEAR)
    month = mCalendar.get(Calendar.MONTH)
    day = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = rememberSaveable { mutableStateOf("") }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val formattedDay = if (mDayOfMonth < 10) "0$mDayOfMonth" else "$mDayOfMonth"
            mDate.value = "$formattedDay/${mMonth + 1}/$mYear"
        }, year, month, day
    )

    return Pair(mDatePickerDialog, mDate)

}
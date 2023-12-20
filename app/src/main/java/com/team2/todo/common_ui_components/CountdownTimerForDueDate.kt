package com.team2.todo.common_ui_components
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import com.team2.todo.utils.AppUtil
import kotlinx.coroutines.delay
import java.time.LocalDateTime

@Composable
fun CountdownTimerForDueDate(
    dueDateTime: LocalDateTime,
    returnOnlyTime:Boolean = false
): String {

    val timeDifferenceInMillis = AppUtil.getDueDateDifferentFromCurrentDate(dueDateTime)
    val remainingTime =
        remember { mutableLongStateOf(timeDifferenceInMillis) }

    LaunchedEffect(remainingTime) {
        while (remainingTime.value > 0) {
            delay(1000L)
            remainingTime.value -= 1000L
        }
    }
    if(remainingTime.value <= 0)
    {
        return AppUtil.OVERDUE
    }
    val daysRemaining = remainingTime.value / (1000 * 60 * 60 * 24)
    val hoursRemaining = (remainingTime.value % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
    val minutesRemaining = (remainingTime.value % (1000 * 60 * 60)) / (1000 * 60)
    val secondsRemaining = (remainingTime.value % (1000 * 60)) / 1000
    if(returnOnlyTime){
        return "$hoursRemaining h : $minutesRemaining m : $secondsRemaining s";
    }
    return "$daysRemaining d : $hoursRemaining h : $minutesRemaining m : $secondsRemaining s"
}
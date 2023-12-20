package com.team2.todo.screens.add_todo.ui_components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by Atharva K on 11/16/23.
 */
@Composable
fun ReminderField(dateSelected: LocalDateTime) {

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val formattedDate = dateSelected.format(formatter)

    Text(
        text = "We will remind you on: $formattedDate",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        fontSize = 15.sp
    )
}
package com.team2.todo.screens.details_page.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddSubTodoFloatingButtons(onCreateNewClick: () -> Unit, onPickFromPreDefinedClick: () -> Unit) {
    var expand by remember {
        mutableStateOf(false)
    }
    return Column(horizontalAlignment = Alignment.End) {
        if (expand) ExtendedFloatingActionButton(
            modifier = Modifier.padding(5.dp),
            onClick = { onCreateNewClick() },
            icon = { Icon(Icons.Filled.AddCircle, "Extended floating action button.") },
            text = { Text(text = "Create New Task") },
        )
        if (expand) ExtendedFloatingActionButton(
            modifier = Modifier.padding(5.dp),
            onClick = { onPickFromPreDefinedClick() },
            icon = { Icon(Icons.Filled.List, "Extended floating action button.") },
            text = { Text(text = "Pick Pre Defined") },
        )
        FloatingActionButton(
            onClick = { expand = !expand },
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Icon(if (expand) Icons.Filled.Close else Icons.Filled.Add, contentDescription = "Add")
        }
    }
}
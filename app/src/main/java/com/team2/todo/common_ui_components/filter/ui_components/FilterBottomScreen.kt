package com.team2.todo.common_ui_components.filter.ui_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.common_ui_components.filter.ui_components.FilterChipGroup
import com.team2.todo.common_ui_components.filter.view_model.FilterViewModel
import com.team2.todo.data.entities.relations.TodoWithSubTodos
import com.team2.todo.screens.listing.view_model.PropertyListViewModel

/*
* Created by Vivek Tate on 18/11/2023
* */

@Composable
fun FilterScreenCompose(viewModel: FilterViewModel, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 25.dp, start = 30.dp, end = 30.dp)
    ) {
        Text(
            text = "Sort your tasks based on",
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        FilterChipGroup(viewModel) { onClick() }
    }
}
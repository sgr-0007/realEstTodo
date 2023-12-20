package com.team2.todo.common_ui_components.filter.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team2.todo.common_ui_components.filter.view_model.FilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipGroup(viewModel: FilterViewModel, onClick: () -> Unit) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(viewModel.getAllFilters()) { filter ->
            FilterChip(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .height(50.dp),

                selected = (filter == viewModel.selectedFilter.value),

                onClick = {
                    viewModel.setSelectedFilter(filter)
                    onClick()
                          },

                label = {
                    Text(text = filter.value)
                }
            )
        }
    }
}
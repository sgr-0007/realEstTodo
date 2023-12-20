package com.team2.todo.screens.listing.ui_components.in_sale

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team2.todo.R
import com.team2.todo.common_ui_components.EmptyList
import com.team2.todo.common_ui_components.filter.ui_components.FilterScreenCompose
import com.team2.todo.common_ui_components.filter.view_model.FilterViewModel
import com.team2.todo.screens.listing.view_model.PropertyListViewModel
import com.team2.todo.data.entities.SubTodo
import com.team2.todo.data.entities.Todo
import com.team2.todo.screens.listing.ui_components.CustomListItem
import java.time.LocalDateTime
import com.team2.todo.data.entities.relations.TodoWithSubTodos as TodoWithSubTodos1

/**
 * Created by Manu KJ on 11/6/23.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InSaleList(viewModel: PropertyListViewModel) {
    val list by remember { viewModel.inSalePropertyList }.collectAsState()

    if (list.isNullOrEmpty()) {
        EmptyList(title = "No Active Sales Found", drawableID = R.drawable.ic_no_in_sale_list)
    } else {
        Scaffold {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(top = 10.dp)
            ) {
                items(list.size) { index ->
                    val todo = list[index]
                    CustomListItem(
                        property = todo,
                        onClearTaskClicked = {
                            viewModel.updateStatus(todo.todo.todoId, true)
                        },
                    )
                }
            }
        }
    }
}
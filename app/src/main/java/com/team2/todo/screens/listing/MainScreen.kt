package com.team2.todo.screens.listing

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.team2.todo.common_ui_components.ReminderAlertCompose
import com.team2.todo.common_ui_components.filter.ui_components.FilterScreenCompose
import com.team2.todo.common_ui_components.filter.view_model.FilterViewModel
import com.team2.todo.data.RealEstateDatabase
import com.team2.todo.data.repo.TodoRepo
import com.team2.todo.screens.listing.ui_components.BottomNavigationCompose
import com.team2.todo.screens.listing.ui_components.completed_sale.CompletedSaleList
import com.team2.todo.screens.listing.ui_components.in_sale.InSaleList
import com.team2.todo.screens.listing.view_model.ListingViewModel
import com.team2.todo.utils.LocationUtil
import com.team2.todo.utils.NavigationUtil
import com.team2.todo.utils.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MainScreen() {
    var currentPage by remember { mutableIntStateOf(0) }
    var showFilter by remember { mutableStateOf(false) }

    var filterViewModel = FilterViewModel(LocalContext.current)
    val database = RealEstateDatabase.getInstance(context = LocalContext.current)
    val repo = TodoRepo(database)
    var ctx = LocalContext.current.applicationContext
    val viewModel = ListingViewModel.getInstance(repo = repo, filterViewModel, ctx)

    if (LocationUtil.valid()) {
        viewModel.fetchNearestTask()
    }

    MaterialTheme(typography = Typography()) {
        Scaffold(
            floatingActionButton = {
                Row {
                    FloatingActionButton(
                        modifier = Modifier.padding(end = 10.dp),
                        onClick = {
                            showFilter = true
                        }
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Add")
                    }

                    if (currentPage == 0) FloatingActionButton(
                        onClick = { NavigationUtil.navigateTo(Screen.AddTodos) },

                        ) {
                        Icon(Icons.Filled.AddCircle, "Extended floating action button.")
                    }
                }

            },
            bottomBar = {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp,
                    ),
                    modifier = Modifier.border(
                        0.1.dp, color = Color.Gray, shape = RoundedCornerShape(0.dp)
                    )
                ) {
                    BottomNavigationCompose(
                        currentPage = currentPage,
                        onClick = { nextPage -> currentPage = nextPage },
                    )
                }
            }
        ) { it ->

            if (showFilter) {
                ModalBottomSheet(onDismissRequest = { showFilter = false; }) {
                    FilterScreenCompose(filterViewModel) {
                        viewModel.getDataForSelectedFilter(filterViewModel.selectedFilter.value)
                    }
                }
            }

            if (viewModel.reminderModel != null) {
                ModalBottomSheet(onDismissRequest = { viewModel.reminderModel = null }) {
                    ReminderAlertCompose(viewModel.reminderModel!!)
                }
            }
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                if (currentPage == 0) {
                    InSaleList(viewModel)
                } else {
                    CompletedSaleList(viewModel)
                }
            }
        }
    }
}
package com.team2.todo.screens.subtodo_details

import SubTodoDetailsComponent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.team2.todo.data.RealEstateDatabase
import com.team2.todo.data.repo.SubTodoRepo
import com.team2.todo.screens.subtodo_details.view_model.SubTodoDetailsViewModel

@Composable
fun SubTodoDetails(subTodoId: Long) {

    val subTodoDetailsContext = LocalContext.current
    val database = RealEstateDatabase.getInstance(subTodoDetailsContext)
    val repo = SubTodoRepo(database)
    val viewModel = SubTodoDetailsViewModel(repo)
    SubTodoDetailsComponent(viewModel,subTodoId)

}

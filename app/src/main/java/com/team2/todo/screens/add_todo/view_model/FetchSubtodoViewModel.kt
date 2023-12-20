package com.team2.todo.screens.add_todo.view_model

import androidx.lifecycle.ViewModel
import com.team2.todo.data.entities.SubTodo
import com.team2.todo.data.repo.SubTodoRepo
import kotlinx.coroutines.flow.Flow

/**
 * Created by Atharva K on 11/14/23.
 */
class FetchSubtodoViewModel(val repository: SubTodoRepo) : ViewModel() {
    fun fetchSubtodo(subTodoId: Long): Flow<SubTodo> {
        return repository.getSubTodosBasedOnSubTodoId(subTodoId)
    }
}
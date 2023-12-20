package com.team2.todo.screens.add_todo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2.todo.data.entities.SubTodo
import com.team2.todo.data.repo.SubTodoRepo
import kotlinx.coroutines.launch

/**
 * Created by Atharva K on 11/14/23.
 */

class AddSubTodoViewModel(val repository: SubTodoRepo): ViewModel() {
    fun addSubTodo(subtodo:SubTodo){
        viewModelScope.launch {
            repository.upsertSubTodo(subtodo)
        }
    }
}
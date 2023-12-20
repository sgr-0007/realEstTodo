package com.team2.todo.screens.add_todo.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2.todo.data.entities.Images
import com.team2.todo.data.entities.Todo
import com.team2.todo.data.repo.TodoRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Atharva K on 11/14/23.
 */
class AddTodoViewModel(val repository:TodoRepo):ViewModel() {

    suspend fun addTodo(todo:Todo):Long{
          delay(2000)
          return repository.upsertTodo(todo)
    }

    fun addImage(image:Images){
        viewModelScope.launch {
            repository.addImage(image)
        }
    }
}
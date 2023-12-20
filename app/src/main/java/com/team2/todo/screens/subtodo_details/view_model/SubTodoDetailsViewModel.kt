package com.team2.todo.screens.subtodo_details.view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team2.todo.data.entities.SubTodo
import com.team2.todo.data.repo.SubTodoRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class SubTodoDetailsViewModel(private val repo: SubTodoRepo) : ViewModel() {

    private val _subTodo: MutableStateFlow<SubTodo?> = MutableStateFlow(null)

    val subTodo: MutableStateFlow<SubTodo?> = _subTodo

    fun getSubTodoById(subTodoId: Long) {
        try {
            viewModelScope.launch {
                repo.getSubTodosBasedOnSubTodoId(subTodoId).collect { subTodoObj ->
                    _subTodo.value = subTodoObj
                }
            }
        } catch (e: Exception) {
            Log.d("errorsub", "getSubTodoById: ${e.localizedMessage}")
        }


    }


}

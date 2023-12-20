package com.team2.todo.data.repo

import com.team2.todo.data.RealEstateDatabase
import com.team2.todo.data.entities.Images
import com.team2.todo.data.entities.Todo
import com.team2.todo.data.entities.relations.TodoWithSubTodos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TodoRepo(private val database: RealEstateDatabase) {

    suspend fun upsertTodo(todoEntity: Todo): Long {
        return database.todoDao().upsertTodo(todoEntity)
    }

    suspend fun addImage(imageEntity: Images) {
        database.todoDao().insertImage(imageEntity)
    }


    fun getTodoWithSubTodosBasedOnTodoId(todoId: Long): Flow<List<TodoWithSubTodos>> =
        database.todoDao().getTodoWithSubTodosBasedOnTodoId(todoId)


    fun getAllTodosWithSubTodos(status: Boolean): Flow<List<TodoWithSubTodos>> =
        database.todoDao().getAllTodosWithSubTodos(status = status)


    fun getAllTodosOrderedByPriorityASCWithSubTodos(status: Boolean): Flow<List<TodoWithSubTodos>> =
        database.todoDao().getAllTodosOrderedByPriorityASCWithSubTodos(status = status)


    fun getAllTodosOrderedByPriorityDESCWithSubTodos(status: Boolean): Flow<List<TodoWithSubTodos>> =
        database.todoDao().getAllTodosOrderedByPriorityDESCWithSubTodos(status = status)


    suspend fun updateTodoStatus(todoId: Long, status: Boolean) {
        database.todoDao().updateTodoStatus(todoId, status)
    }

    fun getAllTodoImagesBasedOnTodo(todoId: Long): Flow<List<Images>> =
        database.todoDao().getAllTodoImagesBasedOnTodo(todoId)

    fun getAllTodosOrderedByPriceASCWithSubTodos(status: Boolean): Flow<List<TodoWithSubTodos>> =
        database.todoDao().getAllTodosOrderedByPriceASCWithSubTodos(status = status)

    fun getAllTodosOrderedByPriceDESCWithSubTodos(status: Boolean): Flow<List<TodoWithSubTodos>> =
        database.todoDao().getAllTodosOrderedByPriceDESCWithSubTodos(status = status)

    fun getAllTodosOrderedByDueDateDESCWithSubTodos(status: Boolean): Flow<List<TodoWithSubTodos>> =
        database.todoDao().getAllTodosOrderedByDueDateDESCWithSubTodos(status = status)

    suspend fun deleteProperty(todoId: Long) {
        withContext(Dispatchers.IO) {
            database.todoDao().deleteProperty(todoId)
        }
    }
    suspend fun deleteTodoImageBasedOnImageId(imageId: Long) {
        withContext(Dispatchers.IO) {
            database.todoDao().deleteTodoImageBasedOnImageId(imageId)
        }
    }



}
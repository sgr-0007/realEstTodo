package com.team2.todo.data.repo

import com.team2.todo.data.RealEstateDatabase
import com.team2.todo.data.entities.SubTodo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class SubTodoRepo(private val database: RealEstateDatabase) {

    suspend fun upsertSubTodo(subTodoEntity: SubTodo) : Long {
        return database.subTodoDao().upsertSubTodo(subTodoEntity)
    }

    suspend fun deleteProperty(subTodoId: Long) {
        withContext(Dispatchers.IO) {
            database.subTodoDao().deleteProperty(subTodoId)
        }
    }

    fun getSubTodosBasedOnTodo(todoId: Long): Flow<List<SubTodo>> =
        database.subTodoDao().getSubTodosBasedOnTodo(todoId)

    fun getSubTodosBasedOnSubTodoId(subTodoId: Long): Flow<SubTodo> =
        database.subTodoDao().getSubTodosBasedOnSubTodoId(subTodoId)

    suspend fun updateSubTodoStatus(subTodoId: Long, status: Boolean) {
        database.subTodoDao().updateSubTodoStatus(subTodoId, status)
    }

}
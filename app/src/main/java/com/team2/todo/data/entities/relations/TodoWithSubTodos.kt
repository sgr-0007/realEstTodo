package com.team2.todo.data.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.team2.todo.data.entities.Images
import com.team2.todo.data.entities.SubTodo
import com.team2.todo.data.entities.Todo

data class TodoWithSubTodos(
    @Embedded val todo: Todo,
    @Relation(
        parentColumn = "todoId",
        entityColumn = "todoId"
    )
    val subtodos: List<SubTodo>,

    @Relation(
        parentColumn = "todoId",
        entityColumn = "todoId"
    )
    val images: List<Images>
)

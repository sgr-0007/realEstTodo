package com.team2.todo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity("todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val todoId: Long,
    val title: String,
    val label: String?,
    val description: String,
    val latitude: Double?,
    val longitude: Double?,
    val price: Double,
    val createdDate: LocalDateTime?,
    val dueDate: LocalDateTime?,
    var status: Boolean,
    val priority: Int?
)



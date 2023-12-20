package com.team2.todo.data.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.team2.todo.data.datautils.ImageDataConverter
import java.time.LocalDateTime

@Entity(
    "subtodos",
    foreignKeys = [
        ForeignKey(
            entity = Todo::class,
            parentColumns = ["todoId"],
            childColumns = ["todoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)


data class SubTodo(
    @PrimaryKey(autoGenerate = true)
    val subTodoId: Long,
    val todoId: Long,
    val title: String?,
    val description: String?,
    @TypeConverters(ImageDataConverter::class)
    val image: Bitmap?,
    /* added type converter for complex date object */
    val createdDate: LocalDateTime?,
    val dueDate: LocalDateTime?,
    val status: Boolean?,
    val priority: Int?,
    val isCompleted: Boolean = false

)





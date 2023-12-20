package com.team2.todo.data.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("images",
    foreignKeys = [
        ForeignKey(
            entity = Todo::class,
            parentColumns = ["todoId"],
            childColumns = ["todoId"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class Images(
    @PrimaryKey(autoGenerate = true)
    val imageId: Long,
    val image: Bitmap?,
    val todoId: Long?
)

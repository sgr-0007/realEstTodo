package com.team2.todo.screens.pre_defined_sub_task.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.team2.todo.R
import com.team2.todo.data.entities.SubTodo
import java.time.LocalDateTime

/**
 * Created by Manu KJ on 12/4/23.
 */

object TaskList {


    private fun createSubTodo(
        todoId: Long,
        title: String,
        description: String,
        priority: Int,
    ): SubTodo {

        return SubTodo(
            subTodoId = 0,
            todoId = todoId,
            title = title,
            description = description,
            image = null,
            createdDate = LocalDateTime.now(),
            dueDate = LocalDateTime.now().plusDays(1),
            status = false,
            priority = priority
        )
    }

    fun getSubTodoList(todoId: Long, ctx: Context): List<SubTodo> {
        val todoList = mutableListOf<SubTodo>()
        val placeHolder =
            BitmapFactory.decodeResource(ctx.resources, R.drawable.ic_task_place_holder)

        todoList.add(
            createSubTodo(
                todoId,
                "Marketing Strategy",
                "Develop a comprehensive marketing plan utilizing online listings, social media, and other channels for effective property promotion.",
                1,

                )
        )

        todoList.add(
            createSubTodo(
                todoId,
                "Showings and Open Houses",
                "Coordinate and host multiple showings, including open house events to engage potential buyers and showcase property features.",
                1,

                )
        )


        todoList.add(
            createSubTodo(
                todoId,
                "Negotiation",
                "Skillfully present and negotiate offers between the seller and potential buyers to reach a mutually beneficial agreement.",
                0,

                )
        )

        todoList.add(
            createSubTodo(
                todoId,
                "Paperwork and Documentation",
                "Handle the necessary paperwork, contracts, disclosures, and legal documents involved in the property sale transaction.",
                1,

                )
        )

        todoList.add(
            createSubTodo(
                todoId,
                "Inspections and Appraisals",
                "Coordinate inspections and appraisals essential for satisfying buyer requirements and ensuring property value.",
                1,

                )
        )

        todoList.add(
            createSubTodo(
                todoId,
                "Closing Preparations",
                "Facilitate the closing process, ensuring all parties fulfill their obligations and complete required paperwork accurately.",
                0,

                )
        )

        todoList.add(
            createSubTodo(
                todoId,
                "Post-Sale Follow-Up",
                "Provide necessary support post-sale to ensure a smooth transition for the buyer and seller, addressing any potential issues.",
                1,

                )
        )

        return todoList
    }

}
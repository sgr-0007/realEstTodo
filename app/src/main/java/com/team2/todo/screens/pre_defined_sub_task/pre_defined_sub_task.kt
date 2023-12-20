package com.team2.todo.screens.pre_defined_sub_task

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.common_ui_components.CommonAppBar
import com.team2.todo.common_ui_components.LoaderBottomSheet
import com.team2.todo.data.RealEstateDatabase
import com.team2.todo.data.entities.SubTodo
import com.team2.todo.data.repo.SubTodoRepo
import com.team2.todo.screens.add_todo.view_model.AddSubTodoViewModel
import com.team2.todo.screens.pre_defined_sub_task.util.TaskList
import com.team2.todo.utils.AppUtil
import com.team2.todo.utils.NavigationUtil
import java.util.Timer
import kotlin.concurrent.schedule

@Composable
fun PreDefinedSubTask(todoId: Long = 0) {
    var ctx = LocalContext.current
    var db = RealEstateDatabase.getInstance(ctx)
    val subtodorepo = SubTodoRepo(db)
    val subtodviewmodel = AddSubTodoViewModel(subtodorepo)

    val list = TaskList.getSubTodoList(todoId = todoId, ctx)
    var isLoading by remember { mutableStateOf(false) }


    return Scaffold(
        topBar = {
            CommonAppBar(
                text = "Predefined Tasks"
            )
        },
    ) {
        if (isLoading)
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoaderBottomSheet("Adding Task into the Property Please wait")
            }
        else
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .padding(vertical = 10.dp, horizontal = 15.dp)
            ) {
                items(list.size) { index ->
                    val task = list[index]
                    TaskItem(
                        task,
                        onClick = {
                            isLoading = true
                            subtodviewmodel.addSubTodo(
                                task
                            )
                            val handler = Handler(Looper.getMainLooper())
                            handler.postDelayed({
                                NavigationUtil.goBack();
                            }, 1000)
                        },
                    )
                }
            }
    }
}

@Composable
fun TaskItem(task: SubTodo, onClick: () -> Unit) {
    return Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(
                horizontal = 10.dp,
                vertical = 10.dp
            )
            .border(
                0.3.dp,
                color = AppUtil.getPriorityColor(task.priority ?: -1),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 10.dp
            )
        ) {
            Text(text = task.title ?: "", fontWeight = FontWeight.Bold, fontSize = 23.sp)
            Text(
                text = task.description ?: "",
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}
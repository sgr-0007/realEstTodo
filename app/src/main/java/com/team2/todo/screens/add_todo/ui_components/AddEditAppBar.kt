package com.team2.todo.screens.add_todo.ui_components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.team2.todo.ui.theme.AppBarBackGroundColor
import com.team2.todo.ui.theme.AppBarContentColor
import com.team2.todo.ui.theme.PrimaryColor
import com.team2.todo.ui.theme.Typography
import com.team2.todo.utils.NavigationUtil
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

/**
 * Created by Manu KJ on 11/14/23.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddEditAppBar(isSubTodo:Boolean=false,isEdit:Boolean=false,isEditSubTodo:Boolean=false) {
    return TopAppBar(
        title = {
            Text(
                text = if(isSubTodo && isEditSubTodo) "Edit SubTask" else if(isSubTodo && !isEditSubTodo) "Add SubTask" else if(isEdit) "Edit Task" else "Add New Property",
                color = AppBarContentColor,
                fontWeight = FontWeight.Bold)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = AppBarBackGroundColor),
        navigationIcon = {
            IconButton(
                onClick = {
                    NavigationUtil.goBack();
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = AppBarContentColor,
                    modifier = Modifier.size(35.dp)
                )
            }
        },
    );
}
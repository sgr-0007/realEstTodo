package com.team2.todo.screens.listing.ui_components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.team2.todo.R
import com.team2.todo.ui.theme.AppBarBackGroundColor
import com.team2.todo.ui.theme.AppBarContentColor
import com.team2.todo.ui.theme.PrimaryColor
import com.team2.todo.utils.NavigationUtil
import com.team2.todo.utils.Screen

/**
 * Created by Manu KJ on 11/9/23.
 */

@Composable
fun BottomNavigationCompose(currentPage: Int, onClick: (nextPage: Int) -> Unit) {
    return NavigationBar(
        contentColor = AppBarContentColor,
        containerColor = AppBarBackGroundColor,
        tonalElevation = 12.dp
    ) {
        NavigationBarItem(
            selected = currentPage == 0,
            onClick = { onClick(0) },
            label = { Text(text = "In Sale", color = AppBarContentColor) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_todo_list_icon),
                    contentDescription = "TODO",
                    modifier = Modifier.size(27.dp)
                )
            },
        )
        NavigationBarItem(
            selected = currentPage == 1,
            onClick = {
                onClick(1)
            },
            label = { Text(text = "Complete ", color = AppBarContentColor) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_completed_list_icon),
                    contentDescription = "TODO",
                    modifier = Modifier.size(27.dp)
                )
            },
        )
    };

}
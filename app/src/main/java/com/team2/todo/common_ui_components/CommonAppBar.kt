package com.team2.todo.common_ui_components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.ui.theme.AppBarBackGroundColor
import com.team2.todo.ui.theme.AppBarContentColor
import com.team2.todo.utils.NavigationUtil

/**
 * Created by Manu KJ on 11/21/23.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonAppBar(text: String = "Dummy App Bar", actions: @Composable RowScope.() -> Unit = {}) {
    return TopAppBar(
        actions = actions,
        modifier = Modifier.padding(end = 15.dp),
        title = {
            Text(
                text = text,
                color = AppBarContentColor,
                fontSize = 23.sp,
                fontWeight = FontWeight.SemiBold,
            )
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
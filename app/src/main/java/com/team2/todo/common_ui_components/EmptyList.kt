package com.team2.todo.common_ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.team2.todo.R
import com.team2.todo.screens.details_page.DetailsPage
import com.team2.todo.ui.theme.PrimaryColor
import com.team2.todo.utils.NavigationUtil
import com.team2.todo.utils.Screen

/**
 * Created by Manu KJ on 11/6/23.
 */
@Preview
@Composable
fun EmptyList(
    title: String = "No Current Sales",
    drawableID: Int = R.drawable.ic_no_completed_list
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = drawableID),
            contentDescription = title,
        )
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryColor,
            textAlign = TextAlign.Center
        )
    }

}
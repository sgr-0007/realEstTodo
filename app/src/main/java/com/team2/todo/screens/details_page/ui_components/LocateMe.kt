package com.team2.todo.screens.details_page.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.R

@Composable
@Preview
fun LocateMe() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(
                1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(20),

                )
            .padding(5.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_locate_property),
            contentDescription = null,
            Modifier.height(100.dp)
        )
        Text(
            text = "View Property Location",
            Modifier.weight(1f),
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

    }

}
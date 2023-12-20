package com.team2.todo.common_ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.R
import com.team2.todo.ui.theme.PrimaryColor

/**
 * Created by Manu KJ on 11/21/23.
 */

@Preview
@Composable
fun LoaderBottomSheet(text: String = "Crafting Your Property... Just a Moment, We're Almost Done!") {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp, top = 15.dp),
    ) {
        Text(
            text = text, fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = PrimaryColor,
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(id = R.drawable.ic_loading),
            contentDescription = "loading",
            modifier = Modifier.height(150.dp)
        )
        LinearProgressIndicator(
            modifier = Modifier

                .padding(horizontal = 2.dp, vertical = 1.dp)
        )
    }
}
package com.team2.todo.common_ui_components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.ui.theme.BlueColor

/**
 * Created by Manu KJ on 12/1/23.
 */


@Composable
fun LocationVerifiedLogo() {
    Row(
        modifier = Modifier
            .padding(start = 10.dp)
            .border(1.dp, color = BlueColor, shape = RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.padding(2.dp))
        Icon(
            imageVector = Icons.Filled.CheckCircle,
            contentDescription = "Image",
            Modifier
                .size(15.dp)
                .padding(end = 5.dp),
            tint = BlueColor
        )
        Text(
            text = "Verified ",
            color = BlueColor,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        )
        Spacer(modifier = Modifier.padding(2.dp))

    }
}
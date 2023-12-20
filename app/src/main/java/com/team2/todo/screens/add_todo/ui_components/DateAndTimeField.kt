package com.team2.todo.screens.add_todo.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.R
import com.team2.todo.ui.theme.PrimaryColor

/**
 * Created by Manu KJ on 11/14/23.
 */


@Composable
fun DateAndTimeField(
    date: String,
    time: String ,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,
) {

    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Text(
            text = "Pick A Due Date",
            modifier = Modifier.padding(bottom = 10.dp),
            fontWeight = FontWeight.SemiBold,
            color = PrimaryColor,
            fontSize = 18.sp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .border(1.dp, color = PrimaryColor, shape = RoundedCornerShape(8.dp))
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Image
                Image(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(64.dp)
                )

                // Spacer for separation
                Spacer(modifier = Modifier.width(16.dp))

                // Nested Column
                Column {
                    // Date
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, color = PrimaryColor, shape = RoundedCornerShape(8.dp))
                            .clickable { onDateClick() },
                    ) {
                        Text(
                            text = if (date.isNullOrEmpty()) "--/--/--" else date,
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                    //Time
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, color = PrimaryColor, shape = RoundedCornerShape(8.dp))
                            .clickable { onTimeClick() },
                    ) {

                        Text(
                            text = if (time.isNullOrEmpty()) "--:--" else time,
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                }
            }
        }
    }
}




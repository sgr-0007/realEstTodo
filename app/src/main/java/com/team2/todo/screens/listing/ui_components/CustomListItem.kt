package com.team2.todo.screens.listing.ui_components

import android.graphics.ImageDecoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.team2.todo.common_ui_components.LocationVerifiedLogo
import com.team2.todo.common_ui_components.OverDueLogo
import com.team2.todo.data.entities.relations.TodoWithSubTodos
import com.team2.todo.ui.theme.BlueColor
import com.team2.todo.ui.theme.DarkGreenColor
import com.team2.todo.ui.theme.DarkGreyColor
import com.team2.todo.ui.theme.DarkRedColor
import com.team2.todo.ui.theme.GreyColor
import com.team2.todo.ui.theme.PrimaryColor
import com.team2.todo.ui.theme.PriorityHigh
import com.team2.todo.ui.theme.PriorityLow
import com.team2.todo.ui.theme.PriorityMedium
import com.team2.todo.utils.AppUtil
import com.team2.todo.utils.AppUtil.getDueDateDifferentFromCurrentDate
import com.team2.todo.utils.AppUtil.getPriorityColor
import com.team2.todo.utils.NavigationUtil
import com.team2.todo.utils.Screen
import kotlinx.coroutines.launch


@Composable
fun CustomListItem(
    property: TodoWithSubTodos,
    onClearTaskClicked: () -> Unit,
    onPermanentDelete: () -> Unit = {}
) {

    var priority = AppUtil.getPriorityString(priorityIndex = property.todo.priority ?: -1)


    fun shouldShowVerified(): Boolean {
        var latitudeNotPresent = (property.todo.latitude == null || property.todo.latitude == 0.0)
        var longitudeNotPresent =
            (property.todo.longitude == null || property.todo.longitude == 0.0)
        return !(latitudeNotPresent && longitudeNotPresent)
    }

    val title = property.todo.title
    val isCompleted = property.todo.status

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
            )
            .border(
                0.3.dp,
                color = getPriorityColor(property.todo.priority ?: -1),
                shape = RoundedCornerShape(10.dp)
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .width(10.dp)
                    .height(90.dp)
                    .background(getPriorityColor(property.todo.priority ?: -1))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(1f)
                    .alpha(if (isCompleted) 0.5f else 1.0f)
                    .clickable {
                        NavigationUtil.navigateTo("${Screen.DetailsScreen.name}/${property.todo.todoId}")
                    }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Image",
                        Modifier
                            .size(15.dp)
                            .padding(end = 5.dp),
                        tint = getPriorityColor(property.todo.priority ?: -1)
                    )
                    Text(
                        text = priority,
                        color = getPriorityColor(property.todo.priority ?: -1),
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp,
                        textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )
                    if (shouldShowVerified()) LocationVerifiedLogo()
                    property.todo.dueDate?.let {
                        if(getDueDateDifferentFromCurrentDate(it) <=0 && !property.todo.status){
                            OverDueLogo()
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = title,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = if (isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )

                }

            }



            Box(
                modifier = Modifier.padding(end = 15.dp)
            ) {
                Icon(
                    if (isCompleted) Icons.Filled.Refresh else Icons.Filled.Done,
                    "Extended floating action button.",
                    tint = GreyColor,
                    modifier = Modifier
                        .border(
                            2.dp,
                            GreyColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(4.dp)
                        .clickable {
                            onClearTaskClicked()

                        }
                )
            }

            if (isCompleted)
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "sdds",
                    modifier = Modifier
                        .clickable {
                            onPermanentDelete()
                        }
                        .padding(end = 10.dp),
                )
        }
    }
}


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.sharp.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.R
import com.team2.todo.common_ui_components.CountdownTimerForDueDate
import com.team2.todo.common_ui_components.EmptyList
import com.team2.todo.data.datautils.LocalDatetimeToWords
import com.team2.todo.screens.subtodo_details.ui_components.DisplaySubTodoImage
import com.team2.todo.screens.subtodo_details.view_model.SubTodoDetailsViewModel
import com.team2.todo.ui.theme.AppBarContentColor
import com.team2.todo.ui.theme.GreyColor
import com.team2.todo.utils.AppUtil
import com.team2.todo.utils.NavigationUtil
import com.team2.todo.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubTodoDetailsComponent(viewModel: SubTodoDetailsViewModel, subTodoId: Long) {

    viewModel.getSubTodoById(subTodoId)
    val propertySubTaskState by remember { viewModel.subTodo }.collectAsState()

    if (propertySubTaskState == null) {
        EmptyList(title = "No Active Sales Found", drawableID = R.drawable.ic_no_in_sale_list)

    } else {

        var timerState =
            propertySubTaskState!!.dueDate?.let { CountdownTimerForDueDate(dueDateTime = it) }


        Scaffold(topBar = {
            propertySubTaskState?.title?.let {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(it)
                        }
                    },
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
                    actions = {

                        Icon(
                            Icons.Filled.Edit,
                            "Extended floating action button.",
                            tint = GreyColor,
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    GreyColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                                .clickable {
                                    NavigationUtil.navigateTo("${Screen.EditSubTodo}/${subTodoId}")
                                }
                        )
                    })

            }
        }) { padding ->


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .width(700.dp)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))

                        DisplaySubTodoImage(propertySubTaskState?.image)

                        Divider(
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp), thickness = 3.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Spacer(modifier = Modifier.width(1.dp))
                            propertySubTaskState?.priority?.let {
                                Icon(
                                    imageVector = Icons.Sharp.Warning,
                                    contentDescription = null,
                                    Modifier
                                        .padding(
                                            start = 5.dp,
                                            top = 5.dp,
                                            end = 5.dp,
                                            bottom = 5.dp
                                        )
                                        .size(20.dp),
                                    tint = AppUtil.getPriorityColor(it)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = AppUtil.getPriorityString(it),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = AppUtil.getPriorityColor(it),
                                    textAlign = TextAlign.Start
                                )
                            }
                        }
                        Divider(
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp), thickness = 3.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = null,
                                Modifier
                                    .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
                                    .size(20.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = LocalDatetimeToWords.formatLocalDateTimeAsWords(
                                    propertySubTaskState?.dueDate
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                textAlign = TextAlign.Start
                            )
                        }
                        Divider(
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp), thickness = 3.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if(propertySubTaskState?.status != true)
                            {
                                if (timerState != null) {
                                    if (timerState != AppUtil.OVERDUE) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.duration),
                                            contentDescription = null,
                                            Modifier
                                                .padding(
                                                    start = 5.dp,
                                                    top = 5.dp,
                                                    end = 5.dp,
                                                    bottom = 5.dp
                                                )
                                                .size(20.dp),
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = timerState,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            textAlign = TextAlign.Start
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(id = R.drawable.duration),
                                            contentDescription = null,
                                            Modifier
                                                .padding(
                                                    start = 5.dp,
                                                    top = 5.dp,
                                                    end = 5.dp,
                                                    bottom = 5.dp
                                                )
                                                .size(20.dp),
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = AppUtil.OVERDUE,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.error,
                                            textAlign = TextAlign.Start,
                                            fontStyle = FontStyle.Italic

                                        )
                                    }

                                }
                            }
                            else{

                                Icon(
                                    painter = painterResource(id = R.drawable.duration),
                                    contentDescription = null,
                                    Modifier
                                        .padding(
                                            start = 5.dp,
                                            top = 5.dp,
                                            end = 5.dp,
                                            bottom = 5.dp
                                        )
                                        .size(20.dp),
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = AppUtil.DONE,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    textAlign = TextAlign.Start,
                                    fontStyle = FontStyle.Italic

                                )
                            }


                        }
                        Divider(
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp), thickness = 3.dp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Spacer(modifier = Modifier.width(1.dp))

                            Icon(
                                imageVector = Icons.Filled.List,
                                contentDescription = null,
                                Modifier.padding(end = 5.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            propertySubTaskState?.description?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyLarge,
                                    maxLines = 10,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}


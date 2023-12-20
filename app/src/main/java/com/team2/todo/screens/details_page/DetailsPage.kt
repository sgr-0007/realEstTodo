@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.team2.todo.screens.details_page

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team2.todo.R
import com.team2.todo.common_ui_components.ImageLoader
import com.team2.todo.common_ui_components.CommonAppBar
import com.team2.todo.common_ui_components.CountdownTimerForDueDate
import com.team2.todo.common_ui_components.EmptyList
import com.team2.todo.common_ui_components.LoaderBottomSheet
import com.team2.todo.common_ui_components.LocationVerifiedLogo
import com.team2.todo.data.RealEstateDatabase
import com.team2.todo.data.datautils.LocalDatetimeToWords
import com.team2.todo.data.entities.relations.TodoWithSubTodos
import com.team2.todo.data.repo.SubTodoRepo
import com.team2.todo.data.repo.TodoRepo
import com.team2.todo.screens.details_page.ui_components.AddSubTodoFloatingButtons
import com.team2.todo.screens.details_page.ui_components.LocateMe
import com.team2.todo.screens.details_page.ui_components.SubTaskListItem
import com.team2.todo.screens.details_page.view_model.DetailsPageViewModel
import com.team2.todo.screens.listing.ui_components.CustomListItem
import com.team2.todo.ui.theme.AppBarContentColor
import com.team2.todo.ui.theme.BlueColor
import com.team2.todo.ui.theme.GreyColor
import com.team2.todo.ui.theme.PrimaryColor
import com.team2.todo.utils.AppUtil
import com.team2.todo.utils.NavigationUtil
import com.team2.todo.utils.Screen


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailsPage(todoId: Long) {


    val todoContext = LocalContext.current
    val database = RealEstateDatabase.getInstance(todoContext)
    val todoRepo = TodoRepo(database)
    val subTodoRepo = SubTodoRepo(database)
    val viewModel = DetailsPageViewModel(todoRepo, subTodoRepo)

    var subTodoId by remember { mutableIntStateOf(0) }


    val collectedTodo by viewModel.getPropertyFromId(todoId).collectAsState(initial = emptyList())
    val collecetedImages by viewModel.getTodoImages(todoId).collectAsState(initial = emptyList())




    if (collectedTodo.isEmpty()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth()
        ) {
            LoaderBottomSheet("Fetching Your Property Details... Hold on, It's Loading Up!")
        }
    } else {

        val propertyDetails: TodoWithSubTodos = collectedTodo[0];
        var dueDate =
            propertyDetails!!.todo.dueDate?.let { CountdownTimerForDueDate(dueDateTime = it) }
        var checkedState by remember { mutableStateOf(propertyDetails.todo.status) }
        Scaffold(
            floatingActionButton = {
                if (!propertyDetails.todo.status) AddSubTodoFloatingButtons(
                    onCreateNewClick = {
                        NavigationUtil.navigateTo("${Screen.AddOrEditSubToDo.name}/${todoId}")
                    },
                    onPickFromPreDefinedClick = {
                        NavigationUtil.navigateTo("${Screen.PreDefinedSubTask.name}/${todoId}")
                    }
                )
            },
            topBar = {

                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(propertyDetails.todo.title)
                            if (AppUtil.shouldShowVerified(propertyDetails)) LocationVerifiedLogo()
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
                        Box(Modifier.padding(end = 15.dp)) {
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
                                        NavigationUtil.navigateTo("${Screen.EditTodo.name}/${todoId}")
                                    }
                            )
                        }
                    })
            },
            content = {
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(bottom = 50.dp, start = 10.dp, end = 10.dp)
                        .padding(24.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .verticalScroll(state = scrollState),
                ) {

                    if (collecetedImages.isEmpty()) {
                        Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                            Box(
                                Modifier
                                    .size(200.dp)
                                    .fillMaxWidth(), Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.home),
                                    contentDescription = null,
                                    Modifier.size(150.dp),
                                    Alignment.Center
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(16.dp)
                                    ),
                                contentAlignment = Alignment.Center,
                            ) {
                                collecetedImages.map { bitmap -> bitmap.image }
                                    ?.let { it1 -> ImageLoader(bitmapList = it1) }
                            }
                        }


                        Spacer(
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
                        )


                    }
                    Spacer(modifier = Modifier.padding(top = 20.dp))

                    Text(text = propertyDetails.todo.description)


                    Divider(
                        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp),
                        thickness = 3.dp
                    )

                    Box {
                        Row {
                            Spacer(modifier = Modifier.padding(horizontal = 1.dp))
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                Modifier.padding(end = 5.dp).size(20.dp),
                                tint = AppUtil.getPriorityColor(propertyDetails.todo.priority!!)
                            )
                            Text(text = "${AppUtil.getPriorityString(propertyDetails.todo.priority)}")


                        }


                    }
                    Divider(
                        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp), thickness = 3.dp
                    )


                    if (propertyDetails.todo.price == 0.0) {
                        Box {

                            Row {
                                Icon(
                                    painter = painterResource(id = R.drawable.moneyimage),
                                    contentDescription = null,
                                    Modifier.padding(end = 5.dp).size(20.dp)
                                )
                                Text(text = "Not Specified")
                            }

                        }
                    } else {
                        Box {

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.padding(horizontal = 2.dp))

                                Icon(
                                    painter = painterResource(id = R.drawable.moneyimage),
                                    contentDescription = null,
                                    Modifier.padding(end = 5.dp).size(20.dp)
                                )
                                Text(propertyDetails.todo.price.toString() + " £")
                            }


                        }
                    }
                    Divider(
                        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp), thickness = 3.dp
                    )



                    Box {

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(modifier = Modifier.padding(horizontal = 1.dp))

                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = null,
                                Modifier.padding(end = 5.dp).size(20.dp)
                            )

                            Text(
                                text = "${
                                    LocalDatetimeToWords.formatLocalDateTimeAsWords(
                                        propertyDetails.todo.dueDate
                                    )
                                }"
                            )
                        }

                    }
                    Divider(
                        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp), thickness = 3.dp
                    )
                    Box() {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            if (!propertyDetails.todo.status) {
                                if (dueDate != null) {
                                    if (dueDate != AppUtil.OVERDUE) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.duration),
                                            contentDescription = null,
                                            Modifier.padding(end = 5.dp).size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = dueDate,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            textAlign = TextAlign.Start
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(id = R.drawable.duration),
                                            contentDescription = null,
                                            Modifier.padding(end = 5.dp).size(20.dp),
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
                            } else {

                                Icon(
                                    painter = painterResource(id = R.drawable.duration),
                                    contentDescription = null,
                                    Modifier.padding(end = 5.dp).size(20.dp),
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


                    }
                    Divider(
                        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp), thickness = 3.dp
                    )
                    Spacer(modifier = Modifier.padding(top = 10.dp))

                    Box(Modifier.fillMaxWidth()) {
                        if (propertyDetails.todo.longitude == 0.0 && propertyDetails.todo.latitude == 0.0) {

                            Row(Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.padding(horizontal = 1.dp))

                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = null,
                                    Modifier.padding(end = 5.dp).size(20.dp),
                                    tint = Color(0XFFffcc00)
                                )
                                Text(text = "Property Not Verified")

                            }
                            Spacer(modifier = Modifier.padding(top = 10.dp))

                        } else {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.GeoLocation(
                                            propertyDetails.todo.latitude!!,
                                            propertyDetails.todo.longitude!!,
                                            context = todoContext
                                        )
                                    }, Arrangement.Center
                            ) {
                                LocateMe()
                            }


                        }

                    }

                    Divider(
                        modifier = Modifier.padding(top = 10.dp), thickness = 3.dp
                    )

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Button(
                            onClick = {
                                checkedState = !checkedState
                                viewModel.updateTodo(
                                    propertyDetails.todo.todoId,
                                    checkedState
                                )
                                NavigationUtil.goBack()
                            },
                            colors = if (!propertyDetails.todo.status) ButtonDefaults.buttonColors(
                                containerColor = Color.Blue
                            ) else ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Row(
                                Modifier
                                    .padding(5.dp),
                                Arrangement.Center,
                            ) {
                                Text(
                                    text = if (!propertyDetails.todo.status) "Mark Completed" else "Mark as InComplete",
                                    fontWeight = FontWeight.Bold
                                )
                            }


                        }

                    }


                    Spacer(modifier = Modifier.padding(top = 20.dp))


                    Text(
                        text = "Tasks",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    if (propertyDetails.subtodos.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 20.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Empty task list? Time to fill it up with your plans and goals!",
                                fontWeight = FontWeight.Light,
                                color = PrimaryColor,
                                textAlign = TextAlign.Center
                            )
                            Image(
                                painter = painterResource(id = R.drawable.ic_no_completed_list),
                                contentDescription = "title",
                                Modifier.size(100.dp)
                            )

                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.height(500.dp)
                        ) {
                            items(propertyDetails.subtodos) { subTask ->
                                //subTask.title?.let { it1 -> Text(text = it1, Modifier.padding(15.dp)) }
                                Box() {
                                    subTodoId = subTask.subTodoId.toInt()

                                    SubTaskListItem(
                                        property = propertyDetails,
                                        subTask = subTask,
                                        viewModel = viewModel
                                    )
                                }
                            }
                        }
                    }


                }


            })
    }
}


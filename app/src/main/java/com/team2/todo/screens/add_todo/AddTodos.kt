package com.team2.todo.screens.add_todo

import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.team2.todo.common_ui_components.CameraCapture
import com.team2.todo.common_ui_components.ImageLoader
import com.team2.todo.common_ui_components.location.VerifyByLocationCompose
import com.team2.todo.data.RealEstateDatabase
import com.team2.todo.data.entities.Images
import com.team2.todo.data.entities.Todo
import com.team2.todo.data.repo.TodoRepo
import com.team2.todo.common_ui_components.LoaderBottomSheet
import com.team2.todo.data.entities.SubTodo
import com.team2.todo.data.entities.relations.TodoWithSubTodos
import com.team2.todo.data.repo.SubTodoRepo
import com.team2.todo.screens.add_todo.ui_components.AddEditAppBar
import com.team2.todo.screens.add_todo.ui_components.DateAndTimeField
import com.team2.todo.screens.add_todo.ui_components.DatePickerPopup
import com.team2.todo.screens.add_todo.ui_components.PickImageForSubTodo
import com.team2.todo.screens.add_todo.ui_components.PickImagesForTodo
import com.team2.todo.screens.add_todo.ui_components.PriorityPickerComponent
import com.team2.todo.screens.add_todo.ui_components.ReminderField
import com.team2.todo.screens.add_todo.ui_components.TimePickerPopup
import com.team2.todo.screens.add_todo.ui_components.priorities
import com.team2.todo.screens.add_todo.ui_components.UploadImagePlaceHolder
import com.team2.todo.screens.add_todo.view_model.AddSubTodoViewModel
import com.team2.todo.screens.add_todo.view_model.AddTodoViewModel
import com.team2.todo.screens.add_todo.view_model.FetchSubtodoViewModel
import com.team2.todo.screens.add_todo.view_model.FetchTodoViewModel
import com.team2.todo.screens.listing.view_model.ListingViewModel
import com.team2.todo.ui.theme.PrimaryColor
import com.team2.todo.utils.NavigationUtil
import com.team2.todo.utils.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Currency

/**
 * Created by Atharva K on 11/13/23.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodos(
    isSubTodo: Boolean = false,
    todoid: Long = 0,
    //isEdit when set to true will allow editing of todos
    isEdit: Boolean = false,
    //isEditSubTodo when set to true will allow editing of subtodos
    isEditSubTodo: Boolean = false
) {

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val OutLineTextColor = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = PrimaryColor,
        focusedLabelColor = Color.Black,
        disabledLabelColor = PrimaryColor
    )
    val OutLinedTextModifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)

    var ctx = LocalContext.current

    //Creating instance of TodoViewModel for accessing the db query functions
    var db = RealEstateDatabase.getInstance(ctx)
    var repository = TodoRepo(db)
    var viewModel = AddTodoViewModel(repository)
    var fetchTodViewModel = FetchTodoViewModel(repository)

    //Creating instance of SubTodoViewModel for accessing the db query functions
    var subtodorepo = SubTodoRepo(db)
    var subtodviewmodel = AddSubTodoViewModel(subtodorepo)
    var editsubtodoviewmodel = FetchSubtodoViewModel(subtodorepo)

    //rememberSaveable is used so that the values remain as they are when orientation changes
    var enteredTitle by rememberSaveable {
        mutableStateOf("")
    }
    var enteredLabel by rememberSaveable {
        mutableStateOf("")
    }
    var enteredDescription by rememberSaveable {
        mutableStateOf("")
    }

    var enteredPrice by rememberSaveable {
        mutableStateOf(0.0)
    }

    var defaultPriority by rememberSaveable {
        mutableStateOf("Low")
    }

    var pound = Currency.getInstance("GBP")

    //Variable used for storing images of a todo based on todoid
    val collectedImages by fetchTodViewModel.getTodoImages(todoid)
        .collectAsState(initial = emptyList())


    var (calendarState, dateselected) = DatePickerPopup()
    var (timeState, timeselected) = TimePickerPopup()

    //latitude and longitude variables are used for storing location coordinates of the user.
    var currentlatitude by rememberSaveable {
        mutableStateOf(0.0)
    }
    var currentlongitude by rememberSaveable {
        mutableStateOf(0.0)
    }

    //bitmapList used for storing list of images uploaded by user
    var bitmapList: List<Bitmap> = mutableListOf()

    var bitmap: Bitmap? = null
    var localdateTime: LocalDateTime = LocalDateTime.now()

    //Getting TodoId from Todos Table
    val scope = rememberCoroutineScope()
    var todoIdretrieved by rememberSaveable { mutableStateOf<Long?>(null) }
    var todoIdretrievalInProgress by rememberSaveable { mutableStateOf(false) }


    var selectpriorityindex by rememberSaveable {
        mutableStateOf(0)
    }

    //these are progress variables used for a db query. When set to true, db operation is complete
    var showAddingDbLoading by rememberSaveable { mutableStateOf(false) }
    var showFetchingDbLoading by rememberSaveable { mutableStateOf(false) }
    var showFetchingSubTodoLoading by rememberSaveable { mutableStateOf(false) }
    var todosRetrieved by remember { mutableStateOf<Flow<List<TodoWithSubTodos>>?>(null) }
    var subtodoRetrieved by remember { mutableStateOf<Flow<SubTodo>>(emptyFlow()) }
    var todosretrievalInProgress by remember { mutableStateOf(false) }

    //these variables are used for validation checking
    var isLabelValid by remember { mutableStateOf(true) }
    var isTitleEmpty by rememberSaveable { mutableStateOf(false) }
    var isLabelEmpty by rememberSaveable { mutableStateOf(false) }
    var isDescriptionEmpty by rememberSaveable { mutableStateOf(false) }

    var updatedBitmapList by remember { mutableStateOf(mutableListOf<Bitmap>()) }
    var subtodoid by remember { mutableLongStateOf(0L) }
    var mainTodoId by remember { mutableLongStateOf(0L) }

    if (isEditSubTodo) {
        showFetchingSubTodoLoading = true
        LaunchedEffect(key1 = true) {
            try {
                todosretrievalInProgress = true
                subtodoRetrieved = editsubtodoviewmodel.fetchSubtodo(todoid)
                todosretrievalInProgress = false
                subtodoRetrieved?.collect { subTodo ->
                        enteredTitle = subTodo.title ?: ""
                        enteredDescription = subTodo.description ?: ""
                        mainTodoId = subTodo.todoId
                        subtodoid = subTodo.subTodoId
                        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        dateselected.value =
                            subTodo.dueDate?.toLocalDate()?.format(dateFormatter) ?: "---"

                        val timeformatter = DateTimeFormatter.ofPattern("[ HH:m[:ss]]")
                        timeselected.value =
                            subTodo.dueDate?.toLocalTime()?.format(timeformatter) ?: "---"

                        val priorityindex = subTodo.priority
                        val priorityList = priorities.values()

                        if (priorityindex != null && priorityindex in priorityList.indices) {
                            defaultPriority = priorityList[priorityindex].name
                        } else {
                            defaultPriority = "Low"
                        }

                        val bitmap = subTodo.image
                        bitmap?.let {
                            updatedBitmapList.add(it)
                        }

                        bitmapList = updatedBitmapList

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    if (isEdit) {
        showFetchingDbLoading = true
        LaunchedEffect(key1 = true) {
            try {
                todosretrievalInProgress = true
                todosRetrieved = fetchTodViewModel.fetchTodo(todoid)
                todosretrievalInProgress = false
                todosRetrieved?.collect { todoList ->
                    for (todo in todoList) {
                        enteredTitle = todo.todo.title
                        enteredDescription = todo.todo.description
                        enteredLabel = todo.todo.label ?: ""
                        enteredPrice = todo.todo.price
                        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                        dateselected.value =
                            todo.todo.dueDate?.toLocalDate()?.format(dateFormatter) ?: "---"

                        val timeformatter = DateTimeFormatter.ofPattern("[ HH:m[:ss]]")
                        timeselected.value =
                            todo.todo.dueDate?.toLocalTime()?.format(timeformatter) ?: "---"
                        val priorityindex = todo.todo.priority
                        val priorityList = priorities.values()

                        if (priorityindex != null && priorityindex in priorityList.indices) {
                            defaultPriority = priorityList[priorityindex].name
                        } else {
                            defaultPriority = "Low"
                        }

                        collectedImages.forEach { image ->
                            // Convert image to Bitmap and add to the list
                            val bitmap = image.image
                            bitmap?.let {
                                updatedBitmapList.add(it)
                            }
                        }
                        bitmapList = updatedBitmapList

                    }
                } ?: run {
                    Toast.makeText(
                        ctx,
                        "Error fetching Todo",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
//                Toast.makeText(ctx, "Error fetching Todo", Toast.LENGTH_SHORT)
//                    .show()
            }
        }


    }
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(it)
        ) {
            if (isSubTodo) {
                if (isEditSubTodo) {
                    AddEditAppBar(isSubTodo, false, isEditSubTodo)
                } else {
                    AddEditAppBar(isSubTodo)
                }
            } else if (isEdit) {
                AddEditAppBar(isSubTodo, isEdit)
            } else {
                AddEditAppBar()
            }
            Column(
                modifier = Modifier
                    .weight(weight = 1.0F)
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                //Title
                OutlinedTextField(
                    modifier = OutLinedTextModifier,
                    value = enteredTitle,
                    onValueChange = {
                        enteredTitle = it
                        isTitleEmpty = it.isEmpty()
                    },
                    label = { if (isSubTodo) Text(text = "Subtask Title") else Text(text = "Title") },
                    colors = OutLineTextColor,
                    isError = isTitleEmpty,
                )
                //Label
                if (!isSubTodo) {
                    OutlinedTextField(
                        modifier = OutLinedTextModifier,
                        value = enteredLabel,
                        onValueChange = {
                            enteredLabel = it
                            isLabelEmpty = it.isEmpty()
                            isLabelValid = it.split("\\s+".toRegex()).size == 1
                        },
                        label = { Text(text = "Label") },
                        colors = OutLineTextColor,
                        isError = isLabelEmpty || !isLabelValid,
                    )
                }
                // Description
                OutlinedTextField(
                    value = enteredDescription,
                    modifier = OutLinedTextModifier.height(120.dp),
                    onValueChange = {
                        enteredDescription = it
                        isDescriptionEmpty = it.isEmpty()
                    },
                    label = { if (isSubTodo) Text(text = "Subtask Description") else Text(text = "Description") },
                    colors = OutLineTextColor,
                    isError = isDescriptionEmpty,
                )


                if (bitmapList.isEmpty())
                    UploadImagePlaceHolder(onCLick = {
                        showBottomSheet = true
                    })

                //Bottom sheet for image selection
                if (showBottomSheet) {
                    ModalBottomSheet(onDismissRequest = { showBottomSheet = false; }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp)
                                .paddingFromBaseline(top = 10.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            //Camera Upload Option
                            CameraCapture { bitmapCallback ->
                                bitmap = bitmapCallback
                                bitmapList = listOf(bitmapCallback)
                                showBottomSheet = false
                                Log.d("Image", bitmapList.toString())
                            }


                            if (isSubTodo) {
                                PickImageForSubTodo { bitmapCallback ->
                                    bitmap = bitmapCallback
                                    bitmapList = listOf(bitmapCallback)
                                    showBottomSheet = false
                                }
                            } else {

                                PickImagesForTodo { bitmapCallback ->
                                    bitmapList = bitmapCallback
                                    showBottomSheet = false
                                }


                            }
                        }

                    }

                }
                if (bitmapList.isNotEmpty()) {
                    ImageLoader(bitmapList = bitmapList) {
                        showBottomSheet = true
                    }

                }

                //storing the index of selected priority which is an enum
                selectpriorityindex = PriorityPickerComponent(defaultPriority = defaultPriority)
                if (!isSubTodo) {
                    OutlinedTextField(
                        value = "${enteredPrice} ${pound.getSymbol()}",
                        onValueChange = { newText ->
                            val priceWithoutSymbol = newText.removeSuffix(pound.getSymbol()).trim()
                            enteredPrice = priceWithoutSymbol.toDouble()
                        },
                        label = { Text(text = "Price: ") },
                        placeholder = { Text(text = "Enter price(in ${pound.getSymbol()}") },
                        //used for allowing only number values
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                        ),
                        colors = OutLineTextColor,
                        modifier = OutLinedTextModifier,
                    )
                }
                DateAndTimeField(date = dateselected.value,
                    time = timeselected.value,
                    onDateClick = {
                        calendarState.show()
                    },
                    onTimeClick = {
                        timeState.show()
                    })

                if (dateselected.value != "" && timeselected.value != "") {
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm[:ss]")
                    try {
                        localdateTime = LocalDateTime.parse(
                            dateselected.value.trim() + timeselected.value.trim(), formatter
                        )

                    } catch (e: Exception) {
                        e.printStackTrace()
                        println("Error parsing date or time: ${e.message}")
                    }

                    //used to show reminder which is 1 day before due date
                    ReminderField(localdateTime)
                }
                if (!isSubTodo) {
                    //verifying current location of the user
                    VerifyByLocationCompose(callback = { location ->
                        currentlatitude = location.latitude
                        currentlongitude = location.longitude
                    })
                }


            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp),
                elevation = ButtonDefaults.buttonElevation(6.dp),
                onClick = {
                    if (enteredTitle.isEmpty()) {
                        Toast.makeText(ctx, "Please fill the title", Toast.LENGTH_SHORT).show()
                        isTitleEmpty = true
                    } else if (enteredDescription.isEmpty()) {
                        Toast.makeText(ctx, "Please fill the description", Toast.LENGTH_SHORT)
                            .show()
                        isDescriptionEmpty = true
                    } else if (!isLabelValid) {
                        Toast.makeText(ctx, "Label should be only 1 word", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        if (isSubTodo && !isEditSubTodo) {
                            subtodviewmodel.addSubTodo(
                                SubTodo(
                                    0,
                                    todoid,
                                    enteredTitle,
                                    enteredDescription,
                                    bitmap,
                                    LocalDateTime.now(),
                                    localdateTime,
                                    false,
                                    selectpriorityindex
                                )
                            )
                            NavigationUtil.goBack();
                            Toast.makeText(
                                ctx,
                                "SubTodo added successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else if (isSubTodo && isEditSubTodo) {

                            scope.launch {
                                try {
                                    if (bitmapList.isNotEmpty()) {
                                        todoIdretrievalInProgress = true
                                        subtodviewmodel.addSubTodo(

                                            SubTodo(
                                                subtodoid,
                                                mainTodoId,
                                                enteredTitle,
                                                enteredDescription,
                                                bitmapList[0],
                                                LocalDateTime.now(),
                                                localdateTime,
                                                false,
                                                selectpriorityindex
                                            )


                                        )
                                    } else {
                                        todoIdretrievalInProgress = true
                                        subtodviewmodel.addSubTodo(

                                            SubTodo(
                                                subtodoid,
                                                mainTodoId,
                                                enteredTitle,
                                                enteredDescription,
                                                null,
                                                LocalDateTime.now(),
                                                localdateTime,
                                                false,
                                                selectpriorityindex
                                            )


                                        )
                                    }

                                    todoIdretrievalInProgress = false
                                    NavigationUtil.goBack()
                                    NavigationUtil.navigateTo("${Screen.SubTodoDetails.name}/${subtodoid}")
                                    Toast.makeText(
                                        ctx,
                                        "SubTodo updated successfully",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()

                                } catch (e: Exception) {
                                    showAddingDbLoading = false;
                                    e.printStackTrace()
                                }
                            }
                        } else if (isEdit) {
                            showAddingDbLoading = true
                            scope.launch {
                                try {
                                    todoIdretrievalInProgress = true
                                    todoIdretrieved = viewModel.addTodo(

                                        Todo(
                                            todoid,
                                            enteredTitle,
                                            enteredLabel,
                                            enteredDescription,
                                            currentlatitude,
                                            currentlongitude,
                                            enteredPrice,
                                            LocalDateTime.now(),
                                            localdateTime,
                                            false,
                                            selectpriorityindex
                                        )


                                    )
                                    todoIdretrievalInProgress = false
                                    todoIdretrieved?.let { todoId ->
                                        showAddingDbLoading = false
                                        NavigationUtil.goBack()
                                        NavigationUtil.navigateTo("${Screen.DetailsScreen.name}/${todoid}")

                                        Toast.makeText(
                                            ctx,
                                            "Todo updated successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()

                                    }

                                } catch (e: Exception) {
                                    showAddingDbLoading = false;
                                    e.printStackTrace()
                                    Toast.makeText(ctx, "Error updating Todo", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        } else {
                            showAddingDbLoading = true
                            scope.launch {
                                try {
                                    todoIdretrievalInProgress = true
                                    todoIdretrieved = viewModel.addTodo(

                                        Todo(
                                            0,
                                            enteredTitle,
                                            enteredLabel,
                                            enteredDescription,
                                            currentlatitude,
                                            currentlongitude,
                                            enteredPrice,
                                            LocalDateTime.now(),
                                            localdateTime,
                                            false,
                                            selectpriorityindex
                                        )


                                    )
                                    todoIdretrievalInProgress = false
                                    todoIdretrieved?.let { todoId ->
                                        for (imageBitmapData in bitmapList) {
                                            viewModel.addImage(Images(0, imageBitmapData, todoId))
                                        }
                                        showAddingDbLoading = false
                                        NavigationUtil.goBack()
                                        NavigationUtil.navigateTo("${Screen.DetailsScreen.name}/${todoId}")
                                        ListingViewModel.instance?.fetchUpdatedList()
                                        Toast.makeText(
                                            ctx,
                                            "Todo added successfully",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()

                                    } ?: run {
                                        Toast.makeText(
                                            ctx,
                                            "Error adding Todo",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }

                                } catch (e: Exception) {
                                    showAddingDbLoading = false;
                                    e.printStackTrace()
                                    Toast.makeText(ctx, "Error adding Todo", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                        }

                    }
                },
                shape = MaterialTheme.shapes.small.copy(all = CornerSize(10.dp))
            ) {
                Text(
                    text = if (isEdit || isEditSubTodo) "UPDATE" else "ADD",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
        if (showAddingDbLoading && isEdit) {
            ModalBottomSheet(onDismissRequest = { showAddingDbLoading = false; }) {
                LoaderBottomSheet(text = "Updating data in DB")
            }
        }



        if (showAddingDbLoading && !isEdit) {
            ModalBottomSheet(onDismissRequest = { showAddingDbLoading = false; }) {
                LoaderBottomSheet()
            }
        }

    }

}
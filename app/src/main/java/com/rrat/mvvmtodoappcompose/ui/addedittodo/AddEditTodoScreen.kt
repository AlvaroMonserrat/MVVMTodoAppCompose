package com.rrat.mvvmtodoappcompose.ui.addedittodo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rrat.mvvmtodoappcompose.util.UiEvent
import kotlinx.coroutines.flow.collect


@Composable
fun AddEditTodoScreen(
    onPopBackStack: ()->Unit,
    viewModel: AddEditTodoViewModel = hiltViewModel()
)
{
    val scaffoldState = remember {
        mutableStateOf(SnackbarHostState())
    }
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            event->
            when(event)
            {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.value.showSnackbar(message = event.message, actionLabel = event.action)
                }
                else -> Unit
            }


        }
    }
    Scaffold(
        scaffoldState = ScaffoldState(DrawerState(initialValue = DrawerValue.Open),scaffoldState.value),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClick)
            }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(value = viewModel.title, onValueChange = {
                viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it))
            },
            placeholder = {
                Text(text = "Title")
            },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(value = viewModel.description, onValueChange = {
                viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it))
            },
                placeholder = {
                    Text(text = "Description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )
        }
    }
}
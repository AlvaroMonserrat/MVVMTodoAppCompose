package com.rrat.mvvmtodoappcompose.ui.todolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.rrat.mvvmtodoappcompose.data.source.local.mapToUiModel
import com.rrat.mvvmtodoappcompose.util.UiEvent
import kotlinx.coroutines.flow.collect


@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
){
    val todos = viewModel.todos.collectAsState(initial = emptyList())
    val scaffoldState = remember {
        mutableStateOf(SnackbarHostState())
    }
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            event ->
            when(event){
                is UiEvent.ShowSnackBar->{
                    val result = scaffoldState.value.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if(result == SnackbarResult.ActionPerformed)
                    {
                        viewModel.onEvent(TodoListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate->onNavigate(event)
                else-> Unit
            }
        }
    }
    
    Scaffold(
        scaffoldState = ScaffoldState(DrawerState(initialValue = DrawerValue.Open),scaffoldState.value),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TodoListEvent.OnAddTodoClick)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(todos.value)
            {
                todo->
                TodoItem(
                    todo = todo.mapToUiModel(),
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(TodoListEvent.OnTodoClick(todo.mapToUiModel()))
                        }
                )
            }
        }
    }
}
package com.rrat.mvvmtodoappcompose.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rrat.mvvmtodoappcompose.data.Todo
import com.rrat.mvvmtodoappcompose.data.asEntity
import com.rrat.mvvmtodoappcompose.data.source.TodoRepository
import com.rrat.mvvmtodoappcompose.util.Routes
import com.rrat.mvvmtodoappcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {

    val todos = repository.observeTasks()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent)
    {
        when(event)
        {
            is TodoListEvent.OnTodoClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnAddTodoClick ->{
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnUndoDeleteClick ->{
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insertTodo(todo.asEntity())
                    }
                }
            }
            is TodoListEvent.OnDeleteTodoClick->{
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo.asEntity())
                    sendUiEvent(UiEvent.ShowSnackBar(message = "Todo deleted", action = "Undo"))
                }
            }
            is TodoListEvent.OnDoneChange->{
                viewModelScope.launch {
                    repository.insertTodo(event.todo.asEntity(isDone = event.isDone))
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent)
    {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

}
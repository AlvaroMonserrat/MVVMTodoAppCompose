package com.rrat.mvvmtodoappcompose.data.source

import com.rrat.mvvmtodoappcompose.data.source.local.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: TodoEntity)

    suspend fun deleteTodo(todo: TodoEntity)

    suspend fun getTodoById(id: Int): TodoEntity?

    fun observeTasks(): Flow<List<TodoEntity>>

    suspend fun getTodos(): List<TodoEntity>
}
package com.rrat.mvvmtodoappcompose.data.source

import com.rrat.mvvmtodoappcompose.data.source.local.TodoDao
import com.rrat.mvvmtodoappcompose.data.source.local.TodoEntity

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val dao: TodoDao): TodoRepository {
    override suspend fun insertTodo(todo: TodoEntity) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: TodoEntity) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): TodoEntity? {
        return dao.getTodoById(id)
    }

    override fun observeTasks(): Flow<List<TodoEntity>> {
        return dao.observeTasks()
    }

    override suspend fun getTodos(): List<TodoEntity> {
        return dao.getTodos()
    }

}
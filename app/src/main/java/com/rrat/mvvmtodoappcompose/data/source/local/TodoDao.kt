package com.rrat.mvvmtodoappcompose.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("SELECT * FROM todo_table Where id = :id")
    suspend fun getTodoById(id: Int): TodoEntity?

    /*Observes list of todos*/
    @Query("SELECT * FROM todo_table")
    fun observeTasks(): Flow<List<TodoEntity>>

    /*Select all todos from the todos table*/
    @Query("SELECT * FROM todo_table")
    suspend fun getTodos(): List<TodoEntity>
}
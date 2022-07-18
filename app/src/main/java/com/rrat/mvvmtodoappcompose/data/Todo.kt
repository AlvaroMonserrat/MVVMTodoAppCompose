package com.rrat.mvvmtodoappcompose.data

import com.rrat.mvvmtodoappcompose.data.source.local.TodoEntity

data class Todo(
    val id: Int?,
    val title: String,
    val description: String?,
    val isDone: Boolean
)

fun Todo.asEntity(isDone: Boolean = false) = TodoEntity(id=id, title=title, description = description, isDone = isDone)
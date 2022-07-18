package com.rrat.mvvmtodoappcompose.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rrat.mvvmtodoappcompose.data.Todo


@Entity(tableName = "todo_table")
data class TodoEntity(
    val title: String,
    val description: String?,
    val isDone: Boolean,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

fun TodoEntity.mapToUiModel() = Todo(id=id, title=title, description=description, isDone=isDone)
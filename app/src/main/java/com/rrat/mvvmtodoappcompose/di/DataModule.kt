package com.rrat.mvvmtodoappcompose.di

import android.app.Application
import androidx.room.Room
import com.rrat.mvvmtodoappcompose.data.source.TodoRepository
import com.rrat.mvvmtodoappcompose.data.source.TodoRepositoryImpl
import com.rrat.mvvmtodoappcompose.data.source.local.TodoDao
import com.rrat.mvvmtodoappcompose.data.source.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase{
        return Room.databaseBuilder(app, TodoDatabase::class.java, "todo_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.dao)
    }

    @Provides
    fun provideTodoDao(db: TodoDatabase): TodoDao {
        return db.dao
    }
}
package com.example.todolist_app.app.di

import com.example.todolist_app.data.repository.TodoListRepositoryImpl
import com.example.todolist_app.data.source.local.dao.TodoListDao
import com.example.todolist_app.domain.repository.TodoListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun providesTodoListRepository(
        todoListDao: TodoListDao
    ): TodoListRepository = TodoListRepositoryImpl(todoListDao)
}

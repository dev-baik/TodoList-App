package com.example.todolist_app.app.di

import com.example.todolist_app.data.source.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideTodoListDao(appDatabase: AppDatabase) = appDatabase.todoListDao()
}

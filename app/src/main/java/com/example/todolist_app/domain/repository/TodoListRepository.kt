package com.example.todolist_app.domain.repository

import com.example.todolist_app.domain.model.TodoList
import kotlinx.coroutines.flow.Flow

interface TodoListRepository {

    fun loadList(): Flow<List<TodoList>>

    suspend fun save(item: TodoList): Boolean

    suspend fun delete(item: TodoList): Boolean
}

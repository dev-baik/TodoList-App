package com.example.todolist_app.domain.usecase

import com.example.todolist_app.domain.model.TodoList
import com.example.todolist_app.domain.repository.TodoListRepository
import javax.inject.Inject

class TodoListUseCase @Inject constructor(
    private val todoListRepository: TodoListRepository
) {

    fun loadList() = todoListRepository.loadList()

    suspend fun save(item: TodoList) = todoListRepository.save(item)

    suspend fun delete(item: TodoList) = todoListRepository.delete(item)
}

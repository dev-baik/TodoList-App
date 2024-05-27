package com.example.todolist_app.data.repository

import com.example.todolist_app.data.model.TodoListMapper.toEntity
import com.example.todolist_app.data.model.TodoListMapper.toTodoList
import com.example.todolist_app.data.source.local.dao.TodoListDao
import com.example.todolist_app.domain.model.TodoList
import com.example.todolist_app.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class TodoListRepositoryImpl @Inject constructor(
    private val todoListDao: TodoListDao
) : TodoListRepository {

    override fun loadList(): Flow<List<TodoList>> {
        return flow {
            todoListDao.selectAll().collect { list ->
                emit(list.map { it.toTodoList() })
            }
        }
    }

    override suspend fun save(item: TodoList): Boolean {
        return try {
            todoListDao.insert(item.toEntity())
            true
        } catch (e: IOException) {
            false
        }
    }

    override suspend fun delete(item: TodoList): Boolean {
        return try {
            todoListDao.delete(item.toEntity())
            true
        } catch (e: IOException) {
            false
        }
    }
}

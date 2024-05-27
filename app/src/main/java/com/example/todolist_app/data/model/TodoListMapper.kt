package com.example.todolist_app.data.model

import com.example.todolist_app.data.model.entity.TodoListEntity
import com.example.todolist_app.domain.model.TodoList

object TodoListMapper {

    fun TodoListEntity.toTodoList() = TodoList(
        id = id,
        task = task,
        createdDate = createdDate
    )

    fun TodoList.toEntity() = TodoListEntity(
        id = id,
        task = task,
        createdDate = createdDate
    )
}

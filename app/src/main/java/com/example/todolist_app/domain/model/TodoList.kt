package com.example.todolist_app.domain.model

import java.io.Serializable
import java.time.LocalDateTime

data class TodoList(
    val id: Int = 0,
    val task: String,
    val createdDate: LocalDateTime,
) : Serializable

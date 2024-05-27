package com.example.todolist_app.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDateTime

@Entity("TodoList")
data class TodoListEntity(
    @PrimaryKey(true)
    val id: Int = 0,

    @ColumnInfo
    var task: String,

    @ColumnInfo
    var createdDate: LocalDateTime,
) : Serializable

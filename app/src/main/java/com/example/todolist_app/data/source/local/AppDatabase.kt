package com.example.todolist_app.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todolist_app.data.model.entity.TodoListEntity
import com.example.todolist_app.data.source.local.dao.TodoListDao

@Database(entities = [TodoListEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoListDao(): TodoListDao
}

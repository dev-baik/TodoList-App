package com.example.todolist_app.presentation.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtil {

    private val yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val dbDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MM/dd hh:mm a")
    private val todoListDateFormat = DateTimeFormatter.ofPattern("yy’ M/d h:mm a")

    fun convertPrintRegisterDateString(date: LocalDate): String = date.format(yyyy_MM_dd)

    fun convertPrintAddTaskDateString(date: LocalDateTime): String = date.format(dbDateFormat)

    fun convertPrintTodoListDateString(date: LocalDateTime): String = date.format(todoListDateFormat)
}

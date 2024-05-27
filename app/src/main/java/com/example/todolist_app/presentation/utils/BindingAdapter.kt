package com.example.todolist_app.presentation.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime

@BindingAdapter("date")
fun TextView.setDate(date: LocalDateTime) {
    text = DateUtil.convertPrintTodoListDateString(date)
}

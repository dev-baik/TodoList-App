package com.example.todolist_app.presentation.utils

import android.app.DatePickerDialog
import android.content.Context
import java.time.LocalDate

object DatePickerDialogUtil {

    fun showDatePickerDialog(
        context: Context,
        initialDate: LocalDate,
        onDateSelected: (LocalDate) -> Unit
    ) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                onDateSelected(selectedDate)
            },
            initialDate.year,
            initialDate.monthValue - 1,
            initialDate.dayOfMonth
        ).show()
    }
}

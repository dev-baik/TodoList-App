package com.example.todolist_app.presentation.utils

import android.app.TimePickerDialog
import android.content.Context

object TimePickerDialog {

    fun showTimePickerDialog(
        context: Context,
        hour: Int,
        minute: Int,
        is24HourView: Boolean = false,
        onTimeSelected: (hours: Int, minutes: Int) -> Unit,
    ) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            onTimeSelected(selectedHour, selectedMinute)
        }

        TimePickerDialog(
            context,
            timeSetListener,
            hour,
            minute,
            is24HourView
        ).show()
    }
}

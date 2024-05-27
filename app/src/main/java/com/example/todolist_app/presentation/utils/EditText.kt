package com.example.todolist_app.presentation.utils

import android.text.InputFilter
import android.widget.EditText

fun EditText.setAlphabeticInputFilter() {
    val filter = InputFilter { source, start, end, _, _, _ ->
        for (index in start until end) {
            if (!source[index].toString().matches("[a-zA-Z\\s]".toRegex())) {
                return@InputFilter ""
            }
        }
        null
    }
    filters = arrayOf(filter)
}

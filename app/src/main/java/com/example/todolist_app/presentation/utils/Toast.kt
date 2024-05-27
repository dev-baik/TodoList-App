package com.example.todolist_app.presentation.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT): Toast =
    Toast.makeText(this, message, length).also { it.show() }

package com.example.todolist_app.presentation.utils

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadRoundImage(uri: String) {
    Glide.with(this)
        .load(uri)
        .circleCrop()
        .into(this)
}

fun ImageView.applyGrayscale() {
    val matrix = ColorMatrix().apply {
        setSaturation(0F)
    }
    colorFilter = ColorMatrixColorFilter(matrix)
}

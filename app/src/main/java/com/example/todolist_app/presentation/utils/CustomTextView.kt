package com.example.todolist_app.presentation.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var originalText: CharSequence? = null

    // 부모 레이아웃의 크기가 변경될 때 동작
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w != oldw) {
            text = originalText
        }
    }

    // 텍스트를 변경할 때 동작
    override fun setText(text: CharSequence?, type: BufferType?) {
        originalText = text
        super.setText(ellipsizeText(text.toString()), type)
    }

    private fun ellipsizeText(text: String): CharSequence {
        if (width == 0) return text

        val availableWidth = width - paddingLeft - paddingRight
        var newText = text
        var newWidth = paint.measureText(newText)

        while (availableWidth < newWidth) {
            newText = newText.dropLast(1)
            newWidth = paint.measureText("$newText···")
        }

        return if (text.length > newText.length) {
            "$newText···"
        } else {
            newText
        }
    }
}

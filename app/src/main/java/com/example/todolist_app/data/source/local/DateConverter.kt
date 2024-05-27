package com.example.todolist_app.data.source.local

import androidx.room.TypeConverter
import com.example.todolist_app.presentation.utils.DateUtil
import java.time.LocalDateTime

class DateConverter {

    @TypeConverter
    fun toDate(timestamp: String): LocalDateTime {
        return LocalDateTime.parse(timestamp, DateUtil.dbDateFormat)
    }

    @TypeConverter
    fun toTimeStamp(date: LocalDateTime): String {
        return DateUtil.dbDateFormat.format(date)
    }
}

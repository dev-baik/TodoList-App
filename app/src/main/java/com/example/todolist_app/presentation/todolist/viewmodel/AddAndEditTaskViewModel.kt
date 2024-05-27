package com.example.todolist_app.presentation.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist_app.domain.model.TodoList
import com.example.todolist_app.domain.usecase.TodoListUseCase
import com.example.todolist_app.presentation.utils.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AddAndEditTaskViewModel @Inject constructor(
    private val todoListUseCase: TodoListUseCase
) : ViewModel() {

    private val _doneEvent = MutableLiveData<Pair<Boolean, String>>()
    val doneEvent: LiveData<Pair<Boolean, String>> = _doneEvent

    private val currentTime = LocalDateTime.now()
    var year = currentTime.year
    var month = currentTime.monthValue
    var day = currentTime.dayOfMonth
    var hour = currentTime.hour
    var minute = currentTime.minute

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private var todoList: TodoList? = null
    var task = MutableLiveData("")

    fun initData(todoList: TodoList) {
        this.todoList = todoList
        task.value = todoList.task
        val createdDate = todoList.createdDate
        year = createdDate.year
        month = createdDate.monthValue
        day = createdDate.dayOfMonth
        hour = createdDate.hour
        minute = createdDate.minute
        setDate(DateUtil.convertPrintAddTaskDateString(createdDate))
    }

    fun setDate(date: String) {
        _date.value = date
    }

    fun insertData() {
        val taskValue = task.value
        val dateValue = LocalDateTime.of(year, month, day, hour, minute)

        if (taskValue.isNullOrBlank() || date.value.isNullOrBlank()) {
            _doneEvent.value = Pair(false, "모든 항목을 입력하셔야 합니다.")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            todoListUseCase.save(
                todoList?.copy(
                    task = taskValue,
                    createdDate = dateValue
                ) ?: TodoList(task = taskValue, createdDate = dateValue)
            ).also {
                _doneEvent.postValue(Pair(true, if (it) "완료했습니다." else "실패했습니다."))
            }
        }
    }
}

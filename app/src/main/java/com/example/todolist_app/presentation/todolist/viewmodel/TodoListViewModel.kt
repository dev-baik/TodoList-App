package com.example.todolist_app.presentation.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist_app.domain.model.TodoList
import com.example.todolist_app.domain.usecase.TodoListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoListUseCase: TodoListUseCase
) : ViewModel() {

    private val _doneEvent = MutableLiveData<Pair<Boolean, String>>()
    val doneEvent: LiveData<Pair<Boolean, String>> = _doneEvent

    fun initDoneEvent() {
        _doneEvent.value = Pair(false, "")
    }

    val todoList = todoListUseCase.loadList()
        .stateIn(
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5000),
            scope = viewModelScope
        )

    fun deleteItem(item: TodoList) {
        viewModelScope.launch(Dispatchers.IO) {
            todoListUseCase.delete(item).also {
                _doneEvent.postValue(Pair(true, if (it) "완료했습니다." else "실패했습니다."))
            }
        }
    }
}

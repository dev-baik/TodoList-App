package com.example.todolist_app.presentation.register.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime

class RegisterUserViewModel : ViewModel() {

    private val _doneEvent = MutableLiveData<Pair<Boolean, String>>()
    val doneEvent: LiveData<Pair<Boolean, String>> = _doneEvent

    var userName = MutableLiveData("")

    private val currentTime = LocalDateTime.now()
    var year = currentTime.year
    var month = currentTime.monthValue
    var day = currentTime.dayOfMonth

    private val _userBirthday = MutableLiveData<String>()
    val userBirthday: LiveData<String> = _userBirthday

    private val _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String> = _imageUri

    fun setUserBirthday(birthday: String) {
        _userBirthday.value = birthday
    }

    fun setImageUri(imageUri: String) {
        _imageUri.value = imageUri
    }

    fun initDoneEvent() {
        _doneEvent.value = Pair(false, "")
    }

    fun validateInputData() {
        val imageUriValue = imageUri.value
        val userNameValue = userName.value
        val userBirthdayValue = userBirthday.value

        if (imageUriValue.isNullOrEmpty() || userNameValue.isNullOrBlank() || userBirthdayValue.isNullOrBlank()) {
            _doneEvent.value = Pair(false, "모든 항목을 입력하셔야 합니다.")
        } else {
            _doneEvent.value = Pair(true, "환영합니다.")
        }
    }
}

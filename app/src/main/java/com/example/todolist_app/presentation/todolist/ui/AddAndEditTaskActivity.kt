package com.example.todolist_app.presentation.todolist.ui

import android.os.Build
import androidx.activity.viewModels
import com.example.todolist_app.R
import com.example.todolist_app.databinding.ActivityAddAndEditTaskBinding
import com.example.todolist_app.domain.model.TodoList
import com.example.todolist_app.presentation.base.BaseActivity
import com.example.todolist_app.presentation.todolist.ui.TodoListFragment.Companion.TODO_LIST_ITEM
import com.example.todolist_app.presentation.todolist.viewmodel.AddAndEditTaskViewModel
import com.example.todolist_app.presentation.utils.DatePickerDialogUtil
import com.example.todolist_app.presentation.utils.DateUtil
import com.example.todolist_app.presentation.utils.TimePickerDialog
import com.example.todolist_app.presentation.utils.setAlphabeticInputFilter
import com.example.todolist_app.presentation.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime

@AndroidEntryPoint
class AddAndEditTaskActivity : BaseActivity<ActivityAddAndEditTaskBinding>(R.layout.activity_add_and_edit_task) {

    private val viewModel: AddAndEditTaskViewModel by viewModels()

    override fun onCreateAction() {
        super.onCreateAction()
        setupTodoListDetails()
        binding.view = this@AddAndEditTaskActivity
        binding.viewModel = this@AddAndEditTaskActivity.viewModel
        binding.etTask.setAlphabeticInputFilter()
        setViewModelObserve()
    }

    private fun setupTodoListDetails() {
        val item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(TODO_LIST_ITEM, TodoList::class.java)
        } else {
            intent.getSerializableExtra(TODO_LIST_ITEM) as TodoList?
        }

        item?.let {
            binding.tvAddAndEdit.text = getString(R.string.edit_task_title)
            binding.btnAddAndEdit.text = getString(R.string.edit_task_edit)
            viewModel.initData(it)
        }
    }

    private fun setViewModelObserve() = with(viewModel) {
        doneEvent.observe(this@AddAndEditTaskActivity) {
            showToast(it.second)
            if (it.first) {
                finish()
            }
        }
    }

    fun showDatePickerDialog() {
        DatePickerDialogUtil.showDatePickerDialog(
            context = this,
            initialDate = LocalDate.of(viewModel.year, viewModel.month, viewModel.day),
            onDateSelected = { selectedDate ->
                updateDateAndShowTimePicker(selectedDate)
            }
        )
    }

    private fun updateDateAndShowTimePicker(
        selectedDate: LocalDate
    ) = with(viewModel) {
        year = selectedDate.year
        month = selectedDate.monthValue
        day = selectedDate.dayOfMonth
        showTimePickerDialog()
    }

    private fun showTimePickerDialog() {
        TimePickerDialog.showTimePickerDialog(
            context = this,
            hour = viewModel.hour,
            minute = viewModel.minute,
            onTimeSelected = { selectedHour, selectedMinute ->
                viewModel.hour = selectedHour
                viewModel.minute = selectedMinute
                viewModel.setDate(formatDateToString())
            }
        )
    }

    private fun formatDateToString(): String {
        return DateUtil.convertPrintAddTaskDateString(
            LocalDateTime.of(
                viewModel.year, viewModel.month, viewModel.day, viewModel.hour, viewModel.minute
            )
        )
    }
}

package com.example.todolist_app.presentation.todolist.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.todolist_app.R
import com.example.todolist_app.databinding.FragmentTodoListBinding
import com.example.todolist_app.domain.model.TodoList
import com.example.todolist_app.presentation.base.BaseFragment
import com.example.todolist_app.presentation.todolist.SwipeHelperCallback
import com.example.todolist_app.presentation.todolist.ui.list.TodoListAdapter
import com.example.todolist_app.presentation.todolist.viewmodel.TodoListViewModel
import com.example.todolist_app.presentation.utils.applyGrayscale
import com.example.todolist_app.presentation.utils.loadRoundImage
import com.example.todolist_app.presentation.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodoListFragment : BaseFragment<FragmentTodoListBinding>(R.layout.fragment_todo_list) {

    private val viewModel: TodoListViewModel by viewModels()
    private val args: TodoListFragmentArgs by navArgs()

    private lateinit var todoListAdapter: TodoListAdapter

    override fun onViewCreatedAction() {
        super.onViewCreatedAction()
        binding.view = this@TodoListFragment
        setImageUri()
        initTodoListRecyclerView()
        setViewModelObserve()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        todoListAdapter.notifyDataSetChanged()
    }

    fun navigateToAddAndEditTask() {
        startActivity(Intent(requireContext(), AddAndEditTaskActivity::class.java))
    }

    private fun setViewModelObserve() {
        viewModel.doneEvent.observe(viewLifecycleOwner) {
            if (it.second.isNotEmpty()) {
                requireContext().showToast(it.second)
                viewModel.initDoneEvent()
            }
        }

        lifecycleScope.launch {
            viewModel.todoList
                .flowWithLifecycle(lifecycle, Lifecycle.State.RESUMED)
                .collectLatest {
                    todoListAdapter.submitList(it)
                }
        }
    }

    private fun initTodoListRecyclerView() {
        todoListAdapter = TodoListAdapter()
        binding.rvTodoList.adapter = todoListAdapter

        val swipeHelperCallback = SwipeHelperCallback(todoListAdapter, Handler())
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvTodoList)
    }

    private fun setImageUri() {
        binding.ivUserImage.loadRoundImage(args.userImage)
        binding.ivUserImage.applyGrayscale()
    }

    inner class Handler {
        fun navigateToEditTask(item: TodoList) {
            val intent = Intent(requireContext(), AddAndEditTaskActivity::class.java).apply {
                putExtra(TODO_LIST_ITEM, item)
            }
            startActivity(intent)
        }

        fun deleteTask(item: TodoList) {
            viewModel.deleteItem(item)
        }
    }

    companion object {
        const val TODO_LIST_ITEM = "todo_list_item"
    }
}

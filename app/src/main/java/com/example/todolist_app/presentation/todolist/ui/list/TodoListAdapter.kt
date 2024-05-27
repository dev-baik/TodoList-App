package com.example.todolist_app.presentation.todolist.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_app.databinding.ItemTodoListSubsequentBinding
import com.example.todolist_app.databinding.ItemTodoListTopBinding
import com.example.todolist_app.domain.model.TodoList
import com.example.todolist_app.presentation.todolist.ui.list.viewholder.SubsequentTodoListViewHolder
import com.example.todolist_app.presentation.todolist.ui.list.viewholder.TopTodoListViewHolder

class TodoListAdapter : ListAdapter<TodoList, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            TopTodoListViewHolder(
                ItemTodoListTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            SubsequentTodoListViewHolder(
                ItemTodoListSubsequentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            (holder as TopTodoListViewHolder).bind(getItem(position))
        } else {
            (holder as SubsequentTodoListViewHolder).bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_OTHER
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_OTHER = 1

        private val diffCallback = object : DiffUtil.ItemCallback<TodoList>() {
            override fun areItemsTheSame(oldItem: TodoList, newItem: TodoList): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TodoList, newItem: TodoList): Boolean {
                return oldItem == newItem
            }
        }
    }
}

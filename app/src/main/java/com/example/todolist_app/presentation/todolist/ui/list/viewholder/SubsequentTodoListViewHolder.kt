package com.example.todolist_app.presentation.todolist.ui.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_app.databinding.ItemTodoListSubsequentBinding
import com.example.todolist_app.domain.model.TodoList

class SubsequentTodoListViewHolder(
    private val binding: ItemTodoListSubsequentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TodoList) {
        binding.item = item

        binding.swipeLayout.translationX = 0f
    }
}

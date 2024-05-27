package com.example.todolist_app.presentation.todolist.ui.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_app.databinding.ItemTodoListTopBinding
import com.example.todolist_app.domain.model.TodoList

class TopTodoListViewHolder(
    private val binding: ItemTodoListTopBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TodoList) {
        binding.item = item

        binding.swipeLayout.translationX = 0f
    }
}

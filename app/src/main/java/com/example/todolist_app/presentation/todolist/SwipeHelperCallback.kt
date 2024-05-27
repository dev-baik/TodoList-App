package com.example.todolist_app.presentation.todolist

import android.graphics.Canvas
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist_app.R
import com.example.todolist_app.presentation.todolist.ui.TodoListFragment
import com.example.todolist_app.presentation.todolist.ui.list.TodoListAdapter

class SwipeHelperCallback(
    private val todoListAdapter: TodoListAdapter,
    private val handler: TodoListFragment.Handler
) : ItemTouchHelper.Callback() {

    private fun getSwipeView(viewHolder: RecyclerView.ViewHolder): View =
        viewHolder.itemView.findViewById(R.id.swipe_layout)

    private fun getTaskAltView(viewHolder: RecyclerView.ViewHolder): View =
        viewHolder.itemView.findViewById(R.id.iv_task_alt)

    private fun getIvEditView(viewHolder: RecyclerView.ViewHolder): View =
        viewHolder.itemView.findViewById(R.id.iv_edit)

    // 이동 방향 결정
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, LEFT or RIGHT)
    }

    // 드래그 일어날 때 동작
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    // 스와이프 일어날 때 동작
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val currentItem = todoListAdapter.currentList[viewHolder.adapterPosition]
        if (direction == LEFT) {
            handler.navigateToEditTask(currentItem)
        } else {
            handler.deleteTask(currentItem)
        }
    }

    // swipe가 cancel되거나 complete되었을 때 동작
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        getDefaultUIUtil().clearView(getSwipeView(viewHolder))
    }

    // 아이템을 터치하거나 스와이프하는 등 뷰에 변화가 생길 경우 동작
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            if (dX > 0) {
                getTaskAltView(viewHolder).isVisible = true
                getIvEditView(viewHolder).isVisible = false
            } else if (dX < 0) {
                getTaskAltView(viewHolder).isVisible = false
                getIvEditView(viewHolder).isVisible = true
            }

            val view = getSwipeView(viewHolder)
            getDefaultUIUtil().onDraw(c, recyclerView, view, dX, dY, actionState, isCurrentlyActive)
        }
    }
}

package com.spase_y.playlistmaker05022024.utils

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class BottomSpaceItemDecoration(private val bottomOffset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        // Получаем позицию текущего элемента
        val position = parent.getChildAdapterPosition(view)
        // Получаем количество элементов в адаптере
        val itemCount = parent.adapter?.itemCount ?: 0
        Log.d("TAG","$itemCount")

        // Если текущий элемент последний, добавляем отступ снизу
        if (position == itemCount - 1) {
            outRect.bottom = bottomOffset
        }
    }
}
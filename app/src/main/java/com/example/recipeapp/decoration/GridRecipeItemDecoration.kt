package com.example.recipeapp.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridRecipeItemDecoration(
    private val padding: Int
): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(rect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if(position == RecyclerView.NO_POSITION) return

        rect.right = padding
        rect.bottom = padding

        rect.top =
                if(position == 0 || position == 1) padding
                else 0

        rect.left =
                if(position % 2 == 0) padding
                else 0
    }
}
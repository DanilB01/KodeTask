package com.example.recipeapp.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearRecipeItemDecoration(
        private val padding: Int
):RecyclerView.ItemDecoration() {

    override fun getItemOffsets(rect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if(position == RecyclerView.NO_POSITION) return

        rect.right = padding
        rect.left = padding
        rect.bottom = padding
        rect.top =
                if (position == 0) padding
                else 0
    }
}
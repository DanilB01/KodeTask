package com.example.recipeapp.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SimilarRecipeItemDecorator(
        private val borderPadding: Int,
        private val dividerPadding: Int
): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(rect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if(position == RecyclerView.NO_POSITION) return

        if(position == 0) {
            rect.left = borderPadding
        } else {
            rect.left = dividerPadding
            if (position == state.itemCount - 1) {
                rect.right = borderPadding
            }
        }
    }
}
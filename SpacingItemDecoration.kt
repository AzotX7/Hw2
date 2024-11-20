package com.example.homew1

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(private val spacing: Int, private val includeEdge: Boolean = false) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (includeEdge) {
            outRect.left = spacing
            outRect.right = spacing
            outRect.bottom = spacing
            if (position < spacing) {
                outRect.top = spacing
            }
        } else {
            outRect.left = spacing
            outRect.right = spacing
            outRect.bottom = spacing
            if (position >= spacing) {
                outRect.top = spacing
            }
        }
    }
}
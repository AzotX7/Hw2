package com.example.homew1

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomGridLayoutManager(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    RecyclerView.LayoutManager() {

    private var totalHeight = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val itemCount = state.itemCount
        totalHeight = 0
        var offsetY = 0
        var currentRowWidth = 0
        var maxHeightInRow = 0

        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            measureChildWithMargins(view, 0, 0)
            val width = getDecoratedMeasuredWidth(view)
            val height = getDecoratedMeasuredHeight(view)
            val left: Int
            val top: Int
            val right: Int
            val bottom: Int

            when {
                i % 4 == 0 -> {
                    left = 0
                    top = offsetY
                    right = width
                    bottom = offsetY + height
                    offsetY += height
                    maxHeightInRow = height
                }
                i % 4 == 1 || i % 4 == 2 -> {
                    val halfWidth = width / 2
                    left = if (currentRowWidth == 0) 0 else halfWidth
                    top = offsetY
                    right = left + halfWidth
                    bottom = offsetY + height

                    currentRowWidth += halfWidth
                    maxHeightInRow = maxOf(maxHeightInRow, height)

                    if (currentRowWidth >= width) {
                        offsetY += maxHeightInRow
                        currentRowWidth = 0
                    }
                }
                else -> {
                    left = 0
                    top = offsetY
                    right = width
                    bottom = offsetY + height
                    offsetY += height
                }
            }

            layoutDecorated(view, left, top, right, bottom)
        }

        totalHeight = offsetY
    }

    override fun canScrollVertically(): Boolean = true
    private var verticalOffset = 0

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State): Int {
        val newOffset = verticalOffset + dy
        val delta = when {
            newOffset < 0 -> -verticalOffset
            newOffset > totalHeight - height -> totalHeight - height - verticalOffset
            else -> dy
        }

        offsetChildrenVertical(-delta)
        verticalOffset += delta

        return delta
    }

    override fun onMeasure(recycler: RecyclerView.Recycler, state: RecyclerView.State, widthSpec: Int, heightSpec: Int) {
        val width = View.MeasureSpec.getSize(widthSpec)
        val height = View.MeasureSpec.getSize(heightSpec)
        setMeasuredDimension(width, height)
    }
}

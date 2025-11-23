package com.pdxx.kotlinlearn.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class MyLayout(context: Context, attrs: AttributeSet? = null) : ViewGroup(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Measure all children
        children.forEach { child ->
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
        }

        // Calculate the total height required
        var totalHeight = 0
        children.forEach { child ->
            totalHeight += child.measuredHeight
        }

        // Set the measured dimension for this layout
        setMeasuredDimension(
            resolveSize(suggestedMinimumWidth, widthMeasureSpec),
            resolveSize(totalHeight, heightMeasureSpec)
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var currentTop = 0
        // Layout children in a vertical stack
        children.forEach { child ->
            child.layout(l, currentTop, r, currentTop + child.measuredHeight)
            currentTop += child.measuredHeight
        }
    }
}

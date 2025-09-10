package com.pdxx.kotlinlearn.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Draw a circle in the center of the view
        val cx = width / 2f
        val cy = height / 2f
        val radius = width.coerceAtMost(height) / 4f
        canvas.drawCircle(cx, cy, radius, paint)
    }
}

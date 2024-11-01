package com.example.myapplication.task3

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView(
    context: Context, attrs: AttributeSet? = null,
) : View(context, attrs) {

    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX, centerY, radius, paint)
    }

    fun setRadius(newRadius: Float) {
        radius = newRadius
        invalidate()
    }

    fun setCenter(x: Float, y: Float) {
        centerX = x
        centerY = y
        invalidate()
    }
}

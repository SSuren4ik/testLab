package com.example.myapplication.task1

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.sqrt

class HouseView(
    context: Context, attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val housePaint = Paint().apply {
        color = Color.parseColor("#FFA500")
        style = Paint.Style.FILL
    }

    private val roofPaint = Paint().apply {
        color = Color.parseColor("#8B4513")
        style = Paint.Style.FILL
    }

    private val sunPaint = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.FILL
    }

    private val chimneyPaint = Paint().apply {
        color = Color.parseColor("#FF0000")
        style = Paint.Style.FILL
    }

    private val grassPaint = Paint().apply {
        color = Color.parseColor("#00FF00")
        style = Paint.Style.FILL
    }

    private val skyPaint = Paint().apply {
        color = Color.parseColor("#87CEEB")
        style = Paint.Style.FILL
    }

    private var sunCenterX = 0f
    private var sunCenterY = 0f
    private var sunRadius = 0f
    private var isMovingSun = false

    private val sunRadiusAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 2000
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { _ ->
            invalidate()
        }
    }

    private val sunAlphaAnimator = ValueAnimator.ofInt(255, 0).apply {
        duration = 2000
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { animation ->
            invalidate()
        }
    }

    init {
        sunRadiusAnimator.start()
        sunAlphaAnimator.start()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sunCenterX = width * 0.8f
        sunCenterY = height * 0.2f
        sunRadius = width * 0.1f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawSky(canvas)
        drawGrass(canvas)
        drawHouse(canvas)
        drawChimney(canvas)
        drawRoof(canvas)
        drawSun(canvas)
    }

    private fun drawHouse(canvas: Canvas) {
        val houseLeft = width * 0.25f
        val houseTop = height * 0.55f
        val houseRight = width * 0.75f
        val houseBottom = height * 0.85f
        canvas.drawRect(houseLeft, houseTop, houseRight, houseBottom, housePaint)
    }

    private fun drawSky(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat() * 0.75f, skyPaint)
    }

    private fun drawGrass(canvas: Canvas) {
        canvas.drawRect(0f, height.toFloat() * 0.75f, width.toFloat(), height.toFloat(), grassPaint)
    }

    private fun drawSun(canvas: Canvas) {
        canvas.drawCircle(sunCenterX, sunCenterY, sunRadius, sunPaint)

        val animatedRadiusValue = sunRadiusAnimator.animatedValue as Float
        val expandingRadius = sunRadius + (sunRadius * animatedRadiusValue)

        val animatedAlphaValue = sunAlphaAnimator.animatedValue as Int
        val expandingCirclePaint = Paint().apply {
            color = Color.argb(animatedAlphaValue, 255, 255, 0)
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        canvas.drawCircle(sunCenterX, sunCenterY, expandingRadius, expandingCirclePaint)
    }

    private fun drawChimney(canvas: Canvas) {
        val chimneyLeft = width * 0.62f
        val chimneyTop = height * 0.38f
        val chimneyRight = width * 0.68f
        val chimneyBottom = height * 0.48f
        canvas.drawRect(chimneyLeft, chimneyBottom, chimneyRight, chimneyTop, chimneyPaint)
    }

    private fun drawRoof(canvas: Canvas) {
        val roofPath = android.graphics.Path().apply {
            moveTo(width * 0.20f, height * 0.55f)
            lineTo(width * 0.80f, height * 0.55f)
            lineTo(width * 0.50f, height * 0.35f)
            close()
        }
        canvas.drawPath(roofPath, roofPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isTouchInsideSun(event.x, event.y)) {
                    isMovingSun = true
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (isMovingSun) {
                    sunCenterX = event.x
                    sunCenterY = event.y
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isMovingSun = false
            }
        }
        return true
    }

    private fun isTouchInsideSun(x: Float, y: Float): Boolean {
        val dx = x - sunCenterX
        val dy = y - sunCenterY
        val distance = sqrt((dx * dx + dy * dy).toDouble())
        return distance <= sunRadius
    }
}

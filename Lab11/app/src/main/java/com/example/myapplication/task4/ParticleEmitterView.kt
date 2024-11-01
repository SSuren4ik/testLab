package com.example.myapplication.task4

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.*

class ParticleEmitterView(
    context: Context, attrs: AttributeSet? = null,
) : View(context, attrs) {

    private var particleSystem: ParticleSystem? = null
    private var emitterX: Float = 0f
    private var emitterY: Float = 0f
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    init {
        post {
            emitterX = width / 2f
            emitterY = height.toFloat() - 100f
            startParticleAnimation()
        }
    }

    private fun startParticleAnimation() {
        particleSystem = ParticleSystem(100, emitterX, emitterY, height.toFloat())

        scope.launch {
            while (true) {
                val deltaTime = 1f / 60f
                particleSystem?.update(deltaTime)
                invalidate()
                delay(30)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        particleSystem?.draw(canvas)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope.cancel()
    }
}

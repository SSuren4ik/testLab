package com.example.myapplication.task4

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.random.Random

class ParticleSystem(
    particleCount: Int,
    emitterX: Float,
    emitterY: Float,
    private val height: Float
) {
    private val particles = mutableListOf<Particle>()
    private val paint = Paint().apply {
        color = Color.WHITE
    }

    init {
        for (i in 0 until particleCount) {
            val velocityX = Random.nextFloat() * 1.5f - 1
            val velocityY = Random.nextFloat() * -20f
            val particle = Particle(emitterX, emitterY, velocityX, velocityY, 1f)
            particles.add(particle)
        }
    }

    fun update(deltaTime: Float) {
        particles.forEach {
            it.velocityY += 9.81f * deltaTime

            it.x += it.velocityX * deltaTime * 100
            it.y += it.velocityY * deltaTime * 100

            it.alpha -= deltaTime * 0.05f
        }

        particles.removeAll { it.alpha <= 0f || it.y > height + 100 }
    }

    fun draw(canvas: Canvas) {
        particles.forEach {
            paint.alpha = (it.alpha * 255).toInt()
            canvas.drawCircle(it.x, it.y, 10f, paint)
        }
    }
}

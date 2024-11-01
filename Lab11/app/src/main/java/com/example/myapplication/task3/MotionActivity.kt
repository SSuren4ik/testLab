package com.example.myapplication.task3

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import com.example.myapplication.databinding.ActivityMotionBinding
import com.example.myapplication.task4.ParticleGeneratorActivity

class MotionActivity : ComponentActivity() {

    private lateinit var binding: ActivityMotionBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextTask.setOnClickListener {
            startActivity(Intent(this, ParticleGeneratorActivity::class.java))
        }

        binding.main.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val x = motionEvent.x
                    val y = motionEvent.y

                    val circleView = CircleView(this).apply {
                        alpha = 1f
                        setCenter(x, y)
                    }

                    binding.main.addView(circleView)

                    startCircleAnimation(circleView)
                    true
                }

                else -> false
            }
        }
    }

    private fun startCircleAnimation(circleView: CircleView) {
        val radiusAnimator = ValueAnimator.ofFloat(0f, 400f).apply {
            duration = 2000
            addUpdateListener { animation ->
                val radius = animation.animatedValue as Float
                circleView.setRadius(radius)
            }
        }
        val fadeOutAnimator = ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 2000
            addUpdateListener { alpha ->
                val newAlpha = alpha.animatedValue as Float
                circleView.alpha = newAlpha
            }
        }

        AnimatorSet().apply {
            playTogether(radiusAnimator, fadeOutAnimator)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.main.removeView(circleView)
                }
            })
            start()
        }
    }


}

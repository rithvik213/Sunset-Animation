package com.bignerdranch.android.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bignerdranch.android.sunset.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    private var sunHasSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scene.setOnClickListener {
            if (sunHasSet) {
                startSunriseAnimation()
            } else {
                startSunsetAnimation()
            }
            sunHasSet = !sunHasSet
        }
    }

    private fun startSunsetAnimation() {
        val sunYStart = binding.sun.top.toFloat()
        val sunYEnd = binding.sky.height.toFloat()

        val heightAnimator = ObjectAnimator.ofFloat(binding.sun, "y", sunYStart, sunYEnd).apply {
            duration = 3000
            interpolator = AccelerateInterpolator()
        }

        val sunsetSkyAnimator = ObjectAnimator.ofInt(binding.sky, "backgroundColor", blueSkyColor, sunsetSkyColor).apply {
            duration = 3000
            setEvaluator(ArgbEvaluator())
        }

        val nightSkyAnimator = ObjectAnimator.ofInt(binding.sky, "backgroundColor", sunsetSkyColor, nightSkyColor).apply {
            duration = 1500
            setEvaluator(ArgbEvaluator())
        }

        AnimatorSet().apply {
            play(heightAnimator).with(sunsetSkyAnimator).before(nightSkyAnimator)
            start()
        }
    }

    private fun startSunriseAnimation() {
        val sunYEnd = binding.sun.top.toFloat()
        val sunYStart = binding.sky.height.toFloat()

        val heightAnimator = ObjectAnimator.ofFloat(binding.sun, "y", sunYStart, sunYEnd).apply {
            duration = 3000
            interpolator = AccelerateInterpolator()
        }

        val sunriseSkyAnimator = ObjectAnimator.ofInt(binding.sky, "backgroundColor", nightSkyColor, sunsetSkyColor).apply {
            duration = 1500
            setEvaluator(ArgbEvaluator())
        }

        val daySkyAnimator = ObjectAnimator.ofInt(binding.sky, "backgroundColor", sunsetSkyColor, blueSkyColor).apply {
            duration = 3000
            setEvaluator(ArgbEvaluator())
        }

        AnimatorSet().apply {
            play(heightAnimator).with(daySkyAnimator).after(sunriseSkyAnimator)
            start()
        }
    }

}

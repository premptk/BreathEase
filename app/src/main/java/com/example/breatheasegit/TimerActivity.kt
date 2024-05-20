package com.example.breatheasegit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class TimerActivity : MainActivity() {
    private var inhaleTime: Int = 0
    private var holdTime: Int = 0
    private var exhaleTime: Int = 0

    private var isInhaling: Boolean = false
    private var isHolding: Boolean = false
    private var isExhaling: Boolean = false

    private var isRunning: Boolean = false
    private var isPaused: Boolean = false
    private var handler: Handler = Handler(Looper.getMainLooper())

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var action_text: TextView
    private var i = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        inhaleTime = intent.getIntExtra("inhaleTime", 7)
        holdTime = intent.getIntExtra("holdTime", 5)
        exhaleTime = intent.getIntExtra("exhaleTime", 7)

        setContentView(R.layout.breathing_page)
        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_text)
        action_text = findViewById(R.id.action_text)

        val startBtn: Button = findViewById(R.id.startBtn)

        val updateProgress = object : Runnable {
            override fun run() {
                if (!isPaused && isRunning) {
                    when {
                        isInhaling -> {
                            if (i <= inhaleTime) {
                                progressText.text = "$i"
                                action_text.text = "Inhale"
                                progressBar.progress = ((i.toFloat() / inhaleTime) * 100).toInt()
                                i++
                                handler.postDelayed(this, 1000)
                            } else {
                                // Transition to holding
                                isInhaling = false
                                isHolding = true
                                i = 1
                                handler.postDelayed(this, 100)
                            }
                        }
                        isHolding -> {
                            if (i <= holdTime) {
                                progressText.text = "$i"
                                action_text.text = "Hold"
                                progressBar.progress = ((i.toFloat() / holdTime) * 100).toInt()
                                i++
                                handler.postDelayed(this, 1000)
                            } else {
                                // Transition to exhaling
                                isHolding = false
                                isExhaling = true
                                i = 1
                                handler.postDelayed(this, 100)
                            }
                        }
                        isExhaling -> {
                            if (i <= exhaleTime) {
                                progressText.text = "$i"
                                action_text.text = "Exhale"
                                progressBar.progress = ((i.toFloat() / exhaleTime) * 100).toInt()
                                i++
                                handler.postDelayed(this, 1000)
                            } else {
                                // Reset the cycle or stop
                                isExhaling = false
                                isRunning = false
                                startBtn.text = "Start"
                                action_text.text = ""
                                i = 1
                                progressText.text = "Click on Start"
                                progressBar.progress = 0
                            }
                        }
                    }
                }
            }
        }

        startBtn.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                isPaused = false
                isInhaling = true
                startBtn.text = "Pause"
                handler.postDelayed(updateProgress, 100)
            } else if (isPaused) {
                // Resume the progress
                isPaused = false
                startBtn.text = "Pause"
                handler.postDelayed(updateProgress, 100)
            } else {
                isPaused = true
                startBtn.text = "Resume"
                handler.removeCallbacks(updateProgress)
            }
        }

        val stopBtn: Button = findViewById(R.id.stopBtn)
        stopBtn.setOnClickListener {
            isRunning = false
            isInhaling = false
            isHolding = false
            isExhaling = false
            startBtn.text = "Start"
            i = 1
            progressText.text = "Click on Start"
            progressBar.progress = 0
            handler.removeCallbacks(updateProgress)
        }
    }
}

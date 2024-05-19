package com.example.breatheasegit

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView

class TimerActivity : MainActivity() {

    private var isRunning: Boolean = false
    private var isPaused: Boolean = false
    private var handler: Handler = Handler(Looper.getMainLooper())

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private var i = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.breathing_page)
        progressBar = findViewById(R.id.progress_bar)
        progressText = findViewById(R.id.progress_text)

        val startBtn: Button = findViewById(R.id.startBtn)

        val updateProgress = object : Runnable {
            override fun run() {
                if (!isPaused && isRunning) {
                    if (i <= 10) {
                        progressText.text = "$i"
                        progressBar.progress = i
                        i++
                        handler.postDelayed(this, 1000)
                    } else {
                        handler.removeCallbacks(this)
                        isRunning = false
                    }
                }
            }
        }

        startBtn.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                isPaused = false
                startBtn.text = "Pause"
                handler.postDelayed(updateProgress, 1000)
            } else if (isPaused) {
                // Resume the progress
                isPaused = false
                startBtn.text = "Pause"
                handler.postDelayed(updateProgress, 1000)
            } else {
                isPaused = true
                startBtn.text = "Resume"
                handler.removeCallbacks(updateProgress)
            }
        }

        val stopBtn: Button = findViewById(R.id.stopBtn)
        stopBtn.setOnClickListener {
            isRunning = false
            startBtn.text = "Start"
            i = 1
            progressText.text = "Click on Start"
            progressBar.progress = i
        }

    }
}
package com.example.breatheasegit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

open class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_layout)

        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)

        button1.setOnClickListener {
            val intent = Intent(applicationContext, TimerActivity::class.java)
            intent.putExtra("inhaleTime", 7)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(applicationContext, TimerActivity::class.java)
            intent.putExtra("inhaleTime", 5)
            startActivity(intent)
        }

    }
}

package com.example.lab03

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView

/** 使用单调时钟；旋转时保存起始时间，重建后继续以同一时间基准计算。 */
class StopwatchActivity : Activity() {
    private lateinit var timeText: TextView
    private val handler = Handler(Looper.getMainLooper())
    private var elapsedBeforeRun = 0L
    private var startedAt = 0L
    private var running = false
    private val ticker = object : Runnable {
        override fun run() {
            showTime()
            if (running) handler.postDelayed(this, 100L)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)
        applySafeInsets()
        timeText = findViewById(R.id.timeText)

        if (savedInstanceState != null) {
            elapsedBeforeRun = savedInstanceState.getLong(getString(R.string.state_key_elapsed))
            startedAt = savedInstanceState.getLong(getString(R.string.state_key_started))
            running = savedInstanceState.getBoolean(getString(R.string.state_key_running))
        }
        showTime()

        findViewById<Button>(R.id.startButton).setOnClickListener {
            if (!running) {
                startedAt = SystemClock.elapsedRealtime()
                running = true
                scheduleTick()
            }
        }
        findViewById<Button>(R.id.pauseButton).setOnClickListener {
            if (running) {
                elapsedBeforeRun += SystemClock.elapsedRealtime() - startedAt
                running = false
                handler.removeCallbacks(ticker)
                showTime()
            }
        }
        findViewById<Button>(R.id.resetButton).setOnClickListener {
            running = false
            elapsedBeforeRun = 0L
            startedAt = 0L
            handler.removeCallbacks(ticker)
            showTime()
        }
        findViewById<Button>(R.id.backButton).setOnClickListener { finish() }
    }

    override fun onStart() {
        super.onStart()
        if (running) scheduleTick()
    }

    override fun onStop() {
        handler.removeCallbacks(ticker)
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(getString(R.string.state_key_elapsed), elapsedBeforeRun)
        outState.putLong(getString(R.string.state_key_started), startedAt)
        outState.putBoolean(getString(R.string.state_key_running), running)
    }

    private fun scheduleTick() {
        handler.removeCallbacks(ticker)
        handler.post(ticker)
    }

    private fun showTime() {
        val elapsed = elapsedBeforeRun +
            if (running) SystemClock.elapsedRealtime() - startedAt else 0L
        val minutes = elapsed / 60_000L
        val seconds = (elapsed / 1_000L) % 60L
        val tenths = (elapsed / 100L) % 10L
        timeText.text = getString(R.string.stopwatch_time_format, minutes, seconds, tenths)
    }
}

package com.example.lab03

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ForwardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forward)
        applySafeInsets()

        val message = intent.getStringExtra(getString(R.string.intent_key_forward)).orEmpty()
        findViewById<TextView>(R.id.receivedText).text =
            getString(R.string.forward_display_format, message)
        findViewById<Button>(R.id.backButton).setOnClickListener { finish() }
    }
}

package com.example.lab03

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ReturnActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return)
        applySafeInsets()

        val input = findViewById<EditText>(R.id.returnInput)
        findViewById<Button>(R.id.confirmButton).setOnClickListener {
            val value = input.text.toString().trim()
            if (value.isEmpty()) {
                Toast.makeText(this, R.string.return_empty_error, Toast.LENGTH_SHORT).show()
            } else {
                val result = Intent().putExtra(getString(R.string.intent_key_return), value)
                setResult(RESULT_OK, result)
                finish()
            }
        }
        findViewById<Button>(R.id.backButton).setOnClickListener { finish() }
    }
}

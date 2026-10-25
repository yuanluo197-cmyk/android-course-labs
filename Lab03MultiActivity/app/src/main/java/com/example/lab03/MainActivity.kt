package com.example.lab03

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

/** 四个按钮分别演示显式跳转、返回结果、隐式相机及秒表。 */
class MainActivity : Activity() {
    private var returnedValue: String? = null
    private lateinit var returnedText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applySafeInsets()

        returnedText = findViewById(R.id.returnedText)
        returnedValue = savedInstanceState?.getString(getString(R.string.state_key_return))
        showReturnedValue()

        findViewById<Button>(R.id.forwardButton).setOnClickListener {
            val intent = Intent(this, ForwardActivity::class.java)
            intent.putExtra(getString(R.string.intent_key_forward), getString(R.string.forward_sample))
            startActivity(intent)
        }
        findViewById<Button>(R.id.returnButton).setOnClickListener {
            @Suppress("DEPRECATION")
            startActivityForResult(Intent(this, ReturnActivity::class.java), RETURN_REQUEST)
        }
        findViewById<Button>(R.id.cameraButton).setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
        findViewById<Button>(R.id.stopwatchButton).setOnClickListener {
            startActivity(Intent(this, StopwatchActivity::class.java))
        }
    }

    @Deprecated("The platform callback matches the course's result-passing example")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RETURN_REQUEST && resultCode == RESULT_OK) {
            returnedValue = data?.getStringExtra(getString(R.string.intent_key_return))
            showReturnedValue()
        }
    }

    private fun showReturnedValue() {
        returnedText.text = returnedValue?.let { getString(R.string.return_result_format, it) }
            ?: getString(R.string.return_placeholder)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        returnedValue?.let { outState.putString(getString(R.string.state_key_return), it) }
    }

    companion object {
        private const val RETURN_REQUEST = 1
    }
}

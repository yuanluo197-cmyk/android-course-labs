package com.example.lab03

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets

/** 保留布局本身的内边距，再加上状态栏与导航栏占用的空间。 */
fun Activity.applySafeInsets() {
    val root = findViewById<View>(R.id.root)
    val left = root.paddingLeft
    val top = root.paddingTop
    val right = root.paddingRight
    val bottom = root.paddingBottom
    root.setOnApplyWindowInsetsListener { view, insets ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bars = insets.getInsets(WindowInsets.Type.systemBars())
            view.setPadding(left + bars.left, top + bars.top, right + bars.right, bottom + bars.bottom)
        } else {
            @Suppress("DEPRECATION")
            view.setPadding(
                left + insets.systemWindowInsetLeft,
                top + insets.systemWindowInsetTop,
                right + insets.systemWindowInsetRight,
                bottom + insets.systemWindowInsetBottom
            )
        }
        insets
    }
    root.requestApplyInsets()
}

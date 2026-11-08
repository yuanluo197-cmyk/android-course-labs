package com.example.lab04
import android.app.Activity
import android.view.View
fun Activity.applyInsets(root: View) {
 root.setOnApplyWindowInsetsListener { v, insets -> v.setPadding(insets.systemWindowInsetLeft,insets.systemWindowInsetTop,insets.systemWindowInsetRight,insets.systemWindowInsetBottom); insets }
 root.requestApplyInsets()
}

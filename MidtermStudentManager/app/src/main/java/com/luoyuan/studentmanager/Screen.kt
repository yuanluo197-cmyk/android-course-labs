package com.luoyuan.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.WindowInsets

const val EXTRA_STUDENT = "extra_student"
const val EXTRA_EXISTING_IDS = "existing_student_ids"
const val STATE_STUDENTS = "student_list"

fun Activity.applySystemInsets(root: View) {
    root.setOnApplyWindowInsetsListener { view, insets ->
        if (Build.VERSION.SDK_INT >= 30) {
            val bars = insets.getInsets(WindowInsets.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
        } else {
            @Suppress("DEPRECATION")
            view.setPadding(insets.systemWindowInsetLeft, insets.systemWindowInsetTop,
                insets.systemWindowInsetRight, insets.systemWindowInsetBottom)
        }
        insets
    }
    root.requestApplyInsets()
}

fun Intent.readStudent(): Student? = if (Build.VERSION.SDK_INT >= 33) {
    getSerializableExtra(EXTRA_STUDENT, Student::class.java)
} else {
    @Suppress("DEPRECATION")
    (getSerializableExtra(EXTRA_STUDENT) as? Student)
}

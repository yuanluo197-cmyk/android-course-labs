package com.luoyuan.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.luoyuan.studentmanager.databinding.ActivityAddStudentBinding

class AddStudentActivity : Activity() {
    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applySystemInsets(binding.root)
        setResult(Activity.RESULT_CANCELED)

        binding.cancel.setOnClickListener { finish() }
        binding.confirm.setOnClickListener { confirmStudent() }
    }

    private fun confirmStudent() {
        val gender = when (binding.gender.checkedRadioButtonId) {
            R.id.male -> 1
            R.id.female -> 2
            else -> 0
        }
        val student = Student(
            binding.studentId.text.toString().trim(),
            binding.studentName.text.toString().trim(),
            gender,
            binding.className.text.toString().trim()
        )
        val existingIds = intent.getStringArrayListExtra(EXTRA_EXISTING_IDS).orEmpty()
        val error = student.validate(existingIds)
        if (error != null) {
            binding.error.setText(when (error) {
                StudentError.EMPTY_ID -> R.string.empty_id
                StudentError.INVALID_ID -> R.string.invalid_id
                StudentError.EMPTY_NAME -> R.string.empty_name
                StudentError.MISSING_GENDER -> R.string.missing_gender
                StudentError.EMPTY_CLASS -> R.string.empty_class
                StudentError.DUPLICATE_ID -> R.string.duplicate_id
            })
            return
        }
        setResult(Activity.RESULT_OK, Intent().putExtra(EXTRA_STUDENT, student))
        finish()
    }
}

package com.luoyuan.studentmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.luoyuan.studentmanager.databinding.ActivityMainBinding

class MainActivity : Activity() {
    companion object {
        private const val REQUEST_ADD_STUDENT = 1001
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StudentAdapter
    private val students = arrayListOf<Student>()

    @Suppress("DEPRECATION", "OVERRIDE_DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ADD_STUDENT && resultCode == RESULT_OK) {
            val student = data?.readStudent()
            if (student != null && student.validate(students.map { it.studentId }) == null) {
                students.add(student)
                refreshList()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applySystemInsets(binding.root)
        title = getString(R.string.app_name)

        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        val restored = savedInstanceState?.getSerializable(STATE_STUDENTS) as? ArrayList<Student>
        students.addAll(restored ?: listOf(Student(
            getString(R.string.default_id), getString(R.string.default_name),
            0, getString(R.string.default_class)
        )))
        adapter = StudentAdapter(students)
        binding.students.adapter = adapter
        binding.addStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
                .putStringArrayListExtra(EXTRA_EXISTING_IDS,
                    ArrayList(students.map { it.studentId }))
            @Suppress("DEPRECATION")
            startActivityForResult(intent, REQUEST_ADD_STUDENT)
        }
        refreshList()
    }

    private fun refreshList() {
        adapter.notifyDataSetChanged()
        binding.count.text = getString(R.string.count_format, students.size)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(STATE_STUDENTS, students)
        super.onSaveInstanceState(outState)
    }
}

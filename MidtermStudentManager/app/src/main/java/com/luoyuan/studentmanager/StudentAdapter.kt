package com.luoyuan.studentmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.luoyuan.studentmanager.databinding.StudentRowBinding

class StudentAdapter(private val students: List<Student>) : BaseAdapter() {
    override fun getCount(): Int = students.size
    override fun getItem(position: Int): Student = students[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = if (convertView == null) {
            StudentRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                .also { it.root.tag = it }
        } else {
            convertView.tag as StudentRowBinding
        }
        val student = getItem(position)
        val context = parent.context
        val gender = context.getString(when (student.gender) {
            1 -> R.string.male
            2 -> R.string.female
            else -> R.string.unknown_gender
        })
        binding.name.text = context.getString(R.string.row_title, student.name)
        binding.detail.text = context.getString(R.string.row_detail,
            student.studentId, gender, student.className)
        return binding.root
    }
}

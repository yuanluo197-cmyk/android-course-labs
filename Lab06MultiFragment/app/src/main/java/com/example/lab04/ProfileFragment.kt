package com.example.lab04

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lab04.databinding.ActivityEditProfileBinding
import java.io.IOException

class ProfileFragment : Fragment(R.layout.activity_edit_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = ActivityEditProfileBinding.bind(view)
        val app = requireActivity().application as PlayerApp
        val hobbies = listOf(binding.travel, binding.reading, binding.gaming, binding.shopping)
        fun show() {
            val player = app.player
            binding.name.setText(player.name)
            binding.student.setText(player.student)
            binding.phone.setText(player.phone)
            binding.email.setText(player.email)
            binding.gender.clearCheck()
            when (player.gender) {
                1 -> binding.male.isChecked = true
                2 -> binding.female.isChecked = true
            }
            hobbies.forEachIndexed { index, box -> box.isChecked = index in player.hobbies }
            binding.best.text = getString(R.string.best_format, app.player.best)
        }
        show()
        binding.cancel.setOnClickListener { show() }
        binding.save.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val student = binding.student.text.toString().trim()
            val phone = binding.phone.text.toString().trim()
            val email = binding.email.text.toString().trim()
            if (name.isBlank() || student.isBlank() || phone.any { !it.isDigit() } ||
                (email.isNotEmpty() && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                Toast.makeText(requireContext(), R.string.invalid, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val gender = when { binding.male.isChecked -> 1; binding.female.isChecked -> 2; else -> 0 }
            val updated = app.player.copy(name = name, student = student, phone = phone, email = email,
                gender = gender, hobbies = hobbies.indices.filterTo(ArrayList()) { hobbies[it].isChecked })
            try {
                app.store.save(PlayerApp.PROFILE_FILE, updated)
                app.player = updated
                view.clearFocus()
                Toast.makeText(requireContext(), R.string.saved, Toast.LENGTH_SHORT).show()
            } catch (_: IOException) {
                Toast.makeText(requireContext(), R.string.save_failed, Toast.LENGTH_LONG).show()
            }
        }
    }
}

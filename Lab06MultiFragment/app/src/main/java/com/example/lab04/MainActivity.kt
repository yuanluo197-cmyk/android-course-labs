package com.example.lab04

import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.lab04.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applyInsets(binding.root)
        val tabs = listOf(binding.profileTab, binding.pairTab, binding.tripleTab)
        fun show(tab: Button, page: Fragment) {
            val tag = tab.id.toString()
            if (supportFragmentManager.findFragmentById(R.id.content)?.tag != tag) {
                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.content, page, tag)
                    .commitNow()
            }
            tabs.forEach { it.isEnabled = it !== tab }
        }
        binding.profileTab.setOnClickListener { show(binding.profileTab, ProfileFragment()) }
        binding.pairTab.setOnClickListener { show(binding.pairTab, PairGameFragment()) }
        binding.tripleTab.setOnClickListener { show(binding.tripleTab, TripleGameFragment()) }
        if (state == null) show(binding.profileTab, ProfileFragment())
        else tabs.forEach {
            it.isEnabled = it.id.toString() != supportFragmentManager.findFragmentById(R.id.content)?.tag
        }
    }
}

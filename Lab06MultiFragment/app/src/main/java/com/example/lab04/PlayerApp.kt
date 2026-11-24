package com.example.lab04

import android.app.Application
import java.io.Serializable

class PlayerApp : Application() {
    val store by lazy { StateStore(this) }
    lateinit var player: Player


    override fun onCreate() {
        super.onCreate()
        player = store.load(PROFILE_FILE, Player::class.java) {
            Player(getString(R.string.default_name), getString(R.string.default_student))
        }
    }
    companion object {
        const val PROFILE_FILE = "profile.dat"
    }
}

data class Player(
    val name: String,
    val student: String,
    val phone: String = "",
    val email: String = "",
    val gender: Int = 0,
    val hobbies: List<Int> = emptyList(),
    val best: Int = 0
) : Serializable {
    companion object { private const val serialVersionUID = 1L }
}

package com.example.lab04
import android.app.Application
class PlayerApp : Application() {
 lateinit var player: Player
 override fun onCreate() { super.onCreate(); player = Player(getString(R.string.default_name),getString(R.string.default_student)) }
}
data class Player(var name:String, var student:String, var phone:String="", var email:String="", var gender:Int=0, var hobbies:List<Int> = emptyList(), var best:Int=0)

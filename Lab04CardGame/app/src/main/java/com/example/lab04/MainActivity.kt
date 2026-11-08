package com.example.lab04
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import com.example.lab04.databinding.ActivityMainBinding
class MainActivity : Activity() {
 private lateinit var binding:ActivityMainBinding
 override fun onCreate(state:Bundle?) {
  super.onCreate(state); binding=ActivityMainBinding.inflate(layoutInflater);setContentView(binding.root);applyInsets(binding.root)
  binding.edit.setOnClickListener { startActivity(Intent(this,EditProfileActivity::class.java)) }
  binding.play.setOnClickListener { startActivity(Intent(this,GameActivity::class.java)) }
 }
 override fun onResume() {
  super.onResume(); val p=(application as PlayerApp).player
  fun value(s:String)=s.ifBlank { getString(R.string.empty) }
  binding.profile.text=getString(R.string.profile_format,p.name,getString(when(p.gender){1->R.string.male;2->R.string.female;else->R.string.other}),p.student,value(p.phone),value(p.email),value(p.hobbies.joinToString(getString(R.string.separator)){getString(it)}),p.best)
 }
}

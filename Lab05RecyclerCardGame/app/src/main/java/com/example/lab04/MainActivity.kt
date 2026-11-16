package com.example.lab04
import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import com.example.lab04.databinding.ActivityMainBinding
class MainActivity : Activity() {
 override fun onCreateOptionsMenu(menu:Menu):Boolean { menuInflater.inflate(R.menu.main_menu,menu); return true }
 override fun onOptionsItemSelected(item:MenuItem):Boolean {
  if(item.itemId==R.id.about){ AlertDialog.Builder(this).setTitle(R.string.about_title).setMessage(R.string.about_message).setPositiveButton(R.string.ok,null).show(); return true }
  return super.onOptionsItemSelected(item)
 }
 private lateinit var binding:ActivityMainBinding
 override fun onCreate(state:Bundle?) {
  super.onCreate(state); binding=ActivityMainBinding.inflate(layoutInflater);setContentView(binding.root);applyInsets(binding.root)
  binding.edit.setOnClickListener { startActivity(Intent(this,EditProfileActivity::class.java)) }
  binding.play.setOnClickListener { startActivity(Intent(this,GameActivity::class.java)) }
  binding.menuButton.setOnClickListener { view ->
   PopupMenu(this, view).apply {
    menuInflater.inflate(R.menu.main_menu, menu)
    setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
    show()
   }
  }
 }
 override fun onResume() {
  super.onResume(); val p=(application as PlayerApp).player
  fun value(s:String)=s.ifBlank { getString(R.string.empty) }
  binding.profile.text=getString(R.string.profile_format,p.name,getString(when(p.gender){1->R.string.male;2->R.string.female;else->R.string.other}),p.student,value(p.phone),value(p.email),value(p.hobbies.joinToString(getString(R.string.separator)){getString(it)}),p.best)
 }
}

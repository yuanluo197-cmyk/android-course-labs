package com.example.lab04
import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.example.lab04.databinding.ActivityEditProfileBinding
class EditProfileActivity : Activity() {
 private lateinit var b:ActivityEditProfileBinding
 override fun onCreate(state:Bundle?) {
  super.onCreate(state);b=ActivityEditProfileBinding.inflate(layoutInflater);setContentView(b.root);applyInsets(b.root)
  val p=(application as PlayerApp).player
  if(state==null) {
   b.name.setText(p.name);b.student.setText(p.student);b.phone.setText(p.phone);b.email.setText(p.email)
   if(p.gender==1)b.male.isChecked=true else if(p.gender==2)b.female.isChecked=true
   listOf(b.travel to R.string.travel,b.reading to R.string.reading,b.gaming to R.string.gaming,b.shopping to R.string.shopping).forEach{(v,id)->v.isChecked=id in p.hobbies}
  }
  b.cancel.setOnClickListener { finish() }
  b.save.setOnClickListener {
   val name=b.name.text.toString().trim();val student=b.student.text.toString().trim()
   val phone=b.phone.text.toString().trim();val email=b.email.text.toString().trim()
   if(name.isBlank()||student.isBlank()||phone.any{!it.isDigit()}||(email.isNotEmpty()&&!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
    Toast.makeText(this,R.string.invalid,Toast.LENGTH_SHORT).show()
   } else {
    p.name=name;p.student=student;p.phone=phone;p.email=email;p.gender=if(b.male.isChecked)1 else if(b.female.isChecked)2 else 0
    p.hobbies=listOf(b.travel to R.string.travel,b.reading to R.string.reading,b.gaming to R.string.gaming,b.shopping to R.string.shopping).filter{it.first.isChecked}.map{it.second}
    finish()
   }
  }
 }
}

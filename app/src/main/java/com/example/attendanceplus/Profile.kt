package com.example.attendanceplus

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.attendanceplus.Auth.Login
import com.google.android.material.bottomnavigation.BottomNavigationView

class Profile: AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val myaccount = findViewById<ImageView>(R.id.myaccount)
        val imageView14 = findViewById<ImageView>(R.id.imageView14)
        val namefield = findViewById<TextView>(R.id.namefield)
        val sign_out = findViewById<ImageView>(R.id.sign_out)
        val std = findViewById<TextView>(R.id.std)
        val bottom_navigation_view1 = findViewById<BottomNavigationView>(R.id.bottom_navigation_view1)
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        myaccount.setOnClickListener {
            when{
                sharedPreferences.getString("type","")=="teacher" ->{
                    startActivity(Intent(this, Teacher_MyAccount::class.java))
                }
                sharedPreferences.getString("type","")=="student" ->{
                    startActivity(Intent(this, Student_MyAccount::class.java))
                }
            }
        }
        imageView14.setOnClickListener {
            Toast.makeText(applicationContext,"Coming Soon!",Toast.LENGTH_SHORT).show()
        }
        sign_out.setOnClickListener {
            // sharedPreferences.edit().clear().apply()  -- delete acc
            sharedPreferences.edit().putBoolean("login",false).apply()
            sharedPreferences.edit().putBoolean("save-acc",true).apply()
            val intent = Intent(this, Login::class.java)  //Login
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


        namefield.text = sharedPreferences.getString("name","")
        std.text = "Class of Study: ${sharedPreferences.getString("Class","")}"

        bottom_navigation_view1.selectedItemId = R.id.profile
        bottom_navigation_view1.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.home -> {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    this.startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }
}
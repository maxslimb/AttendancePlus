package com.example.attendanceplus

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.attendanceplus.Auth.Login
import com.example.attendanceplus.Auth.Student_SignUp
import com.example.attendanceplus.Auth.Teacher_SignUp

class Launch_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch_screen)
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val a = sharedPreferences.getBoolean("save-acc", false)
        val b = sharedPreferences.getBoolean("login", false)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val teacher = findViewById<Button>(R.id.teacher)
        val student = findViewById<Button>(R.id.student)
        imageView.postDelayed({
            imageView.visibility = View.GONE

            when {
                !a && !b -> {
                    Log.d("log", "a==null")
                    teacher.visibility = View.VISIBLE
                    student.visibility = View.VISIBLE
                    teacher.setOnClickListener {
                        startActivity(Intent(this, Teacher_SignUp::class.java))
                    }
                    student.setOnClickListener {
                        startActivity(Intent(this, Student_SignUp::class.java))
                    }
                }
                a && !b -> {
                    teacher.visibility = View.VISIBLE
                    student.visibility = View.VISIBLE
                    teacher.setOnClickListener {
                        startActivity(Intent(this, Login::class.java))
                    }
                    student.setOnClickListener {
                        startActivity(Intent(this, Login::class.java))
                    }
                }
                sharedPreferences.getString("type", "") == "teacher" -> {
                    Log.d("log", "a==teacher")
                    startActivity(Intent(this, MainActivity::class.java))
                }
                sharedPreferences.getString("type", "") == "student" -> {
                    Log.d("log", "a==student")
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }, 2000)

    }
}
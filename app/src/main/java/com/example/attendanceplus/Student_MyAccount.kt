package com.example.attendanceplus

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class Student_MyAccount : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_my_account)
        val nameteacher = findViewById<EditText>(R.id.nameteacher)
        val emailteacher = findViewById<EditText>(R.id.emailteacher)
        val deptteacher = findViewById<EditText>(R.id.deptteacher)
        val yearteacher = findViewById<EditText>(R.id.yearteacher)
        val divteacher = findViewById<EditText>(R.id.divteacher)


        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        nameteacher.setText(sharedPreferences.getString("name",""))
        emailteacher.setText(sharedPreferences.getString("email",""))
        deptteacher.setText(sharedPreferences.getString("admin-no",""))
        yearteacher.setText(sharedPreferences.getString("Class",""))
        divteacher.setText(sharedPreferences.getString("division",""))
    }
}
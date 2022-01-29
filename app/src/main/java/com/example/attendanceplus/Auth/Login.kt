package com.example.attendanceplus.Auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.attendanceplus.MainActivity
import com.example.attendanceplus.R
import com.google.android.material.textfield.TextInputEditText

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val lbutton = findViewById<Button>(R.id.lbutton)
        val emailteacher = findViewById<TextInputEditText>(R.id.emailteacher)
        val pass1 = findViewById<TextInputEditText>(R.id.pass1)

        lbutton.setOnClickListener {
            if ((emailteacher.text.toString().isNotEmpty()) && (pass1.text.toString().isNotEmpty()))
            {
                if((emailteacher.text.toString() == sharedPreferences.getString("email",""))
                    && (pass1.text.toString() == sharedPreferences.getString("pass",""))){
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                }
                else
                {
                    Toast.makeText(applicationContext,"Wrong Email or Password!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(applicationContext,"Please fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
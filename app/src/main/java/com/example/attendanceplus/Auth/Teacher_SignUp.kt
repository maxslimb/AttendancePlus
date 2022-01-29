package com.example.attendanceplus.Auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.example.attendanceplus.MainActivity
import com.example.attendanceplus.R
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject

class Teacher_SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_sign_up)
        val jsonObject = JSONObject()
        try {
            jsonObject.put("kishan@mes.ac.in", "755")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val submit_userinfo = findViewById<ImageButton>(R.id.submit_userinfo_t)
        val sname = findViewById<TextInputEditText>(R.id.sname_t)
        val semail = findViewById<TextInputEditText>(R.id.semail_t)
        val seco1 = findViewById<TextInputEditText>(R.id.seco1_t)
        val division1 = findViewById<TextInputEditText>(R.id.division1_t)
        val department = findViewById<TextInputEditText>(R.id.department_t)
        val year = findViewById<TextInputEditText>(R.id.year_t)
        val pass1 = findViewById<TextInputEditText>(R.id.pass1_t)
        val repass1 = findViewById<TextInputEditText>(R.id.repass1_t)
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        submit_userinfo.setOnClickListener{
            if ((sname.text.toString().isNotEmpty()) && (semail.text.toString().isNotEmpty()) &&
                (department.text.toString().isNotEmpty()) && (year.text.toString().isNotEmpty()) &&
                (division1.text.toString().isNotEmpty()) && (seco1.text.toString().isNotEmpty()) &&
                (pass1.text.toString().isNotEmpty()) && (repass1.text.toString().isNotEmpty())
            ) {
                Log.e("value json ", jsonObject.optString(semail.text.toString()))

                if ((pass1.text.toString() == repass1.text.toString()) && (jsonObject.optString(semail.text.toString())!="") && (jsonObject.optString(semail.text.toString()) == seco1.text.toString())) {
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("name", sname.text.toString())
                    editor.putString("email", semail.text.toString())
                    editor.putString("department", department.text.toString())
                    editor.putString("year", year.text.toString())
                    editor.putString("division", division1.text.toString())
                    editor.putString("secret-code", seco1.text.toString())
                    editor.putString("pass", pass1.text.toString())
                    editor.putString("type", "teacher")
                    editor.commit()
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(applicationContext, "Welcome to Attendance Plus!", Toast.LENGTH_SHORT).show()
                } else if (jsonObject.optString(semail.text.toString()) != seco1.text.toString()) {
                    Toast.makeText(applicationContext, "Secret Code or Email is not valid!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Password does not match!", Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(applicationContext,"Please fill All Fields", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
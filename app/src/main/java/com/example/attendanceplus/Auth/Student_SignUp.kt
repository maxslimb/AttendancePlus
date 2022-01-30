package com.example.attendanceplus.Auth

import android.annotation.SuppressLint
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

class Student_SignUp : AppCompatActivity() {
    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_sign_up)
        val submit_userinfo = findViewById<ImageButton>(R.id.submit_userinfo)
        val sname = findViewById<TextInputEditText>(R.id.sname)
        val semail = findViewById<TextInputEditText>(R.id.semail)
        val rollno = findViewById<TextInputEditText>(R.id.roll_no)
        val seco1 = findViewById<TextInputEditText>(R.id.seco1)
        val iname = findViewById<TextInputEditText>(R.id.iname)
        val division1 = findViewById<TextInputEditText>(R.id.division1)
        val pass1 = findViewById<TextInputEditText>(R.id.pass1)
        val repass1 = findViewById<TextInputEditText>(R.id.repass1)

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val jsonObject = JSONObject()
        try {
            jsonObject.put("2018HE0269", "755")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        submit_userinfo.setOnClickListener{
            if ((sname.text.toString().isNotEmpty()) && (semail.text.toString().isNotEmpty()) &&
                (rollno.text.toString().isNotEmpty()) && (iname.text.toString().isNotEmpty()) &&
                (division1.text.toString().isNotEmpty()) && (seco1.text.toString().isNotEmpty()) &&
                (pass1.text.toString().isNotEmpty()) && (repass1.text.toString().isNotEmpty())
            ) {
                if ((pass1.text.toString() == repass1.text.toString())&&seco1.text.toString() == "111"){   //&&(jsonObject.optString(rollno.text.toString().toUpperCase())==seco1.text.toString())
                    val editor: SharedPreferences.Editor= sharedPreferences.edit()
                    editor.putString("name", sname.text.toString())
                    editor.putString("email", semail.text.toString())
                    editor.putString("admin-no",rollno.text.toString())
                    editor.putString("Class",iname.text.toString().toUpperCase())
                    editor.putString("division",division1.text.toString())
                    editor.putString("secret-code",seco1.text.toString())
                    editor.putString("pass",pass1.text.toString())
                    editor.putString("type", "student")
                    editor.commit()
                    startActivity(Intent(this,MainActivity::class.java))
                    Toast.makeText(applicationContext,"Welcome to Attendance Plus!",Toast.LENGTH_SHORT).show()
                }
                else if(seco1.text.toString()!="111"){

                    Toast.makeText(applicationContext,"Secret Code is not valid",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(applicationContext,"Password does not match!",Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(applicationContext,"Please fill All Fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
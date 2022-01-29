package com.example.attendanceplus

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView4 = findViewById<TextView>(R.id.textView4)
        val year = findViewById<TextView>(R.id.year)
        val textView = findViewById<TextView>(R.id.textView_n)
        val lbutton1 = findViewById<Button>(R.id.lbutton1)
        val lbutton = findViewById<Button>(R.id.lbutton)
        val cardView = findViewById<CardView>(R.id.cardView)
        val tcard = findViewById<ConstraintLayout>(R.id.tcard)
        val bottom_navigation_view = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("login",true).apply()
        textView4.text = sharedPreferences.getString("name","")
        year.text = sharedPreferences.getString("Class","")
        "Welcome ${sharedPreferences.getString("name", "")}".also { textView.text = it }
        when{sharedPreferences.getString("type","")=="teacher" -> {
            lbutton1.visibility = View.GONE
            lbutton.visibility = View.VISIBLE
            cardView.visibility = View.GONE
            tcard.visibility = View.VISIBLE
        }
            sharedPreferences.getString("type","")=="student" -> {
                lbutton1.visibility =View.VISIBLE
                lbutton.visibility = View.GONE
                cardView.visibility = View.VISIBLE
                tcard.visibility = View.GONE
            }
        }

        lbutton.setOnClickListener {
            startActivity(Intent(this, Teacher::class.java))
        }
        lbutton1.setOnClickListener {
            startActivity(Intent(this, Student::class.java))
        }

        bottom_navigation_view.selectedItemId = R.id.home
        bottom_navigation_view.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.profile -> {
                    val intent = Intent(applicationContext, Profile::class.java)
                    this.startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }
}
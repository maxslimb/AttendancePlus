package com.example.attendanceplus

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

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
                CheckPermission()
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
            Onlocation()
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

    private fun CheckPermission() {
        Dexter.withContext(applicationContext)
            .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener, MultiplePermissionsListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {

                }

                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }

            })
            .check();
    }



    private fun Onlocation(){

        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_LOW_POWER
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            startActivity(Intent(this, Student::class.java))
            Log.d("Student","task called")
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    val REQUEST_CHECK_SETTINGS =1
                    exception.startResolutionForResult(this@MainActivity,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }

    }
}
package com.example.attendanceplus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceplus.model.attendance
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.nearby.connection.Payload

class Teacher : AppCompatActivity() {
    val data_s = arrayListOf<attendance>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        val stop_attendance_button = findViewById<Button>(R.id.stop_attendance)
        val add_student_button = findViewById<Button>(R.id.add_student)


        stop_attendance_button.setOnClickListener {
            Nearby.getConnectionsClient(applicationContext).stopAdvertising()
            startActivity(Intent(this, MainActivity::class.java))
        }

        add_student_button.setOnClickListener {
            TODO("bottom navigation sheet")
        }

        val recycler = findViewById<RecyclerView>(R.id.recyclerview_attendance)
        recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
        recycler.adapter = AttendanceListAdapter(data_s)

    }

    override fun onStart() {
        super.onStart()
        startAdvertising()
    }

    private fun startAdvertising() {
        val SERVICE_ID = "com.example.attendanceplus"
        val advertisingOptions = AdvertisingOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        Nearby.getConnectionsClient(applicationContext)
            .startAdvertising(
                "teacher_Kishan", SERVICE_ID, connectionLifecycleCallback, advertisingOptions
            )
            .addOnSuccessListener(
                OnSuccessListener { unused: Void? ->

                    Log.d("Teacher","Advertising success")

                })
            .addOnFailureListener(
                OnFailureListener { e: Exception? ->
                    Log.d("Teacher","Advertising fail: ${e.toString()}")
                })
    }

    private val payloadCallback: PayloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {
            data_s.add(attendance("",""))
            val data_recieved = payload.asBytes()?.let { String(it, Charsets.UTF_8) }
            Log.d("Teacher","Data recieved: ${payload.asBytes()?.let { String(it, Charsets.UTF_8) }}")
            Log.d("Teacher","Data recieved - name: ${data_recieved!!.substring(0,
                data_recieved.indexOf(","))}")
            Log.d("Teacher","Data recieved - roll: ${
                data_recieved.substring(
                    data_recieved.indexOf(",")+1)}")

            Toast.makeText(applicationContext,"Data recieved: ${payload.asBytes()?.let { String(it, Charsets.UTF_8) }}",Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext,"Data recieved - name: ${data_recieved!!.substring(0,
                data_recieved.indexOf(","))}",Toast.LENGTH_SHORT).show()
            Toast.makeText(applicationContext,"Data recieved - roll: ${
                data_recieved.substring(
                data_recieved.indexOf(",")+1)}",Toast.LENGTH_SHORT).show()
        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {

        }
    }
    private val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                // Automatically accept the connection on both sides.
               // Toast.makeText(applicationContext,"Name of Students: ${connectionInfo.endpointName}",Toast.LENGTH_SHORT).show()
                Nearby.getConnectionsClient(applicationContext).acceptConnection(endpointId, payloadCallback)
            }

            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                when (result.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> {
                        Nearby.getConnectionsClient(applicationContext).stopAdvertising()

                        Toast.makeText(applicationContext,"EndID of Student: $endpointId",Toast.LENGTH_SHORT).show()
                    }
                    ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED -> {
                    }
                    ConnectionsStatusCodes.STATUS_ERROR -> {
                    }
                    else -> {
                    }
                }
            }

            override fun onDisconnected(endpointId: String) {
                // We've been disconnected from this endpoint. No more data can be
                // sent or received.
            }
        }

    override fun onStop() {
        super.onStop()
        Nearby.getConnectionsClient(applicationContext).stopAdvertising()
        Log.d("Teacher","Advertising stopped")
    }

}
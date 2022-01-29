package com.example.attendanceplus

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.nearby.connection.Payload

class Teacher : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        val ta_button = findViewById<Button>(R.id.tattendance_button)
        ta_button.setOnClickListener {
            startAdvertising()
        }
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

            Toast.makeText(applicationContext,"Data recieved: ${payload.asBytes()?.let { String(it, Charsets.UTF_8) }}",Toast.LENGTH_SHORT).show()

        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {

        }
    }
    private val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                // Automatically accept the connection on both sides.
                Toast.makeText(applicationContext,"Name of Students: ${connectionInfo.endpointName}",Toast.LENGTH_SHORT).show()
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
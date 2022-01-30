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
import com.google.android.gms.nearby.connection.PayloadTransferUpdate

import com.google.android.gms.nearby.connection.Payload

import com.google.android.gms.nearby.connection.PayloadCallback
import java.nio.charset.StandardCharsets.UTF_8


class Student : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        val ta_button = findViewById<Button>(R.id.giveattendance_button)
        ta_button.setOnClickListener {
            startDiscovery()
        }
    }
    private fun startDiscovery() {
        val SERVICE_ID = "com.example.attendanceplus"
        val discoveryOptions = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        Nearby.getConnectionsClient(applicationContext)
            .startDiscovery(SERVICE_ID, endpointDiscoveryCallback, discoveryOptions)
            .addOnSuccessListener { unused: Void? ->
                Log.d("Student","Discovery success")
            }
            .addOnFailureListener { e: Exception? ->
                Log.d("Student","Discovery failed error: ${e.toString()}")
            }
    }

    private val endpointDiscoveryCallback: EndpointDiscoveryCallback =
        object : EndpointDiscoveryCallback() {
            override fun onEndpointFound(endpointId: String, info: DiscoveredEndpointInfo) {
                // An endpoint was found. We request a connection to it.
                Nearby.getConnectionsClient(applicationContext)
                    .requestConnection("teacher_Kishan", endpointId, connectionLifecycleCallback) //change to faulty name- first param
                    .addOnSuccessListener(
                        OnSuccessListener { unused: Void? -> })
                    .addOnFailureListener(
                        OnFailureListener { e: java.lang.Exception? -> })
            }

            override fun onEndpointLost(endpointId: String) {
                // A previously discovered endpoint has gone away.
            }
        }
    private val payloadCallback: PayloadCallback = object : PayloadCallback() {
        override fun onPayloadReceived(endpointId: String, payload: Payload) {

        }

        override fun onPayloadTransferUpdate(endpointId: String, update: PayloadTransferUpdate) {

        }
    }
    private val connectionLifecycleCallback: ConnectionLifecycleCallback =
        object : ConnectionLifecycleCallback() {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
                // Automatically accept the connection on both sides.
                Log.d("Student", "onConnectionInitiated: accepting connection")
                Nearby.getConnectionsClient(applicationContext).acceptConnection(endpointId,payloadCallback)
            }

            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
                when (result.status.statusCode) {
                    ConnectionsStatusCodes.STATUS_OK -> {
                        // Once you have successfully connected to your friends' devices, you can leave
                        // discovery mode so you can stop discovering other devices
                        Nearby.getConnectionsClient(applicationContext).stopDiscovery();
                        // if you were advertising, you can stop as well
                        Toast.makeText(applicationContext,"EndID of Student: $endpointId", Toast.LENGTH_SHORT).show()
                        val data = Payload.fromBytes("Kishan Patel,35".toByteArray())
                        Nearby.getConnectionsClient(applicationContext).sendPayload(endpointId, data)

                      //  friendEndpointId = endpointId

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
        Nearby.getConnectionsClient(applicationContext).stopDiscovery()
        Log.d("Student","Discovery stopped")
    }

}
package com.example.attendanceplus

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.nearby.connection.PayloadTransferUpdate

import com.google.android.gms.nearby.connection.Payload

import com.google.android.gms.nearby.connection.PayloadCallback
import com.google.android.gms.tasks.Task
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener


class Student : AppCompatActivity() {
   lateinit var ProgressBar_Student: ProgressBar
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        ProgressBar_Student = findViewById(R.id.Student_a_progressBar)

    }

    override fun onStart() {
        super.onStart()
        startDiscovery()
    }

    private fun startDiscovery() {
        val SERVICE_ID = "com.example.attendanceplus"
        val discoveryOptions = DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build()
        Nearby.getConnectionsClient(applicationContext)
            .startDiscovery(SERVICE_ID, endpointDiscoveryCallback, discoveryOptions)
            .addOnSuccessListener { unused: Void? ->
                Toast.makeText(applicationContext,"Connecting to teacher",Toast.LENGTH_SHORT).show()
                Log.d("Student","Discovery success")
            }
            .addOnFailureListener { e: Exception? ->
                Toast.makeText(applicationContext,"Error Connecting to teacher: ${e.toString()}",Toast.LENGTH_SHORT).show()
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
            val data_recieved = payload.asBytes()?.let { String(it, Charsets.UTF_8) }
            Toast.makeText(applicationContext,"Attendance $data_recieved",Toast.LENGTH_SHORT).show()
            if(data_recieved=="Success"){
                Nearby.getConnectionsClient(applicationContext).stopDiscovery()
                startActivity(Intent(this@Student, MainActivity::class.java))
            }
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

                        // if you were advertising, you can stop as well
                        Toast.makeText(applicationContext,"EndID of Student: $endpointId", Toast.LENGTH_SHORT).show()
                        val data = Payload.fromBytes("${sharedPreferences.getString("name","")},${sharedPreferences.getString("admin-no","")}".toByteArray())
                        Nearby.getConnectionsClient(applicationContext).sendPayload(endpointId, data)
                        Nearby.getConnectionsClient(applicationContext).stopDiscovery()
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
package com.example.chatclient

import android.util.Log
import java.io.*
import java.util.concurrent.Executor


import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView
import android.widget.Toast

import com.example.chatclient.Controller.Injector
import com.example.chatclient.Model.*
import com.example.chatclient.R.id.responseTextView
import kotlinx.android.synthetic.main.activity_network_connection.*

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.Socket
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ChatClient(private val serverIP: String, private val serverPort: Int)
{

    private var transmit: PrintWriter? = null
    private var receive: BufferedReader? = null
    private var poolForReceiver: ExecutorService? = null
    private var poolForTransmitter : ExecutorService? = null

    fun connectWithServer() {
        poolForTransmitter = Executors.newSingleThreadExecutor()
        poolForReceiver = Executors.newSingleThreadExecutor()

        try {
            val clientSocket = Socket(serverIP, serverPort)
            var receivedData = BufferedReader(InputStreamReader(clientSocket.getInputStream()))

            poolForReceiver!!.execute(Receiver(receivedData))
            receive = receivedData

        } catch (e: UnknownHostException) {
            Log.e("Network Class", "ClientProtocolException : $e")
        } catch (e: IOException) {
            Log.e("Network Class", "IOException : $e")
        }
    }
}
package com.example.chatclient

import android.support.v7.app.AppCompatActivity
import android.app.Activity;
import android.content.Intent
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView
import android.widget.Toast;
import com.example.chatclient.Controller.ChatRoom
import com.example.chatclient.Model.Messages
import com.example.chatclient.Model.Receiver
import com.example.chatclient.Model.Transceiver
import kotlinx.android.synthetic.main.activity_network_connection.*
import kotlinx.android.synthetic.main.activity_network_connection.view.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import java.io.*
import java.net.Socket
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class NetworkConnection : AppCompatActivity() {

    private var mButton: Button? = null
    private var pool: ExecutorService? = null
    private var chatClient: ChatClient? = null
    private var clientSocket = Socket()
    private var transmit: PrintWriter? = null
    private var receive: BufferedReader? = null
    private var poolForReceiver: ExecutorService? = null
    private var poolForTransmitter : ExecutorService? = null
    private val TAG = MainActivity::class.java.name
    private var transceiver : Transceiver? = null
    
    val URL = "192.168.0.3"
    val port = 55551
    var logTextView : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_connection)
        
        connectButton!!.setOnClickListener{
            transceiver = Transceiver.getInstance()
        }
        clearButton.setOnClickListener(registerUser)
    }

    private val registerUser = View.OnClickListener {
        
        val textToSend = addressEditText.text.toString()
        if (transceiver != null) {
            transceiver?.transmit(textToSend)
            Toast.makeText(this,"Thanks! Transceiver Object is created", Toast.LENGTH_LONG).show()
            val dfg = "Server is DEAD!!!!!!!!"
            logTextView = findViewById(R.id.responseTextView) as TextView
            responseTextView.append(dfg)
        } else {
            Toast.makeText(this,"Enter Server address and tap Connect", Toast.LENGTH_LONG).show()
        }
        startActivity(Intent(this, ChatRoom::class.java))
    }


    fun getMyString() : String {
        return "I come from CoRoutine"
    }

    fun connectWithServer() : Socket {
        var socket = Socket()

        try {
            val clientSocket = Socket(URL, port)
            socket = clientSocket
        } catch (e: UnknownHostException) {
            Log.e("Network Class", "ClientProtocolException : $e")
        } catch (e: IOException) {
            Log.e("Network Class", "IOException : $e")
        }finally {
            Log.d("Network","Socket Created")
        }
       return socket
    }

    private val mClickListener = View.OnClickListener {
        Log.d("ClickListener", "Buttom Clicked")
        Log.d("ClickListener", "What i Got from transciever -> ${transceiver?.receive()}")
    }

}
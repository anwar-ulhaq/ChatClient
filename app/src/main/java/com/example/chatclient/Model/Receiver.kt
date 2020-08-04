package com.example.chatclient.Model

import android.util.Log
import com.example.chatclient.MessageRepository
import java.io.BufferedReader
import java.util.*

class Receiver(receivedData: BufferedReader): Runnable {

    val received = receivedData

    private var receivedMessage: String? = null

    override fun run() {
        val serverResponse = Scanner(received)

        val wholeResponse = StringBuilder()

        while ( serverResponse.hasNextLine() )
        {
            val singleLine = serverResponse.nextLine()
            wholeResponse.append(singleLine)
        }
        receivedMessage = wholeResponse.toString()
    }
}
package com.example.chatclient.Model

import android.app.Activity
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.example.chatclient.ConnectionDetails
import com.example.chatclient.MainActivity
import com.example.chatclient.MainActivity.Companion.PREFERENCEID
import com.example.chatclient.MessageRepository
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newSingleThreadContext
import java.io.*
import java.lang.StringBuilder
import java.net.Socket
import java.net.SocketException
import java.net.UnknownHostException
import java.util.*


class Transceiver {

    private var transmitData: PrintWriter? = null
    private var receiveData : Scanner? = null

    fun transmit(userInput : String) {

        launch(UI) {

            launch(newSingleThreadContext("DataTransmitThread")) {
                try {
                    transmitData = PrintWriter(BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream())), true)
                }catch (e: SocketException){
                    Log.e("Transceiver Object", "SocketException $e")
                } finally {
                    //line below can cause null exception,
                    //one from username inout,=> fixed by disabling button
                    //second from message send input button => have to fix it by disabling
                    transmitData!!.println(userInput)
                }
            }
        }
    }

    fun receive() : String {
        var returnedString = ""
        launch(UI) {
            var receivedString = ""
            var messageRepository= MessageRepository.getInstance(MessageDatabase.getInstance().messageDAO)
            async(newSingleThreadContext("DataReceiveThread")) {
                val temp = 23
                receiveData = Scanner(BufferedReader(InputStreamReader(clientSocket.getInputStream())))

                val stringBuilder = StringBuilder()
                var singleLine = ""
                while (receiveData!!.hasNextLine()) {
                    singleLine = ""
                    singleLine = receiveData!!.nextLine()
                    launch (UI){ ServerResponseExtractor?.addResponse(singleLine) }
                }
                receivedString = singleLine

            }.await()
            returnedString = receivedString
        }
        return returnedString
    }

    companion object {

        @Volatile private var instance: Transceiver? = null
        @Volatile private var clientSocket = Socket()

        private val ipOrURL = ConnectionDetails.serverAddress
        private val port = Integer.parseInt(ConnectionDetails.serverPort)

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: Transceiver().also {
                        launch(UI) {
                            var clientSocketFromOtherThread = Socket()
                            async ( newSingleThreadContext("connectionThread")){
                                try {
                                    val clientSocketConnected = Socket(ipOrURL, port)
                                    clientSocketFromOtherThread = clientSocketConnected
                                } catch (e: UnknownHostException) {
                                    Log.e("Transceiver Object", "ClientProtocolException : $e")
                                } catch (e: IOException) {
                                    Log.e("Transceiver Object", "IOException : $e")
                                } finally {
                                    Log.d("Transceiver Object", "Socket Created")
                                }
                                instance = it
                            }.await()
                            clientSocket = clientSocketFromOtherThread
                        }
                    }
                }
    }
}
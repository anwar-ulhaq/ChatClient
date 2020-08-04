package com.example.chatclient

import android.app.Application

class ConnectionDetails : Application() {
    companion object {
        lateinit var serverAddress : String
        lateinit var serverPort : String
    }
}
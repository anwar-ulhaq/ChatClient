package com.example.chatclient

import android.app.Application

//the username will be stored in an App class (com..........App.kt) that will be available for all activities:
class AppUser : Application() {
    companion object {
        lateinit var appUser:String
    }
}
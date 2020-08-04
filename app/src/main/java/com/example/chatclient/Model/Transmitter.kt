package com.example.chatclient.Model

import android.arch.lifecycle.ViewModelProviders
import android.os.Handler
import android.util.Log
import com.example.chatclient.Controller.Injector
import com.example.chatclient.ViewModel.MessageViewModel
import java.io.PrintWriter
import java.util.*

class Transmitter(socketWriter: PrintWriter): Runnable {

    private val out = socketWriter
    //private var mHandler : Handler

    override fun run() {

        do {
            val commandEntered = Scanner(System.`in`)
            val userInput = commandEntered.nextLine()
            out.println(userInput)
        }while (userInput != ":quit")

        println("Should close stream")

    }
}
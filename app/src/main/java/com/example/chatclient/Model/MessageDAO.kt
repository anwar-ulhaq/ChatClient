package com.example.chatclient.Model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.Message
import android.util.Log

class MessageDAO {


    private val testMessagesList = mutableListOf<Messages>()

    private val liveMessages = MutableLiveData<List<Messages>>()

    init {
        liveMessages.value = testMessagesList
    }

    fun addMessage(message: Messages) {
        testMessagesList.add(message)
        liveMessages.value = testMessagesList
    }

    fun getMessage() = liveMessages as LiveData<List<Messages>>
}
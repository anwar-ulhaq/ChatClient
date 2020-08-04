package com.example.chatclient

import com.example.chatclient.Model.MessageDAO
import com.example.chatclient.Model.Messages

class MessageRepository private constructor(private val messageDAO: MessageDAO) {

    fun addMessage(message: Messages) {
        messageDAO.addMessage(message)
    }

    fun getMessage() = messageDAO.getMessage()

    companion object {
        @Volatile private var instance: MessageRepository? = null

        fun getInstance(messageDAO: MessageDAO) =

                instance ?: synchronized(this) {
                    instance ?: MessageRepository(messageDAO).also { instance = it }
                }
    }
}


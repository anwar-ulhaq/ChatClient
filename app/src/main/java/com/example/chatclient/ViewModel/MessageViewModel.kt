package com.example.chatclient.ViewModel

import android.arch.lifecycle.ViewModel
import com.example.chatclient.MessageRepository
import com.example.chatclient.Model.Messages


class MessageViewModel(private val messageRepository: MessageRepository)
    : ViewModel() {
    fun getMessage() = messageRepository.getMessage()
    fun addMessage(message: Messages) = messageRepository.addMessage(message)
}
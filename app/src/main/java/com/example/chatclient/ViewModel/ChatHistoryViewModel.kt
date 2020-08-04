package com.example.chatclient.ViewModel

import android.arch.lifecycle.ViewModel
import com.example.chatclient.Model.ChatHistoryRepository
import com.example.chatclient.Model.ChatUsers
import com.example.chatclient.Model.History

class ChatHistoryViewModel(private val chatHistoryRepository: ChatHistoryRepository) : ViewModel(){

    fun getHistory() = chatHistoryRepository.getHistory()

    fun addHistory (chatHistory: History) = chatHistoryRepository.addHistory(chatHistory)

}

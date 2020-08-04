package com.example.chatclient.ViewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.chatclient.Model.ChatHistoryRepository

class ChatHistoryViewModelFactory (private val chatHistoryRepository: ChatHistoryRepository)
    : ViewModelProvider.NewInstanceFactory() {
    @Suppress ( "UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatHistoryViewModel(chatHistoryRepository) as T
    }

}
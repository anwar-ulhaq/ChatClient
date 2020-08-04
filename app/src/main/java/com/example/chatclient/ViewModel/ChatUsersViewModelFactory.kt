package com.example.chatclient.ViewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.chatclient.Model.ChatUserRepository

class ChatUsersViewModelFactory (private val chatUserRepository: ChatUserRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatUsersViewModel(chatUserRepository) as T
    }
}
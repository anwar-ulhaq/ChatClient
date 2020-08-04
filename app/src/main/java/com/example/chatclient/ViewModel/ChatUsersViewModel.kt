package com.example.chatclient.ViewModel

import android.arch.lifecycle.ViewModel
import com.example.chatclient.Model.ChatUserRepository
import com.example.chatclient.Model.ChatUsers

class ChatUsersViewModel (private val chatUserRepository: ChatUserRepository)
    : ViewModel() {
    // update message from test Message Database
    fun getUsers() = chatUserRepository.getUsers()
    fun addUsers(chatUsers: ChatUsers) = chatUserRepository.addUser(chatUsers)
}
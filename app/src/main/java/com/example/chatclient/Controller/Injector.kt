package com.example.chatclient.Controller

import com.example.chatclient.MessageRepository
import com.example.chatclient.Model.ChatHistoryRepository
import com.example.chatclient.Model.ChatUserRepository
import com.example.chatclient.Model.MessageDatabase
import com.example.chatclient.ViewModel.ChatHistoryViewModel
import com.example.chatclient.ViewModel.ChatHistoryViewModelFactory
import com.example.chatclient.ViewModel.ChatUsersViewModelFactory
import com.example.chatclient.ViewModel.MessageViewModelFactory

object Injector {

    fun provideMessageViewModelFactory(): MessageViewModelFactory {
        val messageRepository = MessageRepository.getInstance(MessageDatabase.getInstance().messageDAO)
        return MessageViewModelFactory(messageRepository)
    }

    fun provideChatUsersViewModelFactory(): ChatUsersViewModelFactory {
        val chatUserRepository = ChatUserRepository.getInstance(MessageDatabase.getInstance().chatUsersDAO)
        return ChatUsersViewModelFactory(chatUserRepository)
    }

    fun provideChatHistoryViewModelFactory(): ChatHistoryViewModelFactory {
        val chatHistoryRepositoryRepository = ChatHistoryRepository.getInstance(MessageDatabase.getInstance().chatHistoryDAO)
        return ChatHistoryViewModelFactory(chatHistoryRepositoryRepository)
    }

}
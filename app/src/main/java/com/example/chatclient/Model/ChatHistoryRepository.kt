package com.example.chatclient.Model

import com.example.chatclient.Model.HistoryDAO
import com.example.chatclient.Model.History

class ChatHistoryRepository private constructor(private val chatHistoryDAO: HistoryDAO){

    fun addHistory ( history: History) {
        chatHistoryDAO.addHistory(history)
    }

    fun getHistory() = chatHistoryDAO.getHistory()

    companion object {
        @Volatile private var instance: ChatHistoryRepository? = null

        fun getInstance(chatHistoryDAO: HistoryDAO) =
                instance?: synchronized (this){
            instance ?: ChatHistoryRepository(chatHistoryDAO).also { instance = it }
        }
    }
}
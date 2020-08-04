package com.example.chatclient.Model

class MessageDatabase private constructor() {

    var chatUsersDAO = UserDAO()
        private set
    var messageDAO = MessageDAO()
        private set     //setter
    var chatHistoryDAO = HistoryDAO()

    companion object {
        @Volatile private var instance: MessageDatabase? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: MessageDatabase().also {
                        instance = it
                    }
                }
    }
}
package com.example.chatclient.Model

class ChatUserRepository private constructor(private val userDAO: UserDAO) {

    fun addUser(chatUser: ChatUsers) {
        userDAO.addUser(chatUser)
    }

    fun getUsers() = userDAO.getUsers()

    companion object {

        @Volatile private var instance: ChatUserRepository? = null

        fun getInstance(userDAO: UserDAO) =
                instance ?: synchronized(this) {
                    instance ?: ChatUserRepository(userDAO).also { instance = it }
                }
    }
}
package com.example.chatclient.Model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.R.attr.data




class UserDAO {

    private val testUsersList = mutableListOf<ChatUsers>()
    private var liveUsers = MutableLiveData<List<ChatUsers>>()

    init {
        liveUsers.value = testUsersList
    }

    fun addUser(user: ChatUsers) {
        testUsersList.add(user)
        liveUsers.value = testUsersList
    }

    fun getUsers() = liveUsers as LiveData<List<ChatUsers>>

}
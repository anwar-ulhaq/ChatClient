package com.example.chatclient.Model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class HistoryDAO {

    private val testHistoryList = mutableListOf<History>()

    private var liveHistory = MutableLiveData<List<History>>()

    init {
        liveHistory.value = testHistoryList
    }

    fun addHistory(history: History) {
        testHistoryList.add(history)
        liveHistory.value = testHistoryList
    }

    fun getHistory() = liveHistory as LiveData<List<History>>
}
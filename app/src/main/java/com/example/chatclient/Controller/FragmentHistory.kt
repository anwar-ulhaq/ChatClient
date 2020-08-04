package com.example.chatclient.Controller

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button
import com.example.chatclient.Adapter.ChatHistoryAdapter
import com.example.chatclient.Model.History
import com.example.chatclient.Model.Transceiver
import com.example.chatclient.R
import com.example.chatclient.ViewModel.ChatHistoryViewModel
import kotlinx.android.synthetic.main.fragment_history.view.*


class FragmentHistory : Fragment() {

    private var transceiver: Transceiver? = null

    private var getHistory: Button? = null

    companion object {
        fun newInstance(): FragmentHistory {
            return FragmentHistory()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        transceiver = Transceiver.getInstance()

        val rootView = inflater!!.inflate(R.layout.fragment_history, container, false)

        val recyclerView = rootView.historyRecycleView

        recyclerView?.layoutManager = LinearLayoutManager(activity)

        val adapter = ChatHistoryAdapter(activity)

        recyclerView?.adapter = adapter

        val factory = Injector.provideChatHistoryViewModelFactory()

        val viewModel = ViewModelProviders.of(this,factory).get(ChatHistoryViewModel::class.java)

        viewModel.getHistory().observe(this, Observer { chatHistory ->

            adapter.addChatHistoryList(chatHistory!!)
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        })


        getHistory = rootView.getHistory
        getHistory?.setOnClickListener {
            transceiver?.transmit(":messages")
        }

        adapter?.addChatHistory(History("Test History message 01", "2018-10-06 00:08:28"))
        adapter?.addChatHistory(History("Test History message 02", "2018-10-06 00:09:28"))
        adapter?.addChatHistory(History("Test History message 03", "2018-10-06 00:10:28"))
        adapter?.addChatHistory(History("Test History message 04", "2018-10-06 00:11:28"))
        adapter?.addChatHistory(History("Test History message 05", "2018-10-06 00:12:28"))
        adapter?.addChatHistory(History("Test History message 06", "2018-10-06 00:13:28"))

        return rootView
    }
}
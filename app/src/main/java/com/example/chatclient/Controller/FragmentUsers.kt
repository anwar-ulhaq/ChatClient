package com.example.chatclient.Controller

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button
import com.example.chatclient.Adapter.ChatUserAdapter
import com.example.chatclient.Model.ChatUsers
import com.example.chatclient.Model.Transceiver
import com.example.chatclient.R
import com.example.chatclient.ViewModel.ChatUsersViewModel
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.fragment_users.view.*

class FragmentUsers : Fragment() {

    private var getUsers: Button? = null

    companion object {
        fun newInstance(): FragmentUsers {
            return FragmentUsers()
        }
    }


    private var transceiver: Transceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        transceiver = Transceiver.getInstance()

        val rootView = inflater!!.inflate(R.layout.fragment_users, container, false)

        val recyclerView = rootView.userRecycleView

        recyclerView?.layoutManager = LinearLayoutManager(activity)

        var adapter = ChatUserAdapter(activity)

        recyclerView?.adapter = adapter

        val factory = Injector.provideChatUsersViewModelFactory()

        val viewModel = ViewModelProviders.of(this, factory)
                .get(ChatUsersViewModel::class.java)

        viewModel.getUsers().observe(this, Observer { users ->

            adapter.addUserList(users!!)

            recyclerView.scrollToPosition(adapter.itemCount - 1)
        })

        getUsers = rootView.getUsers
        getUsers?.setOnClickListener {
            val command = ":users"
            transceiver?.transmit(command)
        }

        adapter?.addUser(ChatUsers("Test User 01"))
        adapter?.addUser(ChatUsers("Test User 02"))
        adapter?.addUser(ChatUsers("Test User 03"))
        adapter?.addUser(ChatUsers("Test User 04"))
        adapter?.addUser(ChatUsers("Test User 05"))
        adapter?.addUser(ChatUsers("Test User 06"))
        adapter?.addUser(ChatUsers("Test User 07"))

        return rootView
    }


}
package com.example.chatclient.Controller

import kotlinx.android.synthetic.main.fragment_chat.*

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_chat.view.*
import java.util.*
import android.arch.lifecycle.ViewModelProviders
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.example.chatclient.Message
import com.example.chatclient.Adapter.MessageAdapter
import com.example.chatclient.Model.Messages
import com.example.chatclient.Model.Transceiver
import com.example.chatclient.R
import com.example.chatclient.ViewModel.MessageViewModel

import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "FragmentChat"

class FragmentChat : Fragment() {

    var btnSend: Button? = null

    companion object {
        fun newInstance(): FragmentChat {
            return FragmentChat()
        }
    }

    private var transceiver : Transceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater?, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        transceiver = Transceiver.getInstance()

        val rootView = inflater!!.inflate(R.layout.fragment_chat, container, false)

        val recyclerView = rootView.messageList

        recyclerView?.layoutManager = LinearLayoutManager(activity)

        var adapter = MessageAdapter(activity)

        recyclerView?.adapter = adapter

        val factory = Injector.provideMessageViewModelFactory()

        val viewModel = ViewModelProviders.of(this, factory)
                .get(MessageViewModel::class.java)

        viewModel.getMessage().observe(this, Observer { messages ->

            adapter.addMessagesList(messages!!)
            messageList.scrollToPosition(adapter.itemCount - 1)
        })

        btnSend = rootView.btnSend

        btnSend?.setOnClickListener{

            if (txtMessage.text.isNotEmpty()) {
                transceiver?.transmit(txtMessage.text.toString())
                txtMessage.text.clear()
            } else {
                Toast.makeText(activity, "Message should not be empty", Toast.LENGTH_SHORT).show()
            }
        }

        //adding fake messages
        adapter?.addMessage(Messages("Hello, How may I help you?", "Test User 01", 1407869895000L))
        adapter?.addMessage(Messages("Hi, I would like to have dinner reservation for 12", "Test User 02", 1407869895000L))
        adapter?.addMessage(Messages("3 reservation for me too", "Test User 03", 1407869895000L))
        adapter?.addMessage(Messages("Oooops!!!!", "Test User 04", 1407869895000L))
        adapter?.addMessage(Messages("Consider it Done", "Test User 05", 1407869895000L))

        return rootView

    }
}

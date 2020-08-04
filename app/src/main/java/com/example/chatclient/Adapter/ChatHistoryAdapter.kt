package com.example.chatclient.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.chatclient.DateUtils
import com.example.chatclient.Model.History
import com.example.chatclient.R
import kotlinx.android.synthetic.main.chat_history_layout.view.*

class ChatHistoryAdapter (val context: Context) : RecyclerView.Adapter<ChatHistoryViewHolder>(){

    private var chatHistoryList : ArrayList<History> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatHistoryViewHolder {
        return HistoryViewHolder (
                LayoutInflater.from(parent!!.context).inflate(R.layout.chat_history_layout, parent, false)
        )
    }

    inner class HistoryViewHolder(view: View) : ChatHistoryViewHolder(view) {
        val historyText : TextView = view.txtHistory
        val historyTime : TextView = view.txtHistoryTime
        override fun bind(chatHistory: History) {
            historyText.text = chatHistory.historyText
            historyTime.text = chatHistory.time
        }
    }

    fun addChatHistoryList ( historyList : List<History>)
    {
        chatHistoryList.clear()
        chatHistoryList = ArrayList(historyList)
        notifyDataSetChanged()

    }

    fun addChatHistory (chatHistory: History)
    {
        chatHistoryList.add(chatHistory)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return chatHistoryList.size
    }

    override fun onBindViewHolder(holder: ChatHistoryViewHolder?, position: Int) {
        val chatHistory = chatHistoryList.get(position)
        holder?.bind(chatHistory)
    }
}



open class ChatHistoryViewHolder (view : View) : RecyclerView.ViewHolder(view){
    open fun bind(chatHistory: History)
    {

    }
}
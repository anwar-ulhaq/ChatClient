package com.example.chatclient.Adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.chatclient.AppUser
import com.example.chatclient.DateUtils
import com.example.chatclient.Model.Messages
import com.example.chatclient.R
import kotlinx.android.synthetic.main.outgoing_message.view.*
import kotlinx.android.synthetic.main.incoming_message.view.*


private const val VIEW_TYPE_OUTGOING_MESSAGE = 1
private const val VIEW_TYPE_INCOMING_MESSAGE = 2


class MessageAdapter (val context: Context) : RecyclerView.Adapter<MessageViewHolder>() {

    private var messages: ArrayList<Messages> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        return if(viewType == VIEW_TYPE_OUTGOING_MESSAGE) {
            OutgoingMessageViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.outgoing_message, parent, false)
            )
        } else {
            IncomingMessageViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.incoming_message, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages.get(position)
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    inner class OutgoingMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtMyMessage
        private var timeText: TextView = view.txtMyMessageTime

        override fun bind(message: Messages) {
            messageText.text = message.messageText
            timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }

    }

    inner class IncomingMessageViewHolder (view: View) : MessageViewHolder(view) {
        private var messageText: TextView = view.txtOtherMessage
        private var userText: TextView = view.txtOtherUser
        private var timeText: TextView = view.txtOtherMessageTime

        override fun bind(message: Messages) {
            messageText.text = message.messageText
            userText.text = message.user
            timeText.text = DateUtils.fromMillisToTimeString(message.time)
        }
    }

    fun addMessagesList(messageList : List<Messages>)
    {
        messages.clear()
        messages = ArrayList(messageList)
        notifyDataSetChanged()
    }

    fun addMessage(message: Messages){
        val temp :Int = 23
        messages.add(message)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages.get(position)

        return if(AppUser.appUser == message.user) {
            VIEW_TYPE_OUTGOING_MESSAGE
        }
        else {
            VIEW_TYPE_INCOMING_MESSAGE
        }
    }
}

open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message:Messages) {    }
}

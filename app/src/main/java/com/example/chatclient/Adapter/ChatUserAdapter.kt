package com.example.chatclient.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.chatclient.Model.ChatUsers
import com.example.chatclient.R
import kotlinx.android.synthetic.main.chat_users_layout.view.*

class ChatUserAdapter (val context: Context) : RecyclerView.Adapter<ChatUserViewHolder>(){

    private var chatUserList: ArrayList<ChatUsers> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ChatUserViewHolder {
        return UserViewHolder (
                LayoutInflater.from(parent!!.context).inflate(R.layout.chat_users_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return chatUserList.size
    }

    override fun onBindViewHolder(holder: ChatUserViewHolder?, position: Int) {
        val chatUser = chatUserList.get(position)
        holder?.bind(chatUser)
    }

    inner class UserViewHolder (view: View) : ChatUserViewHolder(view){

        private var userNameText : TextView = view.txtUserName
        override fun bind(chatUsers: ChatUsers) {
            userNameText.text = chatUsers.user
        }
    }

    fun addUser( user: ChatUsers){
        chatUserList.clear()
        chatUserList.add(user)
        notifyDataSetChanged()
    }
    fun addUserList( userList: List<ChatUsers>){
        chatUserList = ArrayList(userList)
        notifyDataSetChanged()
    }

    fun clearAdapterList() {
        chatUserList.clear()
    }
}

open class ChatUserViewHolder (view : View) : RecyclerView.ViewHolder(view){
    open fun bind(chatUsers: ChatUsers)
    {

    }
}

package com.example.chatclient.ViewModel

import android.arch.lifecycle.ViewModel
import android.os.Message
import com.example.chatclient.Model.ChatModel
import android.arch.lifecycle.MutableLiveData



class Chat_ViewModel : ViewModel{

    //Primary
    //Will call later on from
    constructor() : super()

    //Secondary
    constructor(chatModel: ChatModel) : super()
    {
        val user = chatModel.user
        val message = chatModel.message
        val time:Long = chatModel.time
    }

    val chatViewModelMutableLiveData = MutableLiveData<ArrayList<Chat_ViewModel>>()

    val chatViewModel = ArrayList<Chat_ViewModel>()


    fun getchatViewModel() : MutableLiveData<ArrayList<Chat_ViewModel>>{

        val chatModel1 = ChatModel("Anwar","message from chatModel1", 1407869895000L)

        val chatModel2 = ChatModel("Anwar","message from chatModel2", 1407869895000L)
        val chatModel3 = ChatModel("Anwar","message from chatModel3", 1407869895000L)

        val chatViewModel1 : Chat_ViewModel = Chat_ViewModel(chatModel1)


        val chatViewModel2 : Chat_ViewModel = Chat_ViewModel(chatModel2)

        val chatViewModel3 : Chat_ViewModel = Chat_ViewModel(chatModel3)

        chatViewModel!!.add(chatViewModel1)
        chatViewModel!!.add(chatViewModel2)
        chatViewModel!!.add(chatViewModel3)

        chatViewModelMutableLiveData.value = chatViewModel

        return chatViewModelMutableLiveData
    }

}
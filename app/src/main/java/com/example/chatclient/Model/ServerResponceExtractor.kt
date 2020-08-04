package com.example.chatclient.Model

import android.util.Log
import com.example.chatclient.AppUser
import com.example.chatclient.MessageRepository
import java.text.SimpleDateFormat
import java.util.*

/*
Server replies comes here,
Username is extracted and a Users object is created along with random color
Messages object is created.
Time of arrival is added in Messages object
Messages object is added to repository
 */


object ServerResponseExtractor {

    /*
    User Handling.
    User as a string is forwarded to UserDAO class,
    addUser(user:Users) method will check for exciting user.
    new user will also assigned random color there.
     */
    private var user: String = ""
    private var userCreated: Boolean? = null
    private var responseOut = ""
    private var message = ""


    val chatUserRepository = ChatUserRepository.getInstance(MessageDatabase.getInstance().chatUsersDAO)
    val messageRepository = MessageRepository.getInstance(MessageDatabase.getInstance().messageDAO)
    val chatHistoryRepository = ChatHistoryRepository.getInstance(MessageDatabase.getInstance().chatHistoryDAO)

    @Synchronized
    fun addResponse(response: String) {

        Log.d("SRE", "$response")

        if (response.contains("Username already in use. Try Something else again")) {
            responseOut = "No User"
        } else if (response.contains("Username set to  ${AppUser.appUser}")) {
            responseOut = "Yes User"
        } else if (response.startsWith("Users:")) {
            handleUserList(response)
        } else if (response.startsWith("History:")) {
            handleHistory(response)
        } else {
            val responseAsList = response.toList()
            messageRepository.addMessage(
                    Messages(
                            response.substring(0, response.lastIndexOf(" ") - 5),
                            response.substring(response.lastIndexOf(" ") + 1),
                            Calendar.getInstance().timeInMillis)
            )
        }
    }

    private fun handleHistory(response: String) {
        val historyAsString = response.substring(0, response.length - 2)
                .replaceFirst("History: ,", "")
        val historyAsList  : List<String> = historyAsString.split(",").map { it.trim() }
        historyAsList.forEach {
            val timeAsReadableFormat = it.substring(it.indexOf("[") + 1, it.indexOf("]") )
            val historyMessage = it.drop (24)
            Log.d("SRE: TIme: ", "${SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(timeAsReadableFormat).time}")
            Log.d("SRE: His Mess: ", "$historyMessage")
            //chatHistoryRepository.addHistory(History(historyMessage,SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(timeAsReadableFormat).time))
            chatHistoryRepository.addHistory(History(historyMessage,timeAsReadableFormat))

        }

    }

    private fun handleUserList(response: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val usersAsString =     response.substring(0, response.length - 1)
                                                .replaceFirst("Users: ,", "")


        Log.d("UserList from SRE", "$usersAsString")

        val usersList  : List<String> = usersAsString.split(",").map { it.trim() }

        usersList.forEach{
            val chatUsers = ChatUsers(it)
            chatUserRepository.addUser(chatUsers)
        }
    }

    fun isUserCreated(): Boolean {
        var returnCondition = false
        if (userCreated != null)
            returnCondition == userCreated

        return returnCondition
    }

    fun getResponse(): String {
        return responseOut
    }

}
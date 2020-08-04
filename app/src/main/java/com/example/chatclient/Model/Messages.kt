package com.example.chatclient.Model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class Messages (val messageText: String,
                     val user: String,
                     val time: Long
                     /*val time:LocalDateTime*/) {

    override fun toString(): String {
        return "[ ${time} ] $messageText from $user "
        //return "[ ${time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))} ] $messageText from $user "
    }
}
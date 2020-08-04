package com.example.chatclient.Controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment

import com.example.chatclient.R

import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_chat_room)

        navigationView.setOnNavigationItemSelectedListener { item ->

            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.Chat -> selectedFragment = FragmentChat.newInstance()
                R.id.Users -> selectedFragment = FragmentUsers.newInstance()
                R.id.History -> selectedFragment = FragmentHistory.newInstance()
            }

            val transaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.container, selectedFragment)

            transaction.commit()

            true
        }

    }
}
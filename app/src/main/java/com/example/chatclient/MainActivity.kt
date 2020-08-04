package com.example.chatclient

import android.support.v7.app.AppCompatActivity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*

import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.text.InputFilter
import android.text.Spanned
import android.content.DialogInterface.OnShowListener
import android.text.TextUtils
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import com.example.chatclient.Model.ServerResponseExtractor
import com.example.chatclient.Model.Transceiver
import com.example.chatclient.Controller.ChatRoom


class MainActivity : AppCompatActivity() {

    private var transceiver : Transceiver? = null
    private var appUser : AppUser? = null
    private var connectionDetails : ConnectionDetails? = null
    private var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GoToLogin.setOnClickListener {

                if (connectionDetails == null ) {
                    queryConnectionDialog(this, resources.getString(R.string.prompt))
                } else {
                    if ( appUser == null ) {
                        Toast.makeText(this, "User not created yet", Toast.LENGTH_LONG).show()
                        queryUserDialog(this,resources.getString(R.string.userprompt))
                    } else {
                        startActivity(Intent(this, ChatRoom::class.java))
                    }
                }
        }

    }

    private fun queryUserDialog(context: Context, msg: String){

        val li = LayoutInflater.from(context)
        val promptsView = li.inflate(R.layout.user_input_dialog, null)
        val alertDialogBuilder = android.app.AlertDialog.Builder(context)
        alertDialogBuilder.setView(promptsView)
        val dialogMsg = promptsView.findViewById(R.id.textViewDialogMsg) as TextView
        val dialogUserName = promptsView.findViewById(R.id.editTextDialogUserName) as EditText
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        dialogUserName.setFilters(arrayOf(filter))
        dialogMsg.text = msg
        dialogUserName.setText("")

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Set Nick Name" ) { dialog, id ->
                    userName = dialogUserName.text.toString()


                    if (transceiver != null) {
                        transceiver?.transmit(":user $userName")
                        val res = transceiver?.receive()
                        Log.d("MAIN: res value", "$res")
                        Log.d("User Creation", "User created ${ServerResponseExtractor.getResponse()}")
                            AppUser.appUser = userName
                            appUser = AppUser()
                            Toast.makeText(this, "User name saved as ${AppUser.appUser}", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, ChatRoom::class.java))
                    }
                    else {
                        Toast.makeText(this, "Transceiver is null", Toast.LENGTH_LONG).show()
                    }
                }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        dialogUserName.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                // Check if edittext is empty
                if (TextUtils.isEmpty(s)) {
                    // Disable ok button
                    alertDialog.getButton(
                            AlertDialog.BUTTON_POSITIVE).isEnabled = false
                } else {
                    alertDialog.getButton(
                            AlertDialog.BUTTON_POSITIVE).isEnabled = true
                }

            }
        })
    }

    private fun queryConnectionDialog(context: Context, msg: String) {
        val li = LayoutInflater.from(context)
        val promptsView = li.inflate(R.layout.connection_details_dialog, null)
        val alertDialogBuilder = android.app.AlertDialog.Builder(context)
        alertDialogBuilder.setView(promptsView)
        val dialogMsg = promptsView.findViewById(R.id.textViewDialogMsg) as TextView
        val dialogServerAddress = promptsView.findViewById(R.id.editTextDialogServerAddress) as EditText
        val dialogServerPort = promptsView.findViewById(R.id.editTextDialogServerPort) as EditText

        dialogMsg.text = msg
        dialogServerAddress.setText("")
        dialogServerPort.setText("")

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Connect"
                ) { dialog, id ->
                    // get user input and set it to result
                    val serverAddress = dialogServerAddress.text.toString()
                    val serverPort = dialogServerPort.text.toString()
                    ConnectionDetails.serverAddress = serverAddress
                    ConnectionDetails.serverPort = serverPort
                    connectionDetails = ConnectionDetails()

                    transceiver = Transceiver.getInstance()

                    queryUserDialog(this,resources.getString(R.string.userprompt))

                }
                .setNegativeButton("Cancel",
                        object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface, id: Int) {
                                dialog.cancel()
                            }
                        })

        val alertDialog = alertDialogBuilder.create()

        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

        dialogServerAddress.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                // Check if edittext is empty
                if (TextUtils.isEmpty(s)) {
                    // Disable ok button
                    alertDialog.getButton(
                            AlertDialog.BUTTON_POSITIVE).isEnabled = false
                } else {
                    // Something into edit text. Enable the button.
                    alertDialog.getButton(
                            AlertDialog.BUTTON_POSITIVE).isEnabled = true
                }

            }
        })

        dialogServerPort.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(s)) {
                    alertDialog.getButton(
                            AlertDialog.BUTTON_POSITIVE).isEnabled = false
                } else {
                    alertDialog.getButton(
                            AlertDialog.BUTTON_POSITIVE).isEnabled = true
                }

            }
        })


    }

    companion object {
        const val PREFERENCEID = "Credentials"
    }
}
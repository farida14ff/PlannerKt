package com.example.plannerkt.section_chat.chats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.plannerkt.R

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
    }

    fun onClickSendMesssage(view: View) {}
}
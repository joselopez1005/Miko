package com.example.miko.presentation.ui.chat_screen

import com.example.miko.R
import com.example.miko.domain.chat.Message
import com.example.miko.domain.chat.ProfileInfo
import java.io.Serializable
import java.time.LocalDateTime

data class ChatScreenStates (
    val isLoading: Boolean = false,
    var error: String? = null,
    val chatProfile: ProfileInfo = ProfileInfo("Miko", R.drawable.ic_morgana),
    val chatLogs: MutableList<Message> = mutableListOf(Message("system", "I want you to act as an ascii artist. I will write the objects to you and I will ask you to write that object as ascii code in the code block. Write only ascii code. Do not explain about the object you wrote. I will say the objects in double quotes. My first object is \"cat\"", LocalDateTime.now())),

    ) : Serializable
package com.example.miko.presentation.ui.chat_screen

import com.example.miko.R
import com.example.miko.domain.chat.Message
import com.example.miko.domain.chat.ProfileInfo
import java.io.Serializable
import java.time.LocalDateTime

data class ChatScreenStates (
    val isLoading: Boolean = false,
    var error: String? = null,
    val chatId:Int = 1,
    val chatProfile: ProfileInfo = ProfileInfo("Miko", R.drawable.ic_morgana),
    val chatLogs: MutableList<Message> = mutableListOf(Message("system", "You are a helpful assistant", LocalDateTime.now())),

    ) : Serializable
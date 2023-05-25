package com.example.miko.presentation.ui.chat_screen

import com.example.miko.R
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message
import com.example.miko.domain.chat.ProfileInfo
import java.io.Serializable
import java.time.LocalDateTime

data class ChatScreenStates (
    val completions: Completions? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val chatProfile: ProfileInfo = ProfileInfo("Miko", R.drawable.ic_morgana),
    val chatLogs: MutableList<Message> = mutableListOf(Message("system", "You are a helpful assistant", LocalDateTime.now().toString())),

    ) : Serializable
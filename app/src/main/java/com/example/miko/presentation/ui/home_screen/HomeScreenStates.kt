package com.example.miko.presentation.ui.home_screen

import com.example.miko.domain.chat.ChatItem

data class HomeScreenStates(
    var amountOfChats: Int = 0,
    var isLoading: Boolean = true,
    var error: String? = null,
    var latestMessagesChats: MutableList<ChatItem?> = mutableListOf()
)
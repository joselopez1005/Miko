package com.example.miko.presentation.ui.chat_screen

sealed class ChatScreenEvents {
    data class onSendMessage(val message: String): ChatScreenEvents()
}

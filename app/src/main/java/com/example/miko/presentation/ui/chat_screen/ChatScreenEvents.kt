package com.example.miko.presentation.ui.chat_screen

sealed class ChatScreenEvents {
    data class OnSendMessage(val message: String): ChatScreenEvents()
    data class DeleteAllMessages(val delete: Boolean): ChatScreenEvents()
}

package com.example.miko.domain.chat

import java.time.LocalDateTime

data class ChatItem(
    val name: String,
    val latestMessage: String,
    val chatId: Int,
    val latestTime: LocalDateTime
)

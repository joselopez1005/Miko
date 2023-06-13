package com.example.miko.domain.chat

import java.time.LocalDateTime

data class Message(
    val role: String,
    val content: String,
    val time: LocalDateTime
)

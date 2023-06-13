package com.example.miko.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMessageEntity (
    val role: String,
    val content: String,
    val time: Long,
    @PrimaryKey val id: Int? = null
)
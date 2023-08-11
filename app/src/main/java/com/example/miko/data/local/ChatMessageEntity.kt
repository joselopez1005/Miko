package com.example.miko.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChatMessageEntity (
    @ColumnInfo(name = "role")
    val role: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "time")
    val time: Long,
    @ColumnInfo(name = "chatId")
    val chatId: Int,
    @PrimaryKey val id: Int? = null
)
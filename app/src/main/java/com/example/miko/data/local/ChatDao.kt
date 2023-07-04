package com.example.miko.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(
        chatMessage: ChatMessageEntity
    )

    @Query("DELETE FROM ChatMessageEntity")
    suspend fun deleteChatMessage()

    @Query("SELECT * FROM ChatMessageEntity ORDER BY id DESC LIMIT 1")
    suspend fun selectLatestMessage(): ChatMessageEntity

    @Query("SELECT * FROM ChatMessageEntity")
    suspend fun getAllMessages(): List<ChatMessageEntity>

    @Query("SELECT * FROM ChatMessageEntity WHERE time >= :time")
    suspend fun getAllMessagesTimeRange(time: Long): List<ChatMessageEntity>

    @Query("SELECT * FROM ChatMessageEntity WHERE role = :role ORDER BY time DESC LIMIT 1 ")
    suspend fun getLatestPersonality(role: String): ChatMessageEntity?
}
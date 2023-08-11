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

    @Query("DELETE FROM ChatMessageEntity WHERE chatId=:chatId")
    suspend fun deleteChatMessage(chatId: Int)

    @Query("SELECT * FROM ChatMessageEntity WHERE chatId = :chatId ORDER BY id DESC LIMIT 1")
    suspend fun selectLatestMessage(chatId: Int): ChatMessageEntity

    @Query("SELECT * FROM ChatMessageEntity WHERE chatId = :chatId")
    suspend fun getAllMessages(chatId: Int): List<ChatMessageEntity>

    @Query("SELECT * FROM ChatMessageEntity WHERE time >= :time AND chatId = :chatId")
    suspend fun getAllMessagesTimeRange(chatId: Int, time: Long): List<ChatMessageEntity>

    @Query("SELECT * FROM ChatMessageEntity WHERE role = :role AND chatId = :chatId ORDER BY time DESC LIMIT 1 ")
    suspend fun getLatestPersonality(chatId: Int, role: String): ChatMessageEntity?

    @Query("SELECT MAX(chatId) FROM ChatMessageEntity")
    suspend fun getNumberOfChats(): Int

    @Query("SELECT * FROM ChatMessageEntity WHERE chatId = :chatId ORDER BY time DESC LIMIT 1")
    suspend fun getLatestMessage(chatId: Int): ChatMessageEntity
}
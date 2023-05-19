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

}
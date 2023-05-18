package com.example.miko.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetChatMessage(
        chatMessage: ChatMessageEntity
    )

    @Query("DELETE FROM ChatMessageEntity")
    suspend fun deleteChatMessage()

}
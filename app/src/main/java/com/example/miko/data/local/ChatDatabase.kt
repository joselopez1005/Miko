package com.example.miko.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ChatMessageEntity::class],
    version = 2
)
abstract class ChatDatabase: RoomDatabase() {
    abstract val dao: ChatDao
}
package com.example.miko.domain.repository

import com.example.miko.domain.chat.ChatItem
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message
import com.example.miko.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessageData(messages: List<Message>, chatId: Int): Flow<Resource<Completions>>
    suspend fun getMessageData(chatId: Int): Flow<Resource<Completions>>
    suspend fun deleteAllMessages(chatId: Int): Flow<Resource<Boolean>>
    suspend fun getLatestPersonality(chatId: Int): Flow<Resource<Completions?>>
    suspend fun getAmountOfChats(): Flow<Resource<Int>>
    suspend fun getLatestMessageInformation(chatId: Int): Flow<Resource<ChatItem>>
}
package com.example.miko.domain.repository

import com.example.miko.domain.chat.ChatItem
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message
import com.example.miko.domain.chat.ProfileInfo
import com.example.miko.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessageData(messages: List<Message>, profileInfo: ProfileInfo): Flow<Resource<Completions>>
    suspend fun getMessageData(profileInfo: ProfileInfo): Flow<Resource<Completions>>
    suspend fun deleteAllMessages(profileInfo: ProfileInfo): Flow<Resource<Boolean>>
    suspend fun getLatestPersonality(profileInfo: ProfileInfo): Flow<Resource<Completions?>>
    suspend fun getAmountOfChats(): Flow<Resource<Int>>
    suspend fun getLatestMessageInformation(chatId: Int): Flow<Resource<ChatItem>>
}
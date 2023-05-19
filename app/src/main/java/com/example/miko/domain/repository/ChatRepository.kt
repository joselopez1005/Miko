package com.example.miko.domain.repository

import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message
import com.example.miko.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun sendMessageData(messages: List<Message>): Flow<Resource<Completions>>
}
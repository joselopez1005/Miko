package com.example.miko.domain.repository

import com.example.miko.domain.chat.Completions
import com.example.miko.domain.util.Resource

interface ChatRepository {
    suspend fun sendMessageData(model: String, role: String, content: String): Resource<Completions>
}
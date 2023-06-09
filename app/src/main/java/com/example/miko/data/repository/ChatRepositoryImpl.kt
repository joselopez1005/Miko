package com.example.miko.data.repository

import com.example.miko.BuildConfig
import com.example.miko.data.local.ChatDatabase
import com.example.miko.data.mappers.toChatMessageEntity
import com.example.miko.data.mappers.toCompletions
import com.example.miko.data.mappers.toMessage
import com.example.miko.data.remote.MessageBody
import com.example.miko.data.remote.OpenApi
import com.example.miko.data.remote.PromptBody
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message
import com.example.miko.domain.repository.ChatRepository
import com.example.miko.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

const val MODEL = "gpt-3.5-turbo-0301"
@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val openApi: OpenApi,
    private val db: ChatDatabase
): ChatRepository {

    private val dao = db.dao
     override suspend fun sendMessageData(
        messages: List<Message>
    ): Flow<Resource<Completions>> {
        return flow {
            emit(Resource.Loading(true))
            val sendMessage = try {
                openApi.getTextCompletion(
                    "Bearer ${BuildConfig.OPEN_API_KEY}",
                    PromptBody(MODEL, messages.map { MessageBody(it.role, it.content) }, 3)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Unkown error"))
                null
            }
            dao.insertChatMessage(messages.last().toChatMessageEntity())

            sendMessage?.let { message ->
                dao.insertChatMessage(message.toChatMessageEntity())
                emit(Resource.Success(
                    data = dao.selectLatestMessage().toCompletions()
                ))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMessageData(): Flow<Resource<Completions>> {
        return flow {
            emit(Resource.Loading(true))
            val listOfMessages = dao.getAllMessages().map { it.toMessage() }
            emit(Resource.Success(Completions(listOfMessages)))
            emit(Resource.Loading(false))
        }
    }
}
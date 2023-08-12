package com.example.miko.data.repository

import android.util.Log
import com.example.miko.BuildConfig
import com.example.miko.data.local.ChatDatabase
import com.example.miko.data.mappers.toChatItem
import com.example.miko.data.mappers.toChatMessageEntity
import com.example.miko.data.mappers.toCompletions
import com.example.miko.data.mappers.toMessage
import com.example.miko.data.remote.MessageBody
import com.example.miko.data.remote.OpenApi
import com.example.miko.data.remote.PromptBody
import com.example.miko.domain.chat.ChatItem
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message
import com.example.miko.domain.chat.ProfileInfo
import com.example.miko.domain.repository.ChatRepository
import com.example.miko.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.ZoneOffset
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
        messages: List<Message>,
        profileInfo: ProfileInfo
    ): Flow<Resource<Completions>> {
        return flow {
            emit(Resource.Loading(true))

            dao.insertChatMessage(messages.last().toChatMessageEntity(profileInfo)) // Storing user message

            val sendMessage = try {
                val validMessageTime = LocalDateTime.now().minusHours(5).toEpochSecond(
                    ZoneOffset.systemDefault().rules.getOffset(
                        LocalDateTime.now()))
                Log.d("ChatRepository", "ValidMessageTime = $validMessageTime \n CurrentMessageTime = ${messages.last().toChatMessageEntity(profileInfo).time}")
                openApi.getTextCompletion(
                    "Bearer ${BuildConfig.OPEN_API_KEY}",
                    PromptBody(MODEL, dao.getAllMessagesTimeRange(profileInfo.chatId, validMessageTime).map { MessageBody(it.role, it.content) }, 1)
                )
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Unknown error"))
                null
            }

            // When setting up personality, we do not want to display the latest message sent back from server
            if (messages.last().role != "system") {
                // Storing AI message
                sendMessage?.let { message ->
                    dao.insertChatMessage(message.toChatMessageEntity(profileInfo))
                    emit(Resource.Success(
                        data = dao.selectLatestMessage(profileInfo.chatId).toCompletions()
                    ))
                }
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMessageData(profileInfo: ProfileInfo): Flow<Resource<Completions>> {
        return flow {
            emit(Resource.Loading(true))
            val listOfMessages = dao.getAllMessages(profileInfo.chatId).map { it.toMessage() }
            emit(Resource.Success(Completions(listOfMessages)))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getLatestPersonality(profileInfo: ProfileInfo): Flow<Resource<Completions?>> {
        return flow {
            emit(Resource.Loading(true))
            val latestPersonality = dao.getLatestPersonality(profileInfo.chatId, "system")?.toCompletions()
            emit(Resource.Success(latestPersonality))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun deleteAllMessages(profileInfo: ProfileInfo): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))
            dao.deleteChatMessage(profileInfo.chatId)
            emit(Resource.Loading(false))
            emit(Resource.Success(true))
        }
    }

    override suspend fun getAmountOfChats(): Flow<Resource<Int>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val amount:Int = dao.getNumberOfChats()
                emit(Resource.Loading(false))
                emit(Resource.Success(amount))
            } catch (e: Exception) {
                emit(Resource.Loading(false))
                emit(Resource.Error("No chats found"))
            }
        }
    }

    override suspend fun getLatestMessageInformation(chatId: Int): Flow<Resource<ChatItem>> {
        return flow {
            emit(Resource.Loading(true))
            val chatItem = dao.getLatestMessage(chatId).toChatItem()
            emit(Resource.Loading(false))
            emit(Resource.Success(chatItem))
        }
    }
}
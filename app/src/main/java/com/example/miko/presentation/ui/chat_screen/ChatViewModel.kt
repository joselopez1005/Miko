package com.example.miko.presentation.ui.chat_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miko.domain.chat.Message
import com.example.miko.domain.repository.ChatRepository
import com.example.miko.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {

    var state by mutableStateOf(ChatScreenStates())

    init {
        getAllMessages()

    }

    fun onEvent(event: ChatScreenEvents) {
        when(event) {
            is ChatScreenEvents.OnSendMessage -> {
                if (!state.isLoading) {
                    state.chatLogs.add(Message(USER, event.message, LocalDateTime.now()))
                    sendMessage()
                }
            }

            is ChatScreenEvents.DeleteAllMessages -> {
                deleteAllMessages()
            }
        }
    }

    private fun setChatPersonality() {
        viewModelScope.launch {
            chatRepository.getLatestPersonality().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(isLoading = false)
                        result.data?.let {
                            state.chatLogs.add(Message(it.messages.last().role, it.messages.last().content, LocalDateTime.now()))
                            Log.d("ChatViewModel", "Personality: ${result.data.messages.last().content}")
                            sendMessage()
                            return@collect
                        }
                        state.chatLogs.add(Message("system", "You are a helpful assistant", LocalDateTime.now()))
                        sendMessage()
                        Log.d("ChatViewModel", "Personality: ASCCIII")

                    }
                    is Resource.Error -> {
                        state = state.copy(error = result.message ?: "Failed to set personality")
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            chatRepository.sendMessageData(state.chatLogs).collect{ result ->
                when(result) {
                    is Resource.Success -> {
                        state = state.copy(isLoading = false)
                        state.chatLogs.add(Message(result.data!!.messages.first().role, result.data.messages.first().content, result.data.messages.first().time))
                    }
                    is Resource.Error -> {
                        state = state.copy(isLoading = false, error = result.message)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    private fun getAllMessages() {
        viewModelScope.launch {
            chatRepository.getMessageData().collect { result ->
                when(result) {
                    is Resource.Success -> {
                        Log.d("MessageResult", result.data?.messages?.size.toString())
                        result.data?.let{ data ->
                            data.messages.forEach {
                                state.chatLogs.add(it)
                            }
                        }
                        setChatPersonality()
                    }
                    is Resource.Error -> {
                        state = state.copy(error = result.message)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    private fun deleteAllMessages() {
        viewModelScope.launch {
            chatRepository.deleteAllMessages().collect { result ->
                state = when (result) {
                    is Resource.Success -> {
                        state.copy(isLoading = false, error = null, chatLogs = mutableListOf())
                    }
                    is Resource.Error -> {
                        state.copy(isLoading = false, error = result.message ?: "Failed to delete messages")
                    }
                    is Resource.Loading -> {
                        state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }
    companion object {
        const val USER = "user"
        const val ASSISTANT = "assistant"
        const val SYSTEM = "system"
    }
}
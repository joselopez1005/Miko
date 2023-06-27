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

    //val state = savedStateHandle.getStateFlow(STATE, ChatScreenStates())
    //val state = MutableStateFlow(ChatScreenStates())
    var state by mutableStateOf(ChatScreenStates())

    init {
        getAllMessages()
    }

    fun onEvent(event: ChatScreenEvents) {
        when(event) {
            is ChatScreenEvents.OnSendMessage -> {
                // Divide under 1000 to make it unix time
                state.chatLogs.add(Message(USER, event.message, LocalDateTime.now()))
                sendMessage()
            }
            is ChatScreenEvents.DeleteAllMessages -> {
                viewModelScope.launch {
                    chatRepository.deleteAllMessages().collect{ result ->
                        when(result) {
                            is Resource.Success -> {
                                state = state.copy(completions = null, error = null, chatLogs = mutableListOf(), isLoading = false)
                            }
                            is Resource.Error -> {

                            }
                            is Resource.Loading -> {
                                state = state.copy(isLoading = true)
                            }
                        }
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
                        state = state.copy(completions = result.data, isLoading = false)
                        state.chatLogs.add(Message(result.data!!.messages.first().role, result.data.messages.first().content, result.data.messages.first().time))
                    }
                    is Resource.Error -> {
                        state = state.copy(completions = null, isLoading = false, error = result.message)
                    }
                    is Resource.Loading -> {
                        state = state.copy(completions = null, isLoading = result.isLoading)
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
                        state = state.copy(completions = result.data)
                        result.data?.let{ data ->
                            data.messages.forEach {
                                state.chatLogs.add(it)
                            }
                        }
                    }
                    is Resource.Error -> {
                    }
                    is Resource.Loading -> {
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
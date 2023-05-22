package com.example.miko.presentation.ui.chat_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
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
    private val savedStateHandle: SavedStateHandle,
    private val chatRepository: ChatRepository
): ViewModel() {

    val state = savedStateHandle.getStateFlow(STATE, ChatScreenStates())

    init {
        getAllMessages()
    }

    fun onEvent(event: ChatScreenEvents) {
        when(event) {
            is ChatScreenEvents.OnSendMessage -> {
                state.value.chatLogs.add(Message(USER, event.message, LocalDateTime.now().toString()))
                sendMessage()
            }
        }
    }

    private fun sendMessage() {
        viewModelScope.launch {
            chatRepository.sendMessageData(state.value.chatLogs).collect{ result ->
                when(result) {
                    is Resource.Success -> {
                        savedStateHandle[STATE] = state.value.copy(completions = result.data, isLoading = false)
                        state.value.chatLogs.add(Message(result.data!!.messages.first().role, result.data.messages.first().content, result.data.messages.first().time))
                    }
                    is Resource.Error -> {
                        savedStateHandle[STATE] = state.value.copy(completions = null, isLoading = false, error = result.message)
                    }
                    is Resource.Loading -> {
                        savedStateHandle[STATE] = state.value.copy(completions = null, isLoading = result.isLoading)
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
                        savedStateHandle[STATE] = state.value.copy(completions = result.data)
                        result.data?.let{ data ->
                            data.messages.forEach {
                                state.value.chatLogs.add(it)
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
        private const val STATE = "STATE"
        const val USER = "user"
        const val ASSISTANT = "assistant"
        const val SYSTEM = "system"
    }
}
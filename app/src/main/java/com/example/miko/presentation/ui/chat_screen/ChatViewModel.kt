package com.example.miko.presentation.ui.chat_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miko.domain.chat.Message
import com.example.miko.domain.repository.ChatRepository
import com.example.miko.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val chatRepository: ChatRepository
): ViewModel() {

    val state = savedStateHandle.getStateFlow(STATE, ChatScreenStates())

    fun onEvent(event: ChatScreenEvents) {
        when(event) {
            is ChatScreenEvents.OnSendMessage -> {
                state.value.chatLogs.add(Message(USER, event.message))
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
                        state.value.chatLogs.add(Message(result.data!!.messages.first().role, result.data.messages.first().content))
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


    companion object {
        private const val STATE = "STATE"
        const val USER = "user"
        const val ASSISTANT = "assistant"
        const val SYSTEM = "system"
    }
}
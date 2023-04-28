package com.example.miko.presentation.ui.chat_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        sendMessage("Test")
    }

    fun sendMessage(message: String?) {
        viewModelScope.launch {
            savedStateHandle[STATE] = ChatScreenStates(completions = null, isLoading = true)

            when (val result = chatRepository.sendMessageData(model = MODEL, role = USER, content = message ?: "")) {
                is Resource.Success -> {
                    savedStateHandle[STATE] = ChatScreenStates(completions = result.data, isLoading = false)
                }
                is Resource.Error -> {
                    savedStateHandle[STATE] = ChatScreenStates(completions = null, isLoading = false, error = result.message)
                }
            }
        }
    }


    companion object {
        const val STATE = "STATE"
        const val MODEL = "gpt-3.5-turbo-0301"
        const val USER = "user"
    }
}
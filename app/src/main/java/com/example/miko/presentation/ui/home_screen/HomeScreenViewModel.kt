package com.example.miko.presentation.ui.home_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miko.domain.repository.ChatRepository
import com.example.miko.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    var state by mutableStateOf(HomeScreenStates())

    init {
        getAmountOfChats()
    }

    private fun getAmountOfChats() {
        viewModelScope.launch {
            chatRepository.getAmountOfChats().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(isLoading = false)
                        Log.d("HomeScreenViewModel", "Amount of Chats ${result.data}")
                        state = state.copy(amountOfChats = result.data!!)
                        var chats = 1
                        while (chats <= state.amountOfChats) {
                            getChatInformation(chats)
                            chats++
                        }
                    }
                    is Resource.Error ->state = state.copy(isLoading = false)
                    is Resource.Loading -> state = state.copy(isLoading = true)

                }
            }
        }
    }

    private fun getChatInformation(chatId: Int) {
        viewModelScope.launch {
            chatRepository.getLatestMessageInformation(chatId).collect { result ->
                state = when (result) {
                    is Resource.Success -> {
                        Log.d("HomeScreenViewModel", "ChatInformation ${result.data}")
                        state.latestMessagesChats.add(result.data)
                        state.copy(isLoading = false)
                    }

                    is Resource.Error -> state.copy(isLoading = false)
                    is Resource.Loading -> state.copy(isLoading = true)
                }
            }
        }
    }
}
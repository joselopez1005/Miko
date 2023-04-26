package com.example.miko.presentation.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miko.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
): ViewModel() {
    fun loadTestChat() {
        viewModelScope.launch {
          val result =  chatRepository.sendMessageData(
                "gpt-3.5-turbo-0301",
                "user",
                "Celebrate with me!"
            )
        }
    }

}
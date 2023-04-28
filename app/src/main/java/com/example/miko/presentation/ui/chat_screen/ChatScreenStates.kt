package com.example.miko.presentation.ui.chat_screen

import com.example.miko.domain.chat.Completions
import java.io.Serializable

data class ChatScreenStates (
    val completions: Completions? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) : Serializable
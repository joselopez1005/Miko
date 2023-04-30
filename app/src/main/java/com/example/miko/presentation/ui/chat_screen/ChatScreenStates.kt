package com.example.miko.presentation.ui.chat_screen

import com.example.miko.R
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.ProfileInfo
import java.io.Serializable

data class ChatScreenStates (
    val completions: Completions? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val chatProfile: ProfileInfo = ProfileInfo("Miko", R.drawable.person_girl)
) : Serializable
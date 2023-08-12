package com.example.miko.domain.chat

import androidx.annotation.DrawableRes

data class ProfileInfo(
    val name: String,
    @DrawableRes val iconRes: Int,
    val chatId: Int
)
package com.example.miko.data.remote.completions

import com.google.gson.annotations.SerializedName

data class MessagesDto(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

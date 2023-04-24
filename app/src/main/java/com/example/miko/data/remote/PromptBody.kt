package com.example.miko.data.remote

import com.google.gson.annotations.SerializedName

data class PromptBody(
    @SerializedName("model")
    val model: String,
    @SerializedName("prompt")
    val messages: List<String>,
)

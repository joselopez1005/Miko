package com.example.miko.data.remote

import com.google.gson.annotations.SerializedName

data class MessageBody(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)

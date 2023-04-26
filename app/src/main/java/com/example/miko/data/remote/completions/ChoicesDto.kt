package com.example.miko.data.remote.completions

import com.google.gson.annotations.SerializedName

data class ChoicesDto (
    @SerializedName("message")
    val message: MessagesDto
)
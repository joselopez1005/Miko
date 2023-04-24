package com.example.miko.data.remote.completions

import com.google.gson.annotations.SerializedName

data class CompletionsDto(
    @SerializedName("message")
    val message: List<MessagesDto>

)

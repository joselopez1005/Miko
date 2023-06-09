package com.example.miko.data.remote.completions

import com.google.gson.annotations.SerializedName

data class CompletionsDto(
    @SerializedName("created")
    val created: Long,
    @SerializedName("choices")
    val choices: List<ChoicesDto>
)

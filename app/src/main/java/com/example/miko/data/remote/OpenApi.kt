package com.example.miko.data.remote

import com.example.miko.data.remote.completions.CompletionsDto
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenApi {

    @POST("v1/chat/completions")
    suspend fun getTextCompletion(
        @Body body: PromptBody
    ): CompletionsDto
}
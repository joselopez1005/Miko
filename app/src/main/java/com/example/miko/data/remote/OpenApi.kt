package com.example.miko.data.remote

import com.example.miko.data.remote.completions.CompletionsDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenApi {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    suspend fun getTextCompletion(
        @Header("Authorization") auth: String,
        @Body request: PromptBody
    ): CompletionsDto
}
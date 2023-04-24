package com.example.miko.data.mappers

import com.example.miko.data.remote.completions.CompletionsDto
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message

fun CompletionsDto.toCompletions(): Completions {
    return Completions(message.map {
        Message(it.role, it.content)
    })
}
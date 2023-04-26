package com.example.miko.data.mappers

import com.example.miko.data.remote.completions.CompletionsDto
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message

fun CompletionsDto.toCompletions(): Completions {
    val list = mutableListOf<Message>()
    choices.forEach {
        list.add(Message(it.message.role, it.message.content))
    }
    return Completions(list)
}
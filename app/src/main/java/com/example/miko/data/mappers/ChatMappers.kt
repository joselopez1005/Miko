package com.example.miko.data.mappers

import com.example.miko.data.local.ChatMessageEntity
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

fun CompletionsDto.toChatMessageEntity(): ChatMessageEntity {
    choices.first().apply {
        return ChatMessageEntity(role = this.message.role, content = this.message.content)
    }
}

fun Message.toChatMessageEntity(): ChatMessageEntity {
    return ChatMessageEntity(role = role, content = content)
}

fun ChatMessageEntity.toCompletions(): Completions {
    return Completions(mutableListOf(Message(role = role, content = content)))
}

fun ChatMessageEntity.toMessage(): Message {
    return Message(role = role, content = content)
}
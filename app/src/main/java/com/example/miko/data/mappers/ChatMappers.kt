package com.example.miko.data.mappers

import com.example.miko.data.local.ChatMessageEntity
import com.example.miko.data.remote.completions.CompletionsDto
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message
import java.time.LocalDateTime


fun CompletionsDto.toChatMessageEntity(): ChatMessageEntity {
    choices.first().apply {
        return ChatMessageEntity(role = this.message.role, content = this.message.content, LocalDateTime.now().toString())
    }
}

fun Message.toChatMessageEntity(): ChatMessageEntity {
    return ChatMessageEntity(role = role, content = content, time = time)
}

fun ChatMessageEntity.toCompletions(): Completions {
    return Completions(mutableListOf(Message(role = role, content = content, time = time)))
}

fun ChatMessageEntity.toMessage(): Message {
    return Message(role = role, content = content, time = time)
}
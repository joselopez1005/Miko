package com.example.miko.data.mappers

import com.example.miko.data.local.ChatMessageEntity
import com.example.miko.data.remote.completions.CompletionsDto
import com.example.miko.domain.chat.ChatItem
import com.example.miko.domain.chat.Completions
import com.example.miko.domain.chat.Message
import com.example.miko.domain.chat.ProfileInfo
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.TimeZone


// Using System might give me an issue on IOS. Look back at this if that is the case
// Used to convert Completions to a ChatMessageEntity used to store response into DB
fun CompletionsDto.toChatMessageEntity(profileInfo: ProfileInfo): ChatMessageEntity {
    choices.first().apply {
        return ChatMessageEntity(role = message.role, content = message.content, time = created, chatId = profileInfo.chatId, name = profileInfo.name, profilePictureRef = profileInfo.iconRes)
    }
}

// Used to convert user message to ChatMessageEntity used to store response into DB
fun Message.toChatMessageEntity(profileInfo: ProfileInfo): ChatMessageEntity {
    return ChatMessageEntity(role = role, content = content, time = time.toEpochSecond(ZoneOffset.systemDefault().rules.getOffset(time)), chatId = profileInfo.chatId, name = profileInfo.name, profilePictureRef = profileInfo.iconRes)
}

// Used to convert ChatMessageEntity to Completions which is what we will be using in domain
fun ChatMessageEntity.toCompletions(): Completions {
    return Completions(mutableListOf(Message(role = role, content = content, time =  LocalDateTime.ofInstant(Instant.ofEpochSecond(time), TimeZone.getDefault().toZoneId()))))
}

// Used to convert ChatMessageEntity to one message which is used when obtaining one message
fun ChatMessageEntity.toMessage(): Message {
    return Message(role = role, content = content, time = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), TimeZone.getDefault().toZoneId()))
}

// Used to convert ChatMessageEntity to ChatItem used to represent information in HomeScreen
fun ChatMessageEntity.toChatItem(): ChatItem {
    return ChatItem(name = chatId.toString(), latestMessage = content, chatId = chatId, latestTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(time), TimeZone.getDefault().toZoneId()))
}
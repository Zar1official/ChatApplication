package ru.zar1official.chatapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MessageEntity(
    val messageId: Int = 0,
    val senderUserName: String,
    val text: String,
    val timestamp: Long
)
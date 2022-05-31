package ru.zar1official.chatapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DialogMessageEntity(
    val messageId: Int = 0,
    val dialogId: Int,
    val sender: String,
    val timestamp: Long,
    val text: String
)
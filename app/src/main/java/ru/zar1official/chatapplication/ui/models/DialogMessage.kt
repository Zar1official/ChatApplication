package ru.zar1official.chatapplication.ui.models

class DialogMessage(
    val dialogId: Int,
    override val messageId: Int = 0,
    override val username: String,
    override val timestamp: Long,
    override val text: String,
    override val isOwnMessage: Boolean
) : Message(
    username = username,
    text = text,
    isOwnMessage = isOwnMessage,
    timestamp = timestamp
)
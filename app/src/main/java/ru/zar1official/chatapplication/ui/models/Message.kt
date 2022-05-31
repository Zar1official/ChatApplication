package ru.zar1official.chatapplication.ui.models

open class Message(
    open val messageId: Int = 0,
    open val username: String,
    open val text: String,
    open val isOwnMessage: Boolean,
    open val timestamp: Long
)
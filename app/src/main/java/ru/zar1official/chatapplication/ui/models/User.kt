package ru.zar1official.chatapplication.ui.models

data class User(
    val userId: Int = 0,
    val username: String,
    val password: String,
    val isMe: Boolean = false
)
package ru.zar1official.chatapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketEntity(val type: String, val value: String)
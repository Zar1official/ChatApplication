package ru.zar1official.chatapplication.data.models

import kotlinx.serialization.Serializable

@Serializable
data class DialogEntity(val dialogId: Int = 0, val creatorId: Int, val companionId: Int)
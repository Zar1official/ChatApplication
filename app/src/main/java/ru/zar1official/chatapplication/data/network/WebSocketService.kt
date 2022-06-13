package ru.zar1official.chatapplication.data.network

import kotlinx.coroutines.flow.Flow
import models.DialogMessageEntity
import models.GeneralChatMessageEntity

interface WebSocketService {
    suspend fun initGeneralChatSession(username: String): Boolean
    suspend fun sendMessage(messageText: String)
    suspend fun closeGeneralChatSession()
    suspend fun isSession(): Boolean

    suspend fun initDialogChatSession(username: String, dialogId: Int): Boolean
    suspend fun sendDialogMessage(messageText: String)

    fun observeMessages(): Flow<GeneralChatMessageEntity>
    fun observeDialogMessages(): Flow<DialogMessageEntity>

    companion object {
        private const val base_url = "ws://61ea-93-100-81-222.eu.ngrok.io"
        const val usernameParam = "username"
        const val dialogIdParam = "dialog_id"
        private fun buildUrl(path: String) = "${base_url}/$path"
    }

    sealed class Paths(val path: String) {
        object ConnectChat : Paths(buildUrl("chat-socket"))
    }

    sealed class EntityType(val type: String) {
        object Message : EntityType("message")
        object DialogMessage : EntityType("dialog-message")
    }
}
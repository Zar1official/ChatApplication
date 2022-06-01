package ru.zar1official.chatapplication.data.network

import kotlinx.coroutines.flow.Flow
import ru.zar1official.chatapplication.data.models.DialogMessageEntity
import ru.zar1official.chatapplication.data.models.MessageEntity

interface WebSocketService {
    suspend fun initGeneralChatSession(username: String): Boolean
    suspend fun sendMessage(messageText: String)
    suspend fun closeGeneralChatSession()
    suspend fun isSession(): Boolean

    suspend fun initDialogChatSession(username: String, dialogId: Int): Boolean
    suspend fun sendDialogMessage(messageText: String)

    fun observeMessages(): Flow<MessageEntity>
    fun observeDialogMessages(): Flow<DialogMessageEntity>

    companion object {
        private const val base_url = "ws://dbf0-93-100-81-222.eu.ngrok.io"
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
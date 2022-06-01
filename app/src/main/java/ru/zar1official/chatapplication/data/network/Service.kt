package ru.zar1official.chatapplication.data.network

import ru.zar1official.chatapplication.data.models.DialogEntity
import ru.zar1official.chatapplication.data.models.DialogMessageEntity
import ru.zar1official.chatapplication.data.models.MessageEntity
import ru.zar1official.chatapplication.data.models.UserEntity

interface Service {
    suspend fun login(user: UserEntity): UserEntity
    suspend fun register(user: UserEntity): UserEntity
    suspend fun getAllMessages(): List<MessageEntity>
    suspend fun getMessagesFromTimeStamp(timestamp: Long): List<MessageEntity>
    suspend fun getUsers(): List<UserEntity>
    suspend fun getDialogMessages(dialogId: Int): List<DialogMessageEntity>
    suspend fun getDialog(userId: Int, companionId: Int): DialogEntity
    suspend fun getDialogs(userId: Int): List<DialogEntity>

    companion object {
        private const val base_url = "https://dbf0-93-100-81-222.eu.ngrok.io"
        private fun buildUrl(path: String) = "$base_url/$path"

        const val timestampParam = "timestamp"
        const val dialogIdParam = "dialog_id"
        const val userIdParam = "user_id"
        const val companionIdParam = "companion_id"
        const val usernameParam = "username"
    }

    sealed class Paths(val path: String) {
        object Login : Paths(buildUrl("login"))
        object Register : Paths(buildUrl("register"))
        object Messages : Paths(buildUrl("messages"))
        object Users : Paths(buildUrl("users"))
        object DialogMessages : Paths(buildUrl("dialog-messages"))
        object GetDialog : Paths(buildUrl("g-dialog"))
        object DialogList : Paths(buildUrl("dialog-list"))
    }
}
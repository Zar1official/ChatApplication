package ru.zar1official.chatapplication.data.network

import models.DialogEntity
import models.DialogMessageEntity
import models.GeneralChatMessageEntity
import models.UserEntity

interface Service {
    suspend fun login(user: UserEntity): UserEntity
    suspend fun register(user: UserEntity): UserEntity
    suspend fun getAllMessages(): List<GeneralChatMessageEntity>
    suspend fun getMessagesFromTimeStamp(timestamp: Long): List<GeneralChatMessageEntity>
    suspend fun getUsers(): List<UserEntity>
    suspend fun getDialogMessages(dialogId: Int): List<DialogMessageEntity>
    suspend fun getDialog(userId: Int, companionId: Int): DialogEntity
    suspend fun getDialogs(userId: Int): List<DialogEntity>

    companion object {
        private const val base_url = "2aff-93-100-81-222.eu.ngrok.io"
        fun buildUrl(path: String) = "https://$base_url/$path"
    }
}
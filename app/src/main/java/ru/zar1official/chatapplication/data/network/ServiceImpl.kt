package ru.zar1official.chatapplication.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import models.DialogEntity
import models.DialogMessageEntity
import models.GeneralChatMessageEntity
import models.UserEntity
import javax.inject.Inject

class ServiceImpl @Inject constructor(private val client: HttpClient) : Service {

    override suspend fun login(user: UserEntity): UserEntity {
        return client.post(Service.Paths.Login.path) {
            contentType(ContentType.Application.Json)
            body = user
        }
    }

    override suspend fun register(user: UserEntity): UserEntity {
        return client.post(Service.Paths.Register.path) {
            contentType(ContentType.Application.Json)
            body = user
        }
    }

    override suspend fun getAllMessages(): List<GeneralChatMessageEntity> {
        return client.get(Service.Paths.Messages.path)
    }

    override suspend fun getMessagesFromTimeStamp(timestamp: Long): List<GeneralChatMessageEntity> {
        return client.get(Service.Paths.Messages.path) {
            parameter(Service.timestampParam, timestamp)
        }
    }

    override suspend fun getUsers(): List<UserEntity> {
        return client.get(Service.Paths.Users.path)
    }

    override suspend fun getDialogMessages(dialogId: Int): List<DialogMessageEntity> {
        return client.get(Service.Paths.DialogMessages.path) {
            parameter(Service.dialogIdParam, dialogId)
        }
    }

    override suspend fun getDialog(userId: Int, companionId: Int): DialogEntity {
        return client.get(Service.Paths.GetDialog.path) {
            parameter(Service.userIdParam, userId)
            parameter(Service.companionIdParam, companionId)
        }
    }

    override suspend fun getDialogs(userId: Int): List<DialogEntity> {
        return client.get(Service.Paths.DialogList.path) {
            parameter(Service.userIdParam, userId)
        }
    }
}
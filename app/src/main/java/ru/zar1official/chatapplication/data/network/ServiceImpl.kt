package ru.zar1official.chatapplication.data.network

import constants.Params
import constants.Routes
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
        return client.post(Service.buildUrl(Routes.Login.path)) {
            contentType(ContentType.Application.Json)
            body = user
        }
    }

    override suspend fun register(user: UserEntity): UserEntity {
        return client.post(Service.buildUrl(Routes.Register.path)) {
            contentType(ContentType.Application.Json)
            body = user
        }
    }

    override suspend fun getAllMessages(): List<GeneralChatMessageEntity> {
        return client.get(Service.buildUrl(Routes.GeneralChatMessages.path))
    }

    override suspend fun getMessagesFromTimeStamp(timestamp: Long): List<GeneralChatMessageEntity> {
        return client.get(Service.buildUrl(Routes.GeneralChatMessages.path)) {
            parameter(Params.TIMESTAMP_PARAM, timestamp)
        }
    }

    override suspend fun getUsers(): List<UserEntity> {
        return client.get(Service.buildUrl(Routes.Users.path))
    }

    override suspend fun getDialogMessages(dialogId: Int): List<DialogMessageEntity> {
        return client.get(Service.buildUrl(Routes.DialogMessages.path)) {
            parameter(Params.DIALOG_ID_PARAM, dialogId)
        }
    }

    override suspend fun getDialog(userId: Int, companionId: Int): DialogEntity {
        return client.get(Service.buildUrl(Routes.GetDialog.path)) {
            parameter(Params.USER_ID_PARAM, userId)
            parameter(Params.COMPANION_ID_PARAM, companionId)
        }
    }

    override suspend fun getDialogs(userId: Int): List<DialogEntity> {
        return client.get(Service.buildUrl(Routes.DialogList.path)) {
            parameter(Params.USER_ID_PARAM, userId)
        }
    }
}
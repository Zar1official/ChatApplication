package ru.zar1official.chatapplication.data.network

import constants.Params
import constants.Routes
import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.DialogMessageEntity
import models.GeneralChatMessageEntity
import models.SocketModel
import models.SocketModelType
import javax.inject.Inject

class WebSocketServiceImpl @Inject constructor(private val client: HttpClient) : WebSocketService {

    private var socketSession: WebSocketSession? = null

    override suspend fun initGeneralChatSession(username: String): Boolean {
        socketSession = client.webSocketSession {
            url(WebSocketService.buildUrl(Routes.GeneralChat.path))
            parameter(Params.USERNAME_PARAM, username)
        }
        return isSession()
    }

    override fun observeMessages(): Flow<GeneralChatMessageEntity> {
        return socketSession?.incoming
            ?.receiveAsFlow()
            ?.filter { it is Frame.Text }
            ?.map {
                val json = (it as? Frame.Text)?.readText() ?: ""
                Json.decodeFromString<SocketModel>(json)
            }
            ?.filter { it.type == SocketModelType.GeneralChatMessage.type }
            ?.map {
                Json.decodeFromString(it.value)
            }
            ?: flow { }
    }

    override fun observeDialogMessages(): Flow<DialogMessageEntity> {
        return socketSession?.incoming
            ?.receiveAsFlow()
            ?.filter { it is Frame.Text }
            ?.map {
                val json = (it as? Frame.Text)?.readText() ?: ""
                Json.decodeFromString<SocketModel>(json)
            }
            ?.filter { it.type == SocketModelType.DialogMessage.type }
            ?.map {
                Json.decodeFromString(it.value)
            }
            ?: flow { }
    }


    override suspend fun sendMessage(messageText: String) {
        val parsedEntity = Json.encodeToString(
            SocketModel(
                type = SocketModelType.GeneralChatMessage.type,
                value = messageText
            )
        )
        socketSession?.send(Frame.Text(parsedEntity))
    }

    override suspend fun closeGeneralChatSession() {
        socketSession?.close()
    }

    override suspend fun isSession(): Boolean {
        return socketSession?.isActive ?: false
    }

    override suspend fun initDialogChatSession(username: String, dialogId: Int): Boolean {
        socketSession = client.webSocketSession {
            url(WebSocketService.buildUrl(Routes.GeneralChat.path))
            parameter(Params.USERNAME_PARAM, username)
            parameter(Params.DIALOG_ID_PARAM, dialogId)
        }
        return isSession()
    }

    override suspend fun sendDialogMessage(messageText: String) {
        val parsedEntity = Json.encodeToString(
            SocketModel(
                type = SocketModelType.DialogMessage.type,
                value = messageText
            )
        )
        socketSession?.send(Frame.Text(parsedEntity))
    }
}
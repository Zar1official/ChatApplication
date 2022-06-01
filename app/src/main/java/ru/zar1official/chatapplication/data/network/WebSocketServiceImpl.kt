package ru.zar1official.chatapplication.data.network

import io.ktor.client.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.zar1official.chatapplication.data.models.DialogMessageEntity
import ru.zar1official.chatapplication.data.models.MessageEntity
import ru.zar1official.chatapplication.data.models.WebSocketEntity
import javax.inject.Inject

class WebSocketServiceImpl @Inject constructor(private val client: HttpClient) : WebSocketService {

    private var socketSession: WebSocketSession? = null

    override suspend fun initGeneralChatSession(username: String): Boolean {
        socketSession = client.webSocketSession {
            url(WebSocketService.Paths.ConnectChat.path)
            parameter(WebSocketService.usernameParam, username)
        }
        return isSession()
    }

    override fun observeMessages(): Flow<MessageEntity> {
        return socketSession?.incoming
            ?.receiveAsFlow()
            ?.filter { it is Frame.Text }
            ?.map {
                val json = (it as? Frame.Text)?.readText() ?: ""
                Json.decodeFromString<WebSocketEntity>(json)
            }
            ?.filter { it.type == WebSocketService.EntityType.Message.type }
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
                Json.decodeFromString<WebSocketEntity>(json)
            }
            ?.filter { it.type == WebSocketService.EntityType.DialogMessage.type }
            ?.map {
                Json.decodeFromString(it.value)
            }
            ?: flow { }
    }


    override suspend fun sendMessage(messageText: String) {
        val parsedEntity = Json.encodeToString(
            WebSocketEntity(
                type = WebSocketService.EntityType.Message.type,
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
            url(WebSocketService.Paths.ConnectChat.path)
            parameter(WebSocketService.usernameParam, username)
            parameter(WebSocketService.dialogIdParam, dialogId)
        }
        return isSession()
    }

    override suspend fun sendDialogMessage(messageText: String) {
        val parsedEntity = Json.encodeToString(
            WebSocketEntity(
                type = WebSocketService.EntityType.DialogMessage.type,
                value = messageText
            )
        )
        socketSession?.send(Frame.Text(parsedEntity))
    }
}
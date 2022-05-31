package ru.zar1official.chatapplication.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.zar1official.chatapplication.data.manager.SessionManager
import ru.zar1official.chatapplication.data.mappers.DialogMapper
import ru.zar1official.chatapplication.data.mappers.DialogMessageMapper
import ru.zar1official.chatapplication.data.mappers.MessageMapper
import ru.zar1official.chatapplication.data.mappers.UserMapper
import ru.zar1official.chatapplication.data.network.Service
import ru.zar1official.chatapplication.data.network.WebSocketService
import ru.zar1official.chatapplication.domain.repository.Repository
import ru.zar1official.chatapplication.ui.models.Dialog
import ru.zar1official.chatapplication.ui.models.DialogMessage
import ru.zar1official.chatapplication.ui.models.Message
import ru.zar1official.chatapplication.ui.models.User
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: Service,
    private val webSocketService: WebSocketService,
    private val sessionManager: SessionManager,
    private val userMapper: UserMapper,
    private val messageMapper: MessageMapper,
    private val dialogMapper: DialogMapper,
    private val dialogMessageMapper: DialogMessageMapper
) :
    Repository {
    override suspend fun getSession(): User {
        val session = sessionManager.getSession()
        return userMapper.mapFromEntity(session, session)
    }

    override suspend fun clearSession() {
        sessionManager.clearSession()
    }

    override suspend fun updateSession(user: User) {
        sessionManager.updateSession(userMapper.mapToEntity(user))
    }

    override suspend fun login(user: User): User {
        val session = sessionManager.getSession()
        return userMapper.mapFromEntity(service.login(userMapper.mapToEntity(user)), session)
    }

    override suspend fun register(user: User): User {
        val session = sessionManager.getSession()
        return userMapper.mapFromEntity(service.register(userMapper.mapToEntity(user)), session)
    }

    override suspend fun getAllMessages(): List<Message> {
        val session = sessionManager.getSession()
        return service.getAllMessages().map {
            messageMapper.mapFromEntity(it, session)
        }
    }

    override suspend fun getUsers(): List<User> {
        val session = sessionManager.getSession()
        return service.getUsers().map { userMapper.mapFromEntity(it, session) }
    }

    override suspend fun closeGeneralWebSocketSession() {
        webSocketService.closeGeneralChatSession()
    }

    override suspend fun initGeneralWebSocketSession(): Boolean {
        val username = sessionManager.getSession().username
        return webSocketService.initGeneralChatSession(username)
    }

    override suspend fun initDialogWebSocketSession(dialogId: Int): Boolean {
        val username = sessionManager.getSession().username
        return webSocketService.initDialogChatSession(username, dialogId)
    }

    override suspend fun observeMessages(): Flow<Message> {
        val session = sessionManager.getSession()
        return webSocketService.observeMessages().map { messageMapper.mapFromEntity(it, session) }
    }

    override suspend fun observeDialogMessages(): Flow<DialogMessage> {
        val session = sessionManager.getSession()
        return webSocketService.observeDialogMessages()
            .map { dialogMessageMapper.mapFromEntity(it, session) }
    }

    override suspend fun sendDialogMessage(messageText: String) {
        webSocketService.sendDialogMessage(messageText)
    }

    override suspend fun sendMessage(messageText: String) {
        webSocketService.sendMessage(messageText)
    }

    override suspend fun getDialog(companionId: Int): Dialog {
        val session = sessionManager.getSession()
        return dialogMapper.mapFromEntity(service.getDialog(session.userId, companionId))
    }

    override suspend fun getDialogs(userId: Int): List<Dialog> {
        return service.getDialogs(userId).map { dialogMapper.mapFromEntity(it) }
    }

    override suspend fun getDialogMessages(dialogId: Int): List<DialogMessage> {
        val session = sessionManager.getSession()
        return service.getDialogMessages(dialogId)
            .map { dialogMessageMapper.mapFromEntity(it, session) }
    }
}
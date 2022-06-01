package ru.zar1official.chatapplication.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.zar1official.chatapplication.ui.models.Dialog
import ru.zar1official.chatapplication.ui.models.DialogMessage
import ru.zar1official.chatapplication.ui.models.Message
import ru.zar1official.chatapplication.ui.models.User

interface Repository {
    suspend fun getSession(): User
    suspend fun clearSession()
    suspend fun updateSession(user: User)
    suspend fun login(user: User): User
    suspend fun register(user: User): User
    suspend fun getUsers(): List<User>

    suspend fun closeGeneralWebSocketSession()
    suspend fun initGeneralWebSocketSession(): Boolean
    suspend fun initDialogWebSocketSession(dialogId: Int): Boolean

    suspend fun observeMessages(): Flow<Message>
    suspend fun getAllMessages(): List<Message>
    suspend fun sendMessage(messageText: String)

    suspend fun getDialog(companionId: Int): Dialog
    suspend fun getDialogs(userId: Int): List<Dialog>
    suspend fun getDialogMessages(dialogId: Int): List<DialogMessage>
    suspend fun observeDialogMessages(): Flow<DialogMessage>
    suspend fun sendDialogMessage(messageText: String)

    suspend fun notifyUser(title: String, text: String, id: Int)
}
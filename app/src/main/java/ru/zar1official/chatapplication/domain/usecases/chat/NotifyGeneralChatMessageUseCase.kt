package ru.zar1official.chatapplication.domain.usecases.chat

import ru.zar1official.chatapplication.domain.repository.Repository
import ru.zar1official.chatapplication.ui.models.Message
import javax.inject.Inject

class NotifyGeneralChatMessageUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(message: Message) {
        kotlin.runCatching {
            val session = repository.getSession()
            if (session.username != message.username) {
                repository.notifyUser(
                    title = "Общий чат: ${message.username}",
                    text = message.text,
                    1
                )
            }
        }
    }
}
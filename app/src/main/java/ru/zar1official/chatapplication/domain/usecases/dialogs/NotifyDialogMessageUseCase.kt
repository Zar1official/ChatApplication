package ru.zar1official.chatapplication.domain.usecases.dialogs

import ru.zar1official.chatapplication.domain.repository.Repository
import ru.zar1official.chatapplication.ui.models.DialogMessage
import javax.inject.Inject

class NotifyDialogMessageUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(message: DialogMessage) {
        kotlin.runCatching {
            val session = repository.getSession()
            if (session.username != message.username) {
                repository.notifyUser(
                    title = message.username,
                    text = message.text,
                    2
                )
            }
        }
    }
}
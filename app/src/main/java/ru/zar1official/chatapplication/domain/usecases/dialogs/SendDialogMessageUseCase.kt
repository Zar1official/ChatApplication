package ru.zar1official.chatapplication.domain.usecases.dialogs

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import ru.zar1official.chatapplication.domain.usecases.general_chat.SendMessageUseCase
import javax.inject.Inject


class SendDialogMessageUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(messageText: String) = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            if (!SendMessageUseCase.isMessageValid(messageText)) {
                return@withContext Result.failure(IllegalArgumentException())
            }
            repository.sendDialogMessage(
                messageText = messageText
            )
        }
    }
}
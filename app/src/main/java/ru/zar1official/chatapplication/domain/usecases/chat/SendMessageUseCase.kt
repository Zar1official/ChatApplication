package ru.zar1official.chatapplication.domain.usecases.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(messageText: String) = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            if (!isMessageValid(messageText)) {
                return@withContext Result.failure(IllegalArgumentException())
            }
            repository.sendMessage(messageText)
        }
    }

    companion object {
        fun isMessageValid(messageText: String) = messageText.length in 1..500
    }
}
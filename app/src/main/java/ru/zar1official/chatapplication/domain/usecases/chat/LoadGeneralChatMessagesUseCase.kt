package ru.zar1official.chatapplication.domain.usecases.chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import ru.zar1official.chatapplication.ui.models.Message
import javax.inject.Inject

class LoadGeneralChatMessagesUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): Result<List<Message>> = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            repository.getAllMessages()
        }
    }
}
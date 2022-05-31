package ru.zar1official.chatapplication.domain.usecases.general_chat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import javax.inject.Inject

class CloseGeneralWebSocketSessionUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        repository.closeGeneralWebSocketSession()
    }
}
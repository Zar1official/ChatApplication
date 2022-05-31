package ru.zar1official.chatapplication.domain.usecases.dialogs

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import javax.inject.Inject

class LoadDialogsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(userId: Int) = withContext(Dispatchers.IO) {
        kotlin.runCatching { repository.getDialogs(userId) }
    }
}
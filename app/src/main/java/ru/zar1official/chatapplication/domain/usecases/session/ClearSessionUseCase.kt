package ru.zar1official.chatapplication.domain.usecases.session

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import javax.inject.Inject

class ClearSessionUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        repository.clearSession()
    }
}
package ru.zar1official.chatapplication.domain.usecases.session

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import ru.zar1official.chatapplication.ui.models.User
import javax.inject.Inject

class UpdateSessionUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(session: User) = withContext(Dispatchers.IO) {
        repository.updateSession(session)
    }
}
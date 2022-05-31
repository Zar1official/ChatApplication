package ru.zar1official.chatapplication.domain.usecases.users

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import ru.zar1official.chatapplication.ui.models.User
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(user: User) = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            if (!LoginUserUseCase.isLoginPassValid(user)) {
                return@withContext Result.failure(IllegalArgumentException())
            }
            val userModel = repository.register(user)
            repository.updateSession(userModel)
        }
    }
}
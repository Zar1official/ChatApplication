package ru.zar1official.chatapplication.domain.usecases.users

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.zar1official.chatapplication.domain.repository.Repository
import ru.zar1official.chatapplication.ui.models.User
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(user: User) = withContext(Dispatchers.IO) {
        kotlin.runCatching {
            if (!isLoginPassValid(user)) {
                return@withContext Result.failure(IllegalArgumentException())
            }
            val model = repository.login(user)
            repository.updateSession(model)
        }
    }

    companion object {
        fun isLoginPassValid(user: User) =
            user.username.length in 1..25 && user.password.length in 1..25
    }
}

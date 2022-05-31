package ru.zar1official.chatapplication.ui.screens.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zar1official.chatapplication.domain.usecases.session.GetCurrentSessionUseCase
import ru.zar1official.chatapplication.domain.usecases.users.LoginUserUseCase
import ru.zar1official.chatapplication.navigation.Screens
import ru.zar1official.chatapplication.ui.screens.contract.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val loginUserUseCase: LoginUserUseCase
) : BaseViewModel() {

    fun onCheckSession() {
        viewModelScope.launch {
            val session = getCurrentSessionUseCase()
            loginUserUseCase(session)
                .onSuccess {
                    navigateTo(Screens.GeneralChatScreen.route)
                }.onFailure {
                    navigateTo(Screens.LoginScreen.route)
                }
        }
    }
}
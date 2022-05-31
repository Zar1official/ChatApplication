package ru.zar1official.chatapplication.ui.screens.login

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zar1official.chatapplication.R
import ru.zar1official.chatapplication.domain.usecases.users.LoginUserUseCase
import ru.zar1official.chatapplication.domain.usecases.users.RegisterUserUseCase
import ru.zar1official.chatapplication.navigation.Screens
import ru.zar1official.chatapplication.ui.models.User
import ru.zar1official.chatapplication.ui.screens.contract.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : BaseViewModel() {
    private val _login = MutableLiveData<String>()
    val login = _login

    private val _password = MutableLiveData<String>()
    val password = _password

    private val _repeatPassword = MutableLiveData<String>()
    val repeatPassword: LiveData<String> = _repeatPassword

    fun onLogin() {
        val user = User(username = login.value.orEmpty(), password = password.value.orEmpty())
        viewModelScope.launch {
            loginUserUseCase(user)
                .onSuccess {
                    navigateTo(Screens.GeneralChatScreen.route, true)
                }.onFailure {
                    when (it) {
                        is java.lang.IllegalArgumentException -> showMessage(R.string.incorrect_fields_message)
                        is Resources.NotFoundException -> showMessage(R.string.no_such_user_message)
                        else -> showMessage(R.string.default_error)
                    }
                }
        }
    }

    fun onRegister() {
        val user = User(username = login.value.orEmpty(), password = password.value.orEmpty())
        viewModelScope.launch {
            registerUserUseCase(user)
                .onSuccess {
                    navigateTo(Screens.GeneralChatScreen.route, true)
                }.onFailure {
                    when (it) {
                        is Resources.NotFoundException -> showMessage(R.string.user_exists_message)
                        is IllegalArgumentException -> showMessage(R.string.incorrect_fields_message)
                        else -> {
                            showMessage(R.string.default_error)
                        }
                    }
                }
        }
    }

    fun onChangeLogin(value: String) {
        _login.value = value
    }

    fun onChangePassword(value: String) {
        _password.value = value
    }

    fun onChangeRepeatPassword(value: String) {
        _repeatPassword.value = value
    }

    fun onNavigateRegister() {
        navigateTo(Screens.RegisterScreen.route)
    }
}
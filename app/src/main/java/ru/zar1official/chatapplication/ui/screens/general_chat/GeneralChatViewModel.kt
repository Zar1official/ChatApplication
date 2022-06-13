package ru.zar1official.chatapplication.ui.screens.general_chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zar1official.chatapplication.R
import ru.zar1official.chatapplication.domain.usecases.chat.*
import ru.zar1official.chatapplication.domain.usecases.dialogs.GetDialogUseCase
import ru.zar1official.chatapplication.domain.usecases.session.ClearSessionUseCase
import ru.zar1official.chatapplication.domain.usecases.users.LoadUsersUseCase
import ru.zar1official.chatapplication.navigation.Screens
import ru.zar1official.chatapplication.navigation.Screens.Companion.buildPath
import ru.zar1official.chatapplication.ui.models.Message
import ru.zar1official.chatapplication.ui.models.User
import ru.zar1official.chatapplication.ui.screens.contract.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class GeneralChatViewModel @Inject constructor(
    private val loadGeneralChatMessagesUseCase: LoadGeneralChatMessagesUseCase,
    private val loadUsersUseCase: LoadUsersUseCase,
    private val initGeneralWebSocketSessionUseCase: InitGeneralWebSocketSessionUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
    private val closeGeneralWebSocketSessionUseCase: CloseGeneralWebSocketSessionUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
    private val getDialogUseCase: GetDialogUseCase,
    private val notifyGeneralChatMessageUseCase: NotifyGeneralChatMessageUseCase
) :
    BaseViewModel() {
    private val _messageText = MutableLiveData<String>()
    val messageText: LiveData<String> = _messageText

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    private val _users = MutableLiveData<List<User>>()

    private val _filteredUsers = MutableLiveData<List<User>>()
    val filteredUsers: LiveData<List<User>> = _filteredUsers

    private val _userFilter = MutableLiveData<String>()
    val userFilter: LiveData<String> = _userFilter

    private val lastTimestamp = MutableLiveData<Long>()

    fun onUploadMessages() {
        viewModelScope.launch {
        }
    }

    fun onSendMessage() {
        val message = messageText.value.orEmpty()
        viewModelScope.launch {
            sendMessageUseCase(message)
                .onSuccess {
                    _messageText.postValue("")
                }
                .onFailure {
                    when (it) {
                        is IllegalArgumentException -> showMessage(R.string.incorrect_message_text_message)
                        else -> showMessage(R.string.default_error)
                    }
                }
        }
    }

    private fun onLoadMessages() {
        viewModelScope.launch {
            loadGeneralChatMessagesUseCase()
                .onSuccess {
                    _messages.postValue(it)
                    completeLoading()
                }.onFailure {
                    showMessage(R.string.loading_messages_error)
                }
        }
    }

    private fun onLoadUsers() {
        viewModelScope.launch {
            loadUsersUseCase()
                .onSuccess {
                    _users.postValue(it)
                    val filterValue = _userFilter.value.orEmpty()
                    _filteredUsers.postValue(it.filter { f -> f.username.contains(filterValue) })
                }
                .onFailure {
                    showMessage(R.string.default_error)
                }
        }
    }

    private suspend fun observeMessages() {
        observeMessagesUseCase()
            .onSuccess {
                it.collect { message ->
                    val tempList = messages.value?.toMutableList() ?: mutableListOf()
                    tempList.add(0, message)
                    _messages.postValue(tempList)
                    notifyGeneralChatMessageUseCase(message)
                }
            }
            .onFailure {
                showMessage(R.string.default_error)
            }
    }

    fun connectToGeneralChat() {
        startLoading()
        onLoadMessages()
        onLoadUsers()
        viewModelScope.launch {
            initGeneralWebSocketSessionUseCase.invoke().onSuccess {
                if (it) {
                    observeMessages()
                }
            }.onFailure {
                showMessage(R.string.default_error)
            }
        }
    }

    fun disconnectGeneralChat() {
        viewModelScope.launch {
            closeGeneralWebSocketSessionUseCase()
        }
    }

    fun onChangeMessage(text: String) {
        _messageText.value = text
    }

    fun onLogout() {
        viewModelScope.launch {
            clearSessionUseCase()
            navigateTo(Screens.LoginScreen.route, true)
        }
    }

    fun onSearchUser(username: String) {
        _userFilter.postValue(username)
        val users = _users.value ?: listOf()
        _filteredUsers.postValue(users.filter { f -> f.username.contains(username) })
    }

    fun onNavigateDialogScreen(user: User) {
        viewModelScope.launch {
            getDialogUseCase(user.userId)
                .onSuccess { dialog ->
                    navigateTo(
                        Screens.DialogScreen.buildPath(dialog.dialogId, user.username)
                    )
                }
                .onFailure {
                    showMessage(R.string.default_error)
                }
        }
    }

    fun onNavigateDialogListScreen() {
        showTextMessage("Not yet implemented")
    }
}
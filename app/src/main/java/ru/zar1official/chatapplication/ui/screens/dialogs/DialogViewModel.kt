package ru.zar1official.chatapplication.ui.screens.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.zar1official.chatapplication.R
import ru.zar1official.chatapplication.domain.usecases.dialogs.InitDialogWebSocketSessionUseCase
import ru.zar1official.chatapplication.domain.usecases.dialogs.LoadDialogMessagesUseCase
import ru.zar1official.chatapplication.domain.usecases.dialogs.ObserveDialogMessagesUseCase
import ru.zar1official.chatapplication.domain.usecases.dialogs.SendDialogMessageUseCase
import ru.zar1official.chatapplication.domain.usecases.general_chat.CloseGeneralWebSocketSessionUseCase
import ru.zar1official.chatapplication.ui.models.DialogMessage
import ru.zar1official.chatapplication.ui.screens.contract.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DialogViewModel @Inject constructor(
    private val loadDialogMessagesUseCase: LoadDialogMessagesUseCase,
    private val initDialogWebSocketSessionUseCase: InitDialogWebSocketSessionUseCase,
    private val closeGeneralWebSocketSessionUseCase: CloseGeneralWebSocketSessionUseCase,
    private val observeDialogMessagesUseCase: ObserveDialogMessagesUseCase,
    private val sendDialogMessageUseCase: SendDialogMessageUseCase
) :
    BaseViewModel() {
    private val _messages = MutableLiveData<List<DialogMessage>>()
    val messages: LiveData<List<DialogMessage>> = _messages

    private val _messageText = MutableLiveData<String>()
    val messageText: LiveData<String> = _messageText

    fun onGoBack(navController: NavController) {
        navController.popBackStack()
    }

    fun onChangeMessage(text: String) {
        _messageText.value = text
    }

    private fun onLoadMessages(dialogIdValue: Int) {
        viewModelScope.launch {
            loadDialogMessagesUseCase(dialogIdValue)
                .onSuccess {
                    _messages.postValue(it)
                }
                .onFailure {
                    showMessage(R.string.default_error)
                }
        }
    }

    fun connectToDialog(dialogIdValue: Int) {
        onLoadMessages(dialogIdValue)
        viewModelScope.launch {
            initDialogWebSocketSessionUseCase(dialogIdValue)
                .onSuccess {
                    if (it) {
                        observeDialogMessagesUseCase().onSuccess { mes ->
                            mes.collect { dialogMessage ->
                                val tempList = messages.value?.toMutableList() ?: mutableListOf()
                                tempList.add(0, dialogMessage)
                                _messages.postValue(tempList)
                            }
                        }
                            .onFailure {
                                showMessage(R.string.default_error)
                            }
                    }
                }
                .onFailure {
                    showMessage(R.string.default_error)
                }
        }
    }

    fun onSendMessage() {
        val message = messageText.value.orEmpty()
        viewModelScope.launch {
            sendDialogMessageUseCase(message)
                .onFailure {
                    when (it) {
                        is IllegalArgumentException -> showMessage(R.string.incorrect_message_text_message)
                        else -> showMessage(R.string.default_error)
                    }
                }
        }
    }

    fun disconnectDialog() {
        viewModelScope.launch { closeGeneralWebSocketSessionUseCase() }
    }
}
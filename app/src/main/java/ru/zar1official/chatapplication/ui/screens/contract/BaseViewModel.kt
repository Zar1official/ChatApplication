package ru.zar1official.chatapplication.ui.screens.contract

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _sharedFlowMessage = MutableSharedFlow<BaseEvent>()
    val sharedFlowMessage = _sharedFlowMessage.asSharedFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    sealed class BaseEvent {
        class NavigateTo(val route: String, val clearBackStack: Boolean) : BaseEvent()
        class ShowMessage(@StringRes val message: Int) : BaseEvent()
        class ShowTextMessage(val message: String) : BaseEvent()
    }

    protected fun showMessage(@StringRes message: Int) {
        viewModelScope.launch {
            _sharedFlowMessage.emit(BaseEvent.ShowMessage(message))
        }
    }

    protected fun navigateTo(route: String, popBackStack: Boolean = false) {
        viewModelScope.launch {
            _sharedFlowMessage.emit(BaseEvent.NavigateTo(route, popBackStack))
        }
    }

    protected fun showTextMessage(message: String) {
        viewModelScope.launch {
            _sharedFlowMessage.emit(BaseEvent.ShowTextMessage(message))
        }
    }

    protected fun completeLoading() {
        _isLoading.postValue(false)
    }

    protected fun startLoading() {
        _isLoading.postValue(true)
    }
}
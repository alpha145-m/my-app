package com.example.myapp.ui.screens.authentication.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.models.UserModel
import com.example.myapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _loginSuccess = MutableSharedFlow<Boolean>()
    val loginSuccess = _loginSuccess.asSharedFlow()

    fun loginUser(userModel: UserModel) {
        _isLoading.value = true
        _message.value = ""
        viewModelScope.launch {
            try {
                authRepository.loginUser(userModel)
                _isLoading.value = false
                _message.value = "Success!"
                _loginSuccess.emit(true)
            } catch (e: Exception) {
                _isLoading.value = false
                _message.value = "Oops! ${e.message}"
            }
        }
    }
}

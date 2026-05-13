package com.example.myapp.ui.screens.authentication.ForgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ForgotPasswordViewModel : ViewModel() {

    private val authRepository = AuthRepository

    // State
    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    // Forgot Password Method
    fun forgotPassword(email: String) {

        _isLoading.value = true

        viewModelScope.launch {

            try {

                authRepository.resetPassword(email)

                _isLoading.value = false
                _message.value = "Password reset email sent!"

            } catch (e: Exception) {

                _isLoading.value = false
                _message.value = "Oops! ${e.message}"
            }
        }
    }
}
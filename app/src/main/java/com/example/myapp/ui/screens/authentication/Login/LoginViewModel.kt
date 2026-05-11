package com.example.myapp.ui.screens.authentication.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class LoginUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    // State
    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    // Methods
    fun loginUser(email: String, password: String) {

        _isLoading.value = true

        viewModelScope.launch {

            try {

                authRepository.run {
                    loginUser(
                        email,
                        password
                    )
                }

                _isLoading.value = false
                _message.value = "Login Successful!"

            } catch (e: Exception) {

                _isLoading.value = false
                _message.value = "Oops! Something went wrong: ${e.message}"
            }
        }
    }
}


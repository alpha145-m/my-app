package com.example.myapp.ui.screens.authentication.Register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.models.UserModel
import com.example.myapp.data.repository.AuthRepository
import io.github.jan.supabase.auth.exception.AuthRestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RegisterViewModel : ViewModel() {

    private val authRepository = AuthRepository

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private var _isSuccess = MutableStateFlow(false)
    val isSuccess = _isSuccess.asStateFlow()

    fun registerUser(userModel: UserModel) {
        if (userModel.email.isEmpty() || (userModel.password?.length ?: 0) < 6) {
            _message.value = "Email is required and password must be at least 6 characters."
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true
                _message.value = ""
                _isSuccess.value = false
                
                authRepository.registerUser(userModel)
                
                _isSuccess.value = true
                _message.value = "Registration successful! Please check your email to confirm your account."
                Log.d("Register", "User registered successfully: ${userModel.email}")

            } catch (e: AuthRestException) {
                Log.e("Register", "Supabase Auth Error: ${e.error} - ${e.description}")
                _message.value = when (e.error) {
                    "user_already_exists" -> "This email is already registered."
                    "over_email_send_rate_limit" -> "Signup rate limit exceeded. Please try again in an hour."
                    "signup_disabled" -> "New registrations are currently disabled."
                    else -> e.description ?: e.message ?: "Registration failed. Please try again."
                }
            } catch (e: Exception) {
                Log.e("Register", "Unexpected Error", e)
                _message.value = "An unexpected error occurred: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
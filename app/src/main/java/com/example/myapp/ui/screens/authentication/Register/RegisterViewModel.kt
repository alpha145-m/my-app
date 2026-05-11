package com.example.myapp.ui.screens.authentication.Register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.models.UserModel
import com.example.myapp.data.repository.AuthRepository
import io.github.jan.supabase.auth.exception.AuthRestException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RegisterViewModel : ViewModel() {

    val authRepository = AuthRepository()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    fun registerUser(userModel: UserModel) {

        viewModelScope.launch {

            try {

                _isLoading.value = true

                authRepository.registerUser(userModel)

                _message.value = "Success!"

            } catch (e: AuthRestException) {

                _message.value =
                    when (e.error) {

                        "over_email_send_rate_limit" ->
                            "Too many signup attempts. Wait a few minutes."

                        "user_already_exists" ->
                            "User already exists."

                        else ->
                            e.message ?: "Authentication failed"
                    }

            } catch (e: Exception) {

                _message.value =
                    "Oops! Something went wrong: ${e.localizedMessage}"

            } finally {

                _isLoading.value = false
            }
        }
    }
}
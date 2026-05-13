package com.example.myapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.data.models.UserModel
import com.example.myapp.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val authRepository = AuthRepository

    private val _userProfile = MutableStateFlow<UserModel?>(null)
    val userProfile = _userProfile.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    init {
        fetchUserProfile()
    }

    fun fetchUserProfile() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val profile = authRepository.getUserProfile()
                _userProfile.value = profile
            } catch (e: Exception) {
                _message.value = "Failed to load profile: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(name: String, phone: String, address: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val updatedUser = _userProfile.value?.copy(
                    name = name,
                    phone = phone,
                    address = address
                ) ?: UserModel(name = name, phone = phone, address = address)
                
                authRepository.updateUserProfile(updatedUser)
                _userProfile.value = updatedUser
                _message.value = "Profile updated successfully!"
            } catch (e: Exception) {
                _message.value = "Update failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun uploadAvatar(byteArray: ByteArray, fileName: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val avatarUrl = authRepository.uploadAvatar(byteArray, fileName)
                val updatedUser = _userProfile.value?.copy(avatarUrl = avatarUrl)
                    ?: UserModel(avatarUrl = avatarUrl)
                
                authRepository.updateUserProfile(updatedUser)
                _userProfile.value = updatedUser
                _message.value = "Profile picture updated!"
            } catch (e: Exception) {
                _message.value = "Upload failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logoutUser()
            onSuccess()
        }
    }
}

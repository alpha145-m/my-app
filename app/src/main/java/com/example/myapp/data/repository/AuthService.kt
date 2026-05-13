package com.example.myapp.data.repository

import com.example.myapp.data.models.UserModel

interface AuthService {
    suspend fun registerUser(user: UserModel)
    suspend fun loginUser(user: UserModel)
    suspend fun resetPassword(email: String)
    suspend fun getUserProfile(): UserModel?
    suspend fun updateUserProfile(user: UserModel)
    suspend fun logoutUser()
}

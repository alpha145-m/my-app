package com.example.myapp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserModel(
    @SerialName("id")
    val id: String? = null,
    
    @Transient // Email is handled by Supabase Auth, usually not stored in the profiles table
    val email: String = "",
    
    @Transient
    val password: String? = null,
    
    @SerialName("name")
    val name: String = "",
    
    @SerialName("phone")
    val phone: String = "",
    
    @SerialName("address")
    val address: String = "",

    @SerialName("avatar_url")
    val avatarUrl: String? = null
)

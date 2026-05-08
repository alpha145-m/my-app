package com.example.myapp.data.repository

import android.R.attr.password
import com.example.myapp.data.models.UserModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


class AuthRepository: AuthService {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://ovvxsvgqfpduneklgzln.supabase.co",
        supabaseKey = "sb_publishable_1Qr6tAMnHxGQFWcxJN_ovw_g7fOzpDd"
         ) {

        install(Postgrest)
        install(Auth)
    }


    override suspend fun registerUser(userDetails: UserModel)  {
        supabase.auth.signUpWith(Email) {
            email = userDetails.email
            password = userDetails.password
        }
    }

    override suspend fun loginUser(userDetails: UserModel)  {
        val user = supabase.auth.signInWith(Email) {
            email = userDetails.email
            password = userDetails.password
        }
    }

    override suspend fun resetPassword(email: String) {
        supabase.auth.resetPasswordForEmail(email = email)
    }

    override suspend fun getUserProfile(user: UserModel) {
//        TODO("Not yet implemented")
    }

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }

}
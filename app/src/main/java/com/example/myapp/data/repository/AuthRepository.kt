package com.example.myapp.data.repository

import com.example.myapp.data.models.UserModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage


object AuthRepository : AuthService {

    val supabase = createSupabaseClient(
            supabaseUrl = "https://ovvxsvgqfpduneklgzln.supabase.co",
            supabaseKey = "sb_publishable_1Qr6tAMnHxGQFWcxJN_ovw_g7fOzpDd"
         ) {
           install(Postgrest)
           install(Auth)
           install(Storage)
    }


    override suspend fun registerUser(user: UserModel)  {
        // Safely sign out any existing session before trying to register a new user
        try {
            supabase.auth.signOut()
        } catch (e: Exception) {
            // Ignore errors during sign out
        }
        
        supabase.auth.signUpWith(Email) {
            email = user.email
            password = user.password ?: ""
        }
    }

    override suspend fun loginUser(user: UserModel)  {
        supabase.auth.signInWith(Email) {
            email = user.email
            password = user.password ?: ""
        }
    }

    override suspend fun resetPassword(email: String) {
        supabase.auth.resetPasswordForEmail(email = email)
    }

    override suspend fun getUserProfile(): UserModel? {
        val user = supabase.auth.currentUserOrNull() ?: return null
        return try {
            supabase.postgrest["profiles"]
                .select {
                    filter {
                        eq("id", user.id)
                    }
                }
                .decodeSingle<UserModel>()
        } catch (e: Exception) {
            UserModel(id = user.id, email = user.email ?: "")
        }
    }

    override suspend fun updateUserProfile(user: UserModel) {
        val currentUser = supabase.auth.currentUserOrNull() ?: return
        supabase.postgrest["profiles"].upsert(user.copy(id = currentUser.id))
    }

    suspend fun uploadAvatar(byteArray: ByteArray, fileName: String): String {
        val currentUser = supabase.auth.currentUserOrNull() ?: throw Exception("Not logged in")
        val bucket = supabase.storage["avatars"]
        val path = "${currentUser.id}/$fileName"
        bucket.upload(path, byteArray) {
            upsert = true
        }
        return bucket.publicUrl(path)
    }

    override suspend fun logoutUser() {
        supabase.auth.signOut()
    }

}

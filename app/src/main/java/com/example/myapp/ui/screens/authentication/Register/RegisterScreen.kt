package com.example.myapp.ui.screens.authentication.Register

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapp.data.models.UserModel
import io.github.jan.supabase.realtime.Column
import io.ktor.websocket.Frame

@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel = viewModel(),
    modifier: Modifier
){
    val isLoading = registerViewModel.isLoading.collectAsState()
    val responseMessage = registerViewModel.message.collectAsState()
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember{ mutableStateOf(TextFieldValue("")) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Frame.Text(text = "password") },
            maxLines = 1
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Frame.Text(text = "email") },
            minLines = 3
        )
        HorizontalDivider()
        Frame.Text(text = responseMessage.value)
        Frame.Text(text = isLoading.value.toString())
        HorizontalDivider()
        if (isLoading.value) {
            CircularProgressIndicator()
        } else {
            OutlinedButton(
                onClick = {
                    val user = UserModel(
                        email = email.text,
                        password = password.text
                    )
                    registerViewModel.registerUser(user)
                }
            ) {
                Frame.Text(text = "create account")
            }
        }


        HorizontalDivider()
    }
}

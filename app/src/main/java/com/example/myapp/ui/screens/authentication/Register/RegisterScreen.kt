package com.example.myapp.ui.screens.authentication.Register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapp.R
import com.example.myapp.data.models.UserModel
import com.example.myapp.ui.navigation.ROUTES
import com.example.myapp.ui.screens.authentication.ForgotPassword.LottieAnimationWidget
import com.example.myapp.ui.theme.primaryColor
import com.example.myapp.ui.theme.secondaryColor
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    navController: NavHostController, 
    modifier: Modifier,
    registerViewModel: RegisterViewModel = viewModel(),
) {
    val darkColor = Color(0xFF000000)
    val pagePadding = 16.dp
    val isLoading by registerViewModel.isLoading.collectAsState()
    val responseMessage by registerViewModel.message.collectAsState()
    val isSuccess by registerViewModel.isSuccess.collectAsState()
    
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("") ) }

    // Navigate to login after successful registration (with a delay to show message)
    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            delay(3000)
            navController.navigate(ROUTES.Login.name) {
                popUpTo(ROUTES.Register.name) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        darkColor,
                        Color(0xFF000000),
                        darkColor
                    )
                )
            )
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(pagePadding)
            .fillMaxSize()
    ) {

        Text(
            text = "Create an account",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        //Lottie animation widget
        LottieAnimationWidget(R.raw.auth_login, 300.dp)
        
        //INPUTS
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = primaryColor
                )
            },
            placeholder = {
                Text(text = "eg. jd@example.com", color = Color.Gray)
            },
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.outline_password_24),
                    contentDescription = "Password",
                    tint = secondaryColor
                )
            },
            placeholder = {
                Text(text = "eg. 12345", color = Color.Gray)
            },
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (responseMessage.isNotEmpty()) {
            Text(
                text = responseMessage,
                color = if (isSuccess) Color.Green else Color.Red,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        
        if (isLoading) {
            CircularProgressIndicator(color = primaryColor)
        } else {
            OutlinedButton(
                onClick = {
                    val user = UserModel(
                        email = email.text.trim(),
                        password = password.text.trim()
                    )
                    registerViewModel.registerUser(user)
                },
                modifier = Modifier.fillMaxWidth(),
                border = ButtonDefaults.outlinedButtonBorder(enabled = true),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White,
                    containerColor = primaryColor
                ),
            ) {
                Text(text = "SIGN UP")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = {
                navController.navigate(ROUTES.Login.name)
            }
        ) {
            Text(
                text = "Already have an account? Login",
                color = Color.LightGray,
                style = TextStyle(fontSize = 12.sp)
            )
        }
    }
}

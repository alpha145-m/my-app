package com.example.myapp.ui.screens.authentication.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.VisualTransformation
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

@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel()
) {
    // inputs
    val darkColor = Color(0xFF000000)
    var emailInput by remember { mutableStateOf(TextFieldValue("")) }
    var passwordInput by remember { mutableStateOf(TextFieldValue("")) }
    var isVisible by remember { mutableStateOf(false) }
    
    // state from viewmodel
    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()
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
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // lottie animation
        LottieAnimationWidget(R.raw.auth_login, 300.dp)

        // welcome message
        Text(
            text = "Login to get started",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // email input
        OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = "Email",
                    tint = primaryColor
                )
            },
            placeholder = {
                Text(text = "eg. jd@example.com")
            },
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        // password input
        OutlinedTextField(
            value = passwordInput,
            onValueChange = { passwordInput = it },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.outline_password_24),
                    contentDescription = "Password",
                    tint = primaryColor
                )
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = if (isVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                IconButton(
                    onClick = { isVisible = !isVisible }
                ) {
                    if (isVisible) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.outline_visibility_off_24),
                            contentDescription = "Hide Password"
                        )
                    } else {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.outline_visibility_24),
                            contentDescription = "Show Password"
                        )
                    }
                }
            },
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // login Button or Loader
        if (message.isNotEmpty()) {
            Text(text = message)
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
        
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            OutlinedButton(
                onClick = {
                    val user = UserModel(
                        email = emailInput.text,
                        password = passwordInput.text
                    )
                    viewModel.loginUser(user)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(text = "LOGIN")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { navController.navigate(ROUTES.ForgotPassword.name) }) {
                Text(
                    text = "Forgot Password",
                    style = TextStyle(fontSize = 12.sp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = { navController.navigate(ROUTES.Register.name) }) {
                Text(
                    text = "No account?",
                    style = TextStyle(fontSize = 12.sp)
                )
            }
        }
        
        TextButton(onClick = { navController.navigate(ROUTES.Home.name) }) {
            Text(
                text = "back",
                style = TextStyle(fontSize = 12.sp)
            )
        }
    }
}

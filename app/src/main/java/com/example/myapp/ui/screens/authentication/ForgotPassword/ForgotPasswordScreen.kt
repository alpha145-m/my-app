package com.example.myapp.ui.screens.authentication.ForgotPassword

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.myapp.R
import com.example.myapp.ui.navigation.ROUTES
import com.example.myapp.ui.theme.primaryColor
import com.example.myapp.ui.theme.secondaryColor


@Composable
fun ForgotPasswordScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    val context = LocalContext.current
    val darkColor = Color(0xFF000000)
    var emailInput by remember { mutableStateOf(TextFieldValue("")) }
    
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
            .padding(16.dp)
            .fillMaxSize()
    ) {

        LottieAnimationWidget(R.raw.auth_login, 200.dp)
        Spacer(modifier = Modifier.height(36.dp))

        Text(
            text = "Oops! Forgot Password ?",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )
        
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = if (message.contains("sent")) Color.Green else Color.Red,
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = emailInput,
            onValueChange = { emailInput = it },
            label = { Text(text = "Email Address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email Input",
                    tint = primaryColor
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = secondaryColor,
                unfocusedLabelColor = primaryColor
            ),
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = { viewModel.forgotPassword(emailInput.text) },
            border = ButtonDefaults.outlinedButtonBorder(enabled = !isLoading),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFFFFFFF),
                containerColor = primaryColor
            ),
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
            } else {
                Text("get password reset")
            }
        }
        
        Row {
            TextButton(
                onClick = { navController.navigate(ROUTES.Login.name) }
            ) {
                Text(
                    text = "back to login",
                    style = TextStyle(fontSize = 11.sp)
                )
            }
            TextButton(
                onClick = { navController.navigate(ROUTES.Register.name) }
            ) {
                Text(
                    text = "no account?",
                    style = TextStyle(fontSize = 11.sp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // CALL OWNER / SUPPORT FEATURE
        TextButton(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:0712345678")
                }
                context.startActivity(intent)
            }
        ) {
            Text(
                text = "Stuck? Call App Owner",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = secondaryColor
                )
            )
        }
    }
}

@Composable
fun LottieAnimationWidget(lottiePath: Int, size: Dp) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottiePath))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(size)
    )
}

package com.example.myapp.ui.screens.authentication.ForgotPassword

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapp.R
import io.github.jan.supabase.realtime.Column

@Composable
@Composable
fun ForgotPasswordScreen(modifier: Modifier, navController: NavHostController){
    var emailInput by remember { mutableStateOf(TextFieldValue("")) }

//    structure:
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp).fillMaxSize()
    ) {

//        lottie animation
        LottieAnimationWidget(R.raw.auth_login,200.dp)
        Spacer(modifier= Modifier.height(36.dp))
// page title
        Text(text="Oops! Forgot Password ?",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            ))
        Spacer(modifier= Modifier.height(24.dp))

//        email input
        OutlinedTextField(
            value = emailInput,
            onValueChange = {emailInput = it },
            label = { Text(text = "Email Address") } ,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription= "Email Input",
                    tint = primaryColor
                )
            },
            colors  = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                unfocusedBorderColor = primaryColor
            ),
            maxLines = 1,
            shape = RoundedCornerShape(24.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier= Modifier.height(24.dp))

//        submit button
        OutlinedButton(
            onClick = {},
            border = ButtonDefaults.outlinedButtonBorder( enabled = false),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFFFFFFF),
                containerColor = primaryColor
            ),
        ) {
            Text("get password reset")
        }
//        text buttons => back to login , no account?
        Row {
            TextButton(
                onClick = {}
            ) {
                Text(
                    text="back to login",
                    style = TextStyle(
                        fontSize = 11.sp,
                    )
                )
            }
            TextButton(
                onClick = {}
            ) {
                Text(text="no account?",
                    style = TextStyle(
                        fontSize = 11.sp,
                    ))
            }
        }

    }
}

/*
* COMPONENTS:
* - reusable bits of our code
* */
@Composable
fun LottieAnimationWidget(lottiePath:Int,size: Dp ) {
    val composition by
    rememberLottieComposition(LottieCompositionSpec.RawRes(lottiePath))
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

package com.example.myapp.ui.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myapp.ui.navigation.ROUTES
import com.example.myapp.ui.theme.darkColor
import com.example.myapp.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel()
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val inputStream = context.contentResolver.openInputStream(it)
                val bytes = inputStream?.readBytes()
                inputStream?.close()
                if (bytes != null) {
                    viewModel.uploadAvatar(bytes, "avatar_${System.currentTimeMillis()}.jpg")
                }
            } catch (e: Exception) {
                // Error handling via ViewModel
            }
        }
    }

    // Initialize fields when profile is loaded
    LaunchedEffect(userProfile) {
        userProfile?.let {
            name = it.name
            phone = it.phone
            address = it.address
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(darkColor, Color.Black, darkColor)
                )
            )
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("My Profile", color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        color = primaryColor
                    )
                }

                if (message.isNotEmpty()) {
                    Text(
                        text = message, 
                        color = if (message.contains("success") || message.contains("updated")) Color.Green else Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                /* PROFILE PICTURE SECTION */
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .size(120.dp)
                        .clickable { launcher.launch("image/*") }
                ) {
                    AsyncImage(
                        model = userProfile?.avatarUrl ?: "https://cdn-icons-png.flaticon.com/512/149/149071.png",
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color.Gray),
                        contentScale = ContentScale.Crop
                    )
                    Surface(
                        color = primaryColor,
                        shape = CircleShape,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Change Photo",
                            modifier = Modifier.padding(6.dp),
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Personal Information", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(16.dp))

                        ProfileTextField(value = name, onValueChange = { name = it }, label = "Full Name")
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        ProfileTextField(value = phone, onValueChange = { phone = it }, label = "Phone Number")
                        Spacer(modifier = Modifier.height(12.dp))

                        ProfileTextField(value = address, onValueChange = { address = it }, label = "Address")
                        
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { viewModel.updateProfile(name, phone, address) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                        ) {
                            Text("Save Changes")
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    onClick = {
                        viewModel.logout {
                            navController.navigate(ROUTES.Login.name) {
                                popUpTo(0)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                    border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = Brush.linearGradient(listOf(Color.Red, Color.Red)))
                ) {
                    Text("Logout")
                }
            }
        }
    }
}

@Composable
fun ProfileTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = Color.Gray
        ),
        singleLine = true
    )
}

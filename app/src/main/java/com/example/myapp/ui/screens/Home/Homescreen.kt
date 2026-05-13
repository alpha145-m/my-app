package com.example.myapp.ui.screens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myapp.ui.navigation.ROUTES
import com.example.myapp.ui.screens.profile.ProfileViewModel
import com.example.myapp.ui.theme.darkColor
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


data class Car(
    val name: String,
    val price: String,
    val year: String,
    val imageUrl: String,
    val engine: String,
    val description: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homescreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userProfile by profileViewModel.userProfile.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

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
    val carList = remember {
        listOf(
            Car(
                "Toyota Corolla", 
                "Ksh 2,800,000", 
                "2018", 
                "https://ovvxsvgqfpduneklgzln.supabase.co/storage/v1/object/public/cars/Toyota%20Corolla.jpg",
                "1.8L 4-cylinder",
                "A reliable and fuel-efficient sedan, perfect for daily commuting in Kenya. Known for its durability and low maintenance costs."
            ),
            Car(
                "Honda Civic", 
                "Ksh 3,200,000", 
                "2019", 
                "https://ovvxsvgqfpduneklgzln.supabase.co/storage/v1/object/public/cars/Honda%20Civic.jpg",
                "1.5L Turbocharged",
                "Stylish and sporty, the Honda Civic offers a smooth ride with advanced safety features and a comfortable interior."
            ),
            Car(
                "BMW 3 Series", 
                "Ksh 5,500,000", 
                "2020", 
                "https://ovvxsvgqfpduneklgzln.supabase.co/storage/v1/object/public/cars/BMW%203%20Series.jpg",
                "2.0L TwinPower Turbo",
                "The ultimate driving machine. This luxury sedan combines high performance with premium comfort and cutting-edge technology."
            ),
            Car(
                "Mercedes C-Class", 
                "Ksh 7,800,000", 
                "2021", 
                "https://ovvxsvgqfpduneklgzln.supabase.co/storage/v1/object/public/cars/Mercedes%20C-Class.jpg",
                "2.0L Inline-4 Turbo",
                "Luxury meets sophistication. The Mercedes C-Class offers an elegant design, a high-tech cabin, and an exceptionally smooth driving experience."
            ),
            Car(
                "Audi A4", 
                "Ksh 5,200,000", 
                "2020", 
                "https://ovvxsvgqfpduneklgzln.supabase.co/storage/v1/object/public/cars/Audi%20A%204.jpg",
                "2.0L TFSI",
                "An intelligent and sporty choice. The Audi A4 features a refined interior, Quattro all-wheel drive, and excellent handling."
            ),
        )
    }

    var search by remember { mutableStateOf("") }

    val filteredCars = carList.filter {
        it.name.contains(search, ignoreCase = true)
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("CarMarket 🚗", color = Color.White) },
                actions = {
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            if (userProfile?.avatarUrl != null) {
                                AsyncImage(
                                    model = userProfile?.avatarUrl,
                                    contentDescription = "Profile",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Profile",
                                    tint = Color.White
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                            modifier = Modifier.background(Color.DarkGray)
                        ) {
                            DropdownMenuItem(
                                text = { Text("View Profile", color = Color.White) },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color.White) },
                                onClick = {
                                    showMenu = false
                                    navController.navigate(ROUTES.Profile.name)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Logout", color = Color.Red) },
                                leadingIcon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = Color.Red) },
                                onClick = {
                                    showMenu = false
                                    profileViewModel.logout {
                                        navController.navigate(ROUTES.Login.name) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black.copy(alpha = 0.7f)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            /* USER GREETING */
            if (userProfile != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (userProfile?.avatarUrl != null) {
                        AsyncImage(
                            model = userProfile?.avatarUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                    Column {
                        Text(
                            text = "Hello, ${userProfile?.name?.ifEmpty { "User" } ?: "User"}! 👋",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Find your next ride today",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            /* SEARCH BAR */
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search cars (Toyota, BMW...)", color = Color.Gray) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Available Cars",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            /* CAR LIST */
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredCars) { car ->
                    CarCard(
                        car = car,
                        onClick = {
                            val encodedDesc = URLEncoder.encode(car.description, StandardCharsets.UTF_8.toString())
                            val encodedEngine = URLEncoder.encode(car.engine, StandardCharsets.UTF_8.toString())
                            val encodedUrl = URLEncoder.encode(car.imageUrl, StandardCharsets.UTF_8.toString())
                            navController.navigate("${ROUTES.Details.name}/${car.name}/${car.price}/${car.year}/$encodedEngine/$encodedDesc/$encodedUrl")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CarCard(
    car: Car,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray.copy(alpha = 0.5f)
        )
    ) {
        Column {
            AsyncImage(
                model = car.imageUrl,
                contentDescription = car.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = car.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "Year: ${car.year}", color = Color.LightGray)
                Text(
                    text = "Price: ${car.price}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View Details")
                }
            }
        }
    }
}

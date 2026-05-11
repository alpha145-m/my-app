package com.example.myapp.ui.screens.Home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapp.ui.navigation.ROUTES


data class Car(
    val name: String,
    val price: String,
    val year: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Homescreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    val carList = remember {
        listOf(
            Car("Toyota Corolla", "$12,500", "2018"),
            Car("Honda Civic", "$14,000", "2019"),
            Car("BMW 3 Series", "$25,000", "2020"),
            Car("Mercedes C-Class", "$30,000", "2021"),
            Car("Audi A4", "$28,000", "2020")
        )
    }

    var search by remember { mutableStateOf("") }

    val filteredCars = carList.filter {
        it.name.contains(search, ignoreCase = true)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("CarMarket 🚗") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            /* SEARCH BAR */
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search cars (Toyota, BMW...)") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Available Cars",
                style = MaterialTheme.typography.titleLarge
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
                            navController.navigate("${ROUTES.Details.name}/${car.name}/${car.price}/${car.year}")
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
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = car.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Year: ${car.year}")
            Text(
                text = "Price: ${car.price}",
                color = MaterialTheme.colorScheme.primary
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

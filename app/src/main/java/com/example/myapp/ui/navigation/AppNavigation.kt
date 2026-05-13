package com.example.myapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapp.ui.screens.Home.Homescreen
import com.example.myapp.ui.screens.Home.Details.DetailsScreen
import com.example.myapp.ui.screens.authentication.ForgotPassword.ForgotPasswordScreen
import com.example.myapp.ui.screens.authentication.Login.LoginScreen
import com.example.myapp.ui.screens.authentication.Register.RegisterScreen
import com.example.myapp.ui.screens.onboarding.OnboardingScreen
import com.example.myapp.ui.screens.profile.ProfileScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = ROUTES.Onboarding.name
    ){
        composable(ROUTES.Onboarding.name) { OnboardingScreen(navController = navController, modifier = modifier) }
        composable(ROUTES.Login.name) { LoginScreen( navController= navController,modifier = modifier) }
        composable(ROUTES.ForgotPassword.name) { ForgotPasswordScreen( navController= navController,modifier = modifier) }
        composable(ROUTES.Register.name) { RegisterScreen( navController= navController,modifier = modifier) }
        composable(ROUTES.Home.name) { Homescreen(navController = navController, modifier = modifier) }
        composable(ROUTES.Profile.name) { ProfileScreen(navController = navController) }
        
        composable(
            route = "${ROUTES.Details.name}/{name}/{price}/{year}/{engine}/{description}/{imageUrl}"
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val price = backStackEntry.arguments?.getString("price")
            val year = backStackEntry.arguments?.getString("year")
            val engine = backStackEntry.arguments?.getString("engine")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            }
            val description = backStackEntry.arguments?.getString("description")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            }
            val imageUrl = backStackEntry.arguments?.getString("imageUrl")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            }
            
            DetailsScreen(
                navController = navController,
                carName = name,
                carPrice = price,
                carYear = year,
                carEngine = engine,
                carDescription = description,
                carImageUrl = imageUrl
            )
        }
    }
}
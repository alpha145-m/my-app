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
        
        composable(
            route = "${ROUTES.Details.name}/{name}/{price}/{year}"
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val price = backStackEntry.arguments?.getString("price")
            val year = backStackEntry.arguments?.getString("year")
            DetailsScreen(
                navController = navController,
                carName = name,
                carPrice = price,
                carYear = year
            )
        }
    }
}
package com.example.myapp.ui.screens.onboarding

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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myapp.R
import com.example.myapp.ui.navigation.ROUTES
import com.example.myapp.ui.theme.darkColor
import kotlinx.coroutines.launch

data class OnboardingPage(
    val title: String,
    val description: String,
    val imageUrl: String
)

@Composable
fun OnboardingScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
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
    val pages = listOf(
        OnboardingPage(
            title = "Find Your Dream Car",
            description = "Browse thousands of new and used cars from trusted sellers.",
            imageUrl = "https://ovvxsvgqfpduneklgzln.supabase.co/storage/v1/object/public/onboarding/car%201.jpg"
        ),
        OnboardingPage(
            title = "Compare & Choose Easily",
            description = "Compare prices, features, and mileage in one place.",
            imageUrl = "https://ovvxsvgqfpduneklgzln.supabase.co/storage/v1/object/public/onboarding/car%202.jpg"
        ),
        OnboardingPage(
            title = "Buy with Confidence",
            description = "Verified listings and secure transactions for peace of mind.",
            imageUrl = "https://ovvxsvgqfpduneklgzln.supabase.co/storage/v1/object/public/onboarding/car%203.jpg"
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { position ->
            OnboardingPageContent(page = pages[position])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${pagerState.currentPage + 1} / ${pages.size}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )

            Button(
                onClick = {
                    if (pagerState.currentPage < pages.size - 1) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        navController.navigate(ROUTES.Register.name) {
                            popUpTo(ROUTES.Onboarding.name) { inclusive = true }
                        }
                    }
                }
            ) {
                Text(text = if (pagerState.currentPage == pages.size - 1) "Finish" else "Next")
            }
        }
    }
}

@Composable
fun OnboardingPageContent(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = page.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 32.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = page.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )
    }
}

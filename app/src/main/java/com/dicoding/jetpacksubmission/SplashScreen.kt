package com.dicoding.jetpacksubmission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dicoding.jetpacksubmission.ui.theme.JetpackSubmissionTheme
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(1500L)
        navController.navigate("home")
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(android.graphics.Color.parseColor("#1B3358"))
            )
    ){
        Image(
            painter = painterResource(id = R.drawable.bookrary),
            contentDescription = "Splashscreen Bookrary",
            modifier = Modifier.size(200.dp)
        )
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun SplashScreenPreview() {
    JetpackSubmissionTheme {
        val navController = rememberNavController()
        SplashScreen(navController = navController)
    }
}
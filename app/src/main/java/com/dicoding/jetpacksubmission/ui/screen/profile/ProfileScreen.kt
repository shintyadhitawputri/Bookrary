package com.dicoding.jetpacksubmission.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.ui.theme.JetpackSubmissionTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
            color = Color(android.graphics.Color.parseColor("#1B3358"))),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .width(390.dp)
                .height(630.dp)
                .background(
                    color = Color(android.graphics.Color.parseColor("#ffb99e")),
                    shape = RoundedCornerShape(
                        topStart = 64.dp,
                        topEnd = 64.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.self_picture),
                contentDescription = "Self Picture",
                modifier = Modifier
                    .width(200.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Shintyadhita Wirawan Putri",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    color = Color(android.graphics.Color.parseColor("#1B3358")),
                    fontSize = 24.sp
                )
                Text(
                    text = "shintyadhita.wp@gmail.com",
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.outfit_regular)),
                    color = Color(android.graphics.Color.parseColor("#1B3358")),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun ProfilePreview() {
    JetpackSubmissionTheme {
        Surface {
            ProfileScreen()
        }
    }
}

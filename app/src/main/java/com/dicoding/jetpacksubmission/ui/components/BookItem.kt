package com.dicoding.jetpacksubmission.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.ui.theme.Shapes

@Composable
fun BookItem(
    photoUrl: String,
    title: String,
    price: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(Shapes.medium)
                .align(Alignment.CenterHorizontally)
        ) {
            AsyncImage (
                model = photoUrl,
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily(Font(R.font.outfit_semibold))
            ),
            color = Color(android.graphics.Color.parseColor("#D6AFB8")),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.required_price, price),
            style = MaterialTheme.typography.titleSmall,
            color = Color(android.graphics.Color.parseColor("#ACB1BB")),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
    }
}
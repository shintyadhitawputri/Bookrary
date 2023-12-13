package com.dicoding.jetpacksubmission.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.ui.theme.JetpackSubmissionTheme
import com.dicoding.jetpacksubmission.ui.theme.Shapes

@Composable
fun CartItem(
    bookId: Long,
    photoUrl: String,
    title: String,
    price: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color(android.graphics.Color.parseColor("#1B3358")))
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
                .padding(bottom = 7.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily(Font(R.font.outfit_semibold)),
                    color = Color(android.graphics.Color.parseColor("#D6AFB8"))
                )
            )
            Text(
                text = stringResource(
                    R.string.required_price,
                    price
                ),
                style = MaterialTheme.typography.titleSmall,
                color = Color(android.graphics.Color.parseColor("#ACB1BB"))

            )
        }
        ProductCounter(
            orderId = bookId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(bookId, count + 1) },
            onProductDecreased = { onProductCountChanged(bookId, count - 1) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    JetpackSubmissionTheme {
        CartItem(
            1,
            "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1637913585i/59702962.jpg",
            "Book Title",
            4000,
            0,
            onProductCountChanged = { _, _ -> },
        )
    }
}
package com.dicoding.jetpacksubmission.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.di.Injection
import com.dicoding.jetpacksubmission.ui.ViewModelFactory
import com.dicoding.jetpacksubmission.ui.common.UiState
import com.dicoding.jetpacksubmission.ui.components.OrderButton
import com.dicoding.jetpacksubmission.ui.components.ProductCounter
import com.dicoding.jetpacksubmission.ui.theme.JetpackSubmissionTheme


@Composable
fun DetailScreen(
    bookId: Long,
    viewModel: DetailBookViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getBookById(bookId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.book.photoUrl,
                    data.book.title,
                    data.book.writer,
                    data.book.year,
                    data.book.blurb,
                    data.book.price,
                    data.count,
                    onBackClick = navigateBack,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.book, count)
                        navigateToCart()
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}


@Composable
fun DetailContent(
    photoUrl: String,
    title: String,
    writer: String,
    year: String,
    blurb: String,
    price: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    var totalPrice by rememberSaveable { mutableIntStateOf(0) }
    var orderCount by rememberSaveable { mutableIntStateOf(count) }

    Column(modifier = modifier
        .background(
            color = Color(android.graphics.Color.parseColor("#1B3358")))) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    modifier = modifier.height(400.dp)
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                        .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = Color(android.graphics.Color.parseColor("#f587a1")),
                    modifier = Modifier.padding(16.dp).clickable { onBackClick() }
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.dmserifdisplay_regular)),
                        color = Color(android.graphics.Color.parseColor("#D6AFB8"))
                    ),
                )
                Text(
                    text = stringResource(R.string.written, writer),
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.outfit_regular)),
                        color = Color(android.graphics.Color.parseColor("#ACB1BB"))
                    ),
                )
                Text(
                    text = year,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color(android.graphics.Color.parseColor("#ACB1BB"))
                    ),
                    textAlign = TextAlign.Justify,
                )
                Text(
                    text = stringResource(R.string.required_price, price),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.outfit_medium)),
                        color = Color(android.graphics.Color.parseColor("#DCDCDC"))
                    ),
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = blurb,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(android.graphics.Color.parseColor("#EDEDED"))
                    ),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(4.dp).background(Color.LightGray))
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ProductCounter(
                1,
                orderCount,
                onProductIncreased = { orderCount++ },
                onProductDecreased = { if (orderCount > 0) orderCount-- },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
            )
            totalPrice = price * orderCount
            OrderButton(
                text = stringResource(R.string.add_to_cart, totalPrice),
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    JetpackSubmissionTheme {
        DetailContent(
            "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1637913585i/59702962.jpg",
            "Buku Menari",
            "Nina",
            "2019",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            7500,
            1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}
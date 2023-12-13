package com.dicoding.jetpacksubmission.ui.screen.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.di.Injection
import com.dicoding.jetpacksubmission.model.Book
import com.dicoding.jetpacksubmission.model.OrderBook
import com.dicoding.jetpacksubmission.ui.ViewModelFactory
import com.dicoding.jetpacksubmission.ui.common.UiState
import com.dicoding.jetpacksubmission.ui.components.CartItem
import com.dicoding.jetpacksubmission.ui.components.OrderButton
import com.dicoding.jetpacksubmission.ui.theme.JetpackSubmissionTheme

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderBook()
            }
            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { bookId, count ->
                        viewModel.updateOrderBook(bookId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.orderBook.joinToString { it.book.title },
        state.orderBook.joinToString { it.book.writer },
        state.totalPrice
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color(android.graphics.Color.parseColor("#1B3358"))),
        ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(state.orderBook, key = { it.book.id }) { item ->
                CartItem(
                    bookId = item.book.id,
                    photoUrl = item.book.photoUrl,
                    title = item.book.title,
                    price = item.book.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged
                )
                Divider()
            }
        }
        OrderButton(
            text = stringResource(R.string.total_order, state.totalPrice),
            enabled = state.orderBook.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun CartContentPreview() {
    JetpackSubmissionTheme {
        CartContent(
            state = CartState(
                orderBook = listOf(
                    OrderBook(Book(1, "It's Book Title", "Lala", "abc", "blub", "2003", 3000), 2),
                ),
                totalPrice = 35000
            ),
            onProductCountChanged = { _, _ -> },
            onOrderButtonClicked = { _ -> }
        )
    }
}

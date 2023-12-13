package com.dicoding.jetpacksubmission.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.jetpacksubmission.R
import com.dicoding.jetpacksubmission.data.BookRepository
import com.dicoding.jetpacksubmission.di.Injection
import com.dicoding.jetpacksubmission.model.OrderBook
import com.dicoding.jetpacksubmission.ui.ViewModelFactory
import com.dicoding.jetpacksubmission.ui.common.UiState
import com.dicoding.jetpacksubmission.ui.components.BookItem
import com.dicoding.jetpacksubmission.ui.theme.JetpackSubmissionTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    val orderBook by viewModel.orderBook.collectAsState()

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllBooksDisplay()
            }
            is UiState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(android.graphics.Color.parseColor("#1B3358")))
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Bookrary",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.dmserifdisplay_regular)),
                            color = Color(android.graphics.Color.parseColor("#D6AFB8")),
                            fontSize = 40.sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 0.dp, top = 5.dp, end = 5.dp, bottom = 0.dp)
                        )
                        SearchBar(
                            query = query,
                            onQueryChange = viewModel::search,
                            modifier = Modifier
                                .background(Color(android.graphics.Color.parseColor("#1B3358")))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(android.graphics.Color.parseColor("#1B3358")))
                        ) {
                            if (query.isBlank()) {
                                HomeContent(
                                    orderBook = uiState.data,
                                    navigateToDetail = navigateToDetail,
                                )
                            } else {
                                HomeContent(
                                    orderBook = orderBook,
                                    navigateToDetail = navigateToDetail,
                                )
                            }
                        }
                    }
                }

            }
            is UiState.Error -> {
            }
        }
    }
}


@Composable
fun HomeContent(
    orderBook: List<OrderBook>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(top = 5.dp)
    ) {
        items(orderBook, key = {it.book.id}) { data ->
            BookItem(
                photoUrl = data.book.photoUrl,
                title = data.book.title,
                price = data.book.price,
                modifier = Modifier.clickable {
                    navigateToDetail(data.book.id)
                })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.material3.SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_book))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .background(Color(android.graphics.Color.parseColor("#1B3358")))
            .padding(start = 10.dp, top = 0.dp, end = 10.dp, bottom = 0.dp),
        windowInsets = WindowInsets(
            top = dimensionResource(R.dimen.top_inset),
        )
    ) {
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun ScreenPreview() {
    JetpackSubmissionTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            HomeScreen(viewModel = HomeViewModel(repository = BookRepository())) {}
        }
    }
}
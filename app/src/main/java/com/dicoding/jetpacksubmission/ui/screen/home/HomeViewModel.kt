package com.dicoding.jetpacksubmission.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.jetpacksubmission.data.BookRepository
import com.dicoding.jetpacksubmission.model.OrderBook
import com.dicoding.jetpacksubmission.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderBook>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderBook>>>
        get() = _uiState

    private val _groupedBooks = MutableStateFlow<Map<Char, List<OrderBook>>>(emptyMap())

    private val _orderBook = MutableStateFlow<List<OrderBook>>(emptyList())
    val orderBook: StateFlow<List<OrderBook>> get() = _orderBook

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun getAllBooksDisplay() {
        viewModelScope.launch {
            repository.getAllBooks()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderBooks ->
                    _uiState.value = UiState.Success(orderBooks)
                }
        }
    }

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            val searchResults = repository.searchBooks(_query.value)
            _groupedBooks.value = groupSearchResults(searchResults)
            _orderBook.value = searchResults
        }
    }

    private fun groupSearchResults(searchResults: List<OrderBook>): Map<Char, List<OrderBook>> {
        return searchResults
            .sortedBy { it.book.title }
            .groupBy { it.book.title[0] }
    }
}
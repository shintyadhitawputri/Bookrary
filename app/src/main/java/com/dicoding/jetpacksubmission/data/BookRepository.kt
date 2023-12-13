package com.dicoding.jetpacksubmission.data

import com.dicoding.jetpacksubmission.model.BooksData
import com.dicoding.jetpacksubmission.model.OrderBook
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class BookRepository {

    private val orderBook = mutableListOf<OrderBook>()

    init {
        if (orderBook.isEmpty()) {
            BooksData.books.forEach {
                orderBook.add(OrderBook(it, 0))
            }
        }
    }

    fun getAllBooks(): Flow<List<OrderBook>> {
        return flowOf(orderBook)
    }

    fun searchBooks(query: String): List<OrderBook>{
        return orderBook.filter {
            it.book.title.contains(query, ignoreCase = true)
        }
    }

    fun getOrderBookById(bookId: Long): OrderBook {
        return orderBook.first {
            it.book.id == bookId
        }
    }

    fun updateOrderBook(bookId: Long, newCountValue: Int): Flow<Boolean> {
        val index = orderBook.indexOfFirst { it.book.id == bookId }
        val result = if (index >= 0) {
            val orderBooks = orderBook[index]
            orderBook[index] =
                orderBooks.copy(book = orderBooks.book, count = newCountValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderBook(): Flow<List<OrderBook>> {
        return getAllBooks()
            .map { orderedBook ->
                orderedBook.filter { orderBook ->
                    orderBook.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(): BookRepository =
            instance ?: synchronized(this) {
                BookRepository().apply {
                    instance = this
                }
            }
    }
}
package com.dicoding.jetpacksubmission.ui.screen.cart

import com.dicoding.jetpacksubmission.model.OrderBook

data class CartState(
    val orderBook: List<OrderBook>,
    val totalPrice: Int
)
package com.dicoding.jetpacksubmission.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.jetpacksubmission.data.BookRepository
import com.dicoding.jetpacksubmission.ui.screen.cart.CartViewModel
import com.dicoding.jetpacksubmission.ui.screen.detail.DetailBookViewModel
import com.dicoding.jetpacksubmission.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: BookRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailBookViewModel::class.java)) {
            return DetailBookViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
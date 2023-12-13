package com.dicoding.jetpacksubmission.di

import com.dicoding.jetpacksubmission.data.BookRepository

object Injection {
    fun provideRepository(): BookRepository {
        return BookRepository.getInstance()
    }
}
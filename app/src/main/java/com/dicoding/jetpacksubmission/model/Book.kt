package com.dicoding.jetpacksubmission.model

data class Book(
    val id: Long,
    val title: String,
    val photoUrl: String,
    val writer: String,
    val blurb: String,
    val year: String,
    val price: Int
)

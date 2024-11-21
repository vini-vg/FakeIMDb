package com.example.fakeimdb.model

data class Movie(
    val id: Int,
    val title: String,
    val genre: String,
    val rating: Double,
    val description: String,
    val posterUrl: String
)

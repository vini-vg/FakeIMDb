package com.example.fakeimdb.model // Ou o pacote adequado

data class MovieResponseItem(
    val id: Int,
    val title: String,
    val genre_ids: List<Int>,
    val vote_average: Double,
    val overview: String,
    val poster_path: String
)
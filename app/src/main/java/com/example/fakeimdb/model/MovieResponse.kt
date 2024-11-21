package com.example.fakeimdb.model

data class MovieResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieResponseItem>  // Lista de filmes
)

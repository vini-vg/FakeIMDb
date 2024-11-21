package com.example.fakeimdb.model

data class MovieDetailResponse(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val vote_average: Double,
    // Adicione outros campos, como a lista de gêneros, se necessário
)

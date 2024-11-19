package com.example.fakeimdb.network

import com.example.fakeimdb.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // Endpoint para filmes populares
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int = 1
    ): MovieResponse

    abstract fun getPopularMovies(): MovieResponse

    // Você pode adicionar outros endpoints conforme necessário
}


package com.example.fakeimdb.network

import com.example.fakeimdb.model.GenreResponse
import com.example.fakeimdb.model.MovieDetailResponse
import com.example.fakeimdb.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Endpoint para buscar os detalhes de um filme
    @GET("movie/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id") movieId: Int, // Pega o ID do filme
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR"// Chave da API
    ): Call<MovieDetailResponse> // Retorna o tipo Call com MovieDetailResponse

    // Endpoint para filmes populares
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR",
        @Query("page") page: Int = 1
    ): MovieResponse

    // Endpoint para obter os gêneros de filmes
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR"
    ): GenreResponse

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "pt-BR",
        @Query("sort_by") sortBy: String = "popularity.desc", // Ordenação por popularidade
        @Query("page") page: Int // Página a ser carregada
    ): MovieResponse
}

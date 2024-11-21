package com.example.fakeimdb.data

import com.example.fakeimdb.model.MovieResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movies")
    suspend fun getMovies(@Query("query") query: String): List<MovieResponseItem>
}
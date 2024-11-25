package com.example.fakeimdb.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movie: FavoriteMovie)

    @Delete
    suspend fun removeFavorite(movie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movie")
    fun getAllFavorites(): LiveData<List<FavoriteMovie>>

    @Query("SELECT * FROM favorite_movie WHERE id = :movieId LIMIT 1")
    fun getFavoriteMovieById(movieId: Int): FavoriteMovie?
}

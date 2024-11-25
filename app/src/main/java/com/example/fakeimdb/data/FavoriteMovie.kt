
package com.example.fakeimdb.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie")
data class FavoriteMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val rating: Double,
    val favorite: Int
)


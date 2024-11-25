package com.example.fakeimdb.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMovie::class], version = 2, exportSchema = false)  // Aumente a versão para 2 ou mais
abstract class MovieDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration()  // Mantém a migração destrutiva
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

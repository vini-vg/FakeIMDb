package com.example.fakeimdb.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.fakeimdb.data.FavoriteMovie
import com.example.fakeimdb.data.MovieDatabase

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    // Obtenha o DAO de FavoriteMovie do banco de dados
    private val favoriteMovieDao = MovieDatabase.getInstance(application).favoriteMovieDao()

    // LiveData que observa os filmes favoritos
    val favoriteMovies: LiveData<List<FavoriteMovie>> = favoriteMovieDao.getAllFavorites()
}

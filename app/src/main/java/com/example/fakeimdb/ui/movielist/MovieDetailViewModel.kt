package com.example.fakeimdb.ui.moviedetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fakeimdb.data.MovieDatabase
import com.example.fakeimdb.data.FavoriteMovie
import com.example.fakeimdb.model.MovieDetailResponse
import com.example.fakeimdb.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val _movieDetails = MutableLiveData<MovieDetailResponse>()
    val movieDetails: LiveData<MovieDetailResponse> get() = _movieDetails

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val database = MovieDatabase.getInstance(application)
    private val favoriteMovieDao = database.favoriteMovieDao()

    // Função para buscar os detalhes de um filme específico
    fun getMovieDetails(movieId: Int) {
        val apiKey = "7b1b54d3177cf9fd220c94991c98b592"
        val language = "pt-BR"

        RetrofitInstance.api.getMovieDetails(movieId, apiKey, language).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                if (response.isSuccessful) {
                    _movieDetails.postValue(response.body())
                    Log.d("MovieDetailViewModel", "Detalhes do filme recebidos: ${response.body()}")
                } else {
                    Log.e("MovieDetailViewModel", "Erro na resposta: Código ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e("MovieDetailViewModel", "Erro ao buscar detalhes do filme: ${t.localizedMessage}")
            }
        })
    }

    // Adicionar filme aos favoritos
    fun addMovieToFavorites(movie: FavoriteMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieDao.addFavorite(movie)
            Log.d("MovieDetailViewModel", "Filme adicionado aos favoritos: ${movie.title}")
            _isFavorite.postValue(true) // Atualiza o estado
        }
    }

    // Remover filme dos favoritos
    fun removeMovieFromFavorites(movie: FavoriteMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteMovieDao.removeFavorite(movie)
            Log.d("MovieDetailViewModel", "Filme removido dos favoritos: ${movie.title}")
            _isFavorite.postValue(false) // Atualiza o estado
        }
    }

    // Verificar se o filme está nos favoritos
    fun checkIfFavorite(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val movie = favoriteMovieDao.getFavoriteMovieById(movieId)
            _isFavorite.postValue(movie != null)
        }
    }
}

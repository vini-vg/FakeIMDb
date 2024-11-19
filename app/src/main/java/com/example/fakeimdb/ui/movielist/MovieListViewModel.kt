package com.example.fakeimdb.ui.movielist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakeimdb.model.Movie
import com.example.fakeimdb.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieListViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies // Expondo o LiveData para a Activity

    // Função para buscar filmes populares
    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPopularMovies()
                if (response.results.isNotEmpty()) {
                    _movies.postValue(response.results) // Atualiza o LiveData com os filmes recebidos
                } else {
                    Log.e("MovieListViewModel", "Nenhum filme encontrado.")
                }
            } catch (e: IOException) {
                // Erro de conexão
                Log.e("MovieListViewModel", "Erro de conexão: ${e.localizedMessage}")
            } catch (e: HttpException) {
                // Erro HTTP
                Log.e("MovieListViewModel", "Erro HTTP ${e.code()}: ${e.localizedMessage}")
            } catch (e: Exception) {
                // Outros tipos de erro
                Log.e("MovieListViewModel", "Erro inesperado: ${e.localizedMessage}")
            }
        }
    }
}

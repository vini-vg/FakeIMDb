package com.example.fakeimdb.ui.movielist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakeimdb.BuildConfig
import com.example.fakeimdb.model.MovieResponseItem
import com.example.fakeimdb.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieListViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<MovieResponseItem>>()
    val movies: LiveData<List<MovieResponseItem>> get() = _movies // Expondo o LiveData para a Activity

    private val allMovies = mutableListOf<MovieResponseItem>() // Lista acumulativa de todos os filmes

    // Função para buscar todos os filmes
    fun getAllMovies() {
        viewModelScope.launch {
            try {
                var page = 1
                allMovies.clear() // Limpar a lista antes de carregar novos filmes

                do {
                    val response = RetrofitInstance.api.discoverMovies(
                        apiKey = BuildConfig.TMDB_API_KEY,
                        page = page
                    )
                    Log.d("MovieListViewModel", "Carregando página $page com ${response.results.size} filmes")
                    allMovies.addAll(response.results)
                    page++
                } while (page <= 100)

                _movies.postValue(allMovies) // Atualiza o LiveData com todos os filmes carregados
                Log.d("MovieListViewModel", "Total de filmes carregados: ${allMovies.size}")
            } catch (e: Exception) {
                Log.e("MovieListViewModel", "Erro ao carregar filmes: ${e.localizedMessage}")
            }
        }
    }

    // Função para buscar filmes filtrados
    fun filterMovies(query: String): List<MovieResponseItem> {
        return if (query.isEmpty()) {
            allMovies
        } else {
            allMovies.filter { it.title.contains(query, ignoreCase = true) }
        }
    }
}

package com.example.fakeimdb.ui.movielist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakeimdb.BuildConfig
import com.example.fakeimdb.model.Genre
import com.example.fakeimdb.model.MovieResponseItem
import com.example.fakeimdb.network.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieListViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<MovieResponseItem>>()
    val movies: LiveData<List<MovieResponseItem>> get() = _movies // Expondo o LiveData para a Activity

    private val _genres = MutableLiveData<List<Genre>>()
    val genres: LiveData<List<Genre>> get() = _genres // Expondo os gêneros para a Activity

    private val genreMap = mutableMapOf<Int, String>() // Mapa para armazenar os gêneros

    // Função para buscar filmes populares
    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPopularMovies(BuildConfig.TMDB_API_KEY) // Usando a chave de API
                if (response.results.isNotEmpty()) {
                    _movies.postValue(response.results) // Atualiza o LiveData com os filmes recebidos
                    Log.d("MovieListViewModel", "Filmes recebidos: ${response.results}")
                } else {
                    Log.e("MovieListViewModel", "Nenhum filme encontrado.")
                }
            } catch (e: IOException) {
                Log.e("MovieListViewModel", "Erro de conexão: ${e.localizedMessage}")
            } catch (e: HttpException) {
                Log.e("MovieListViewModel", "Erro HTTP ${e.code()}: ${e.localizedMessage}")
            } catch (e: Exception) {
                Log.e("MovieListViewModel", "Erro inesperado: ${e.localizedMessage}")
            }
        }
    }

    // Função para carregar os gêneros de filmes
    fun getGenres() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getGenres(BuildConfig.TMDB_API_KEY) // Usando a chave de API
                _genres.postValue(response.genres) // Atualiza o LiveData com os gêneros recebidos
                genreMap.clear() // Limpa o mapa antes de adicionar novos dados
                response.genres.forEach {
                    genreMap[it.id] = it.name // Armazena os gêneros no mapa
                }
                Log.d("MovieListViewModel", "Gêneros recebidos: ${response.genres}")
            } catch (e: IOException) {
                Log.e("MovieListViewModel", "Erro de conexão ao buscar gêneros: ${e.localizedMessage}")
            } catch (e: HttpException) {
                Log.e("MovieListViewModel", "Erro HTTP ao buscar gêneros: ${e.code()}: ${e.localizedMessage}")
            } catch (e: Exception) {
                Log.e("MovieListViewModel", "Erro inesperado ao buscar gêneros: ${e.localizedMessage}")
            }
        }
    }

    // Função para mapear IDs de gênero para os nomes correspondentes
    fun getGenresName(genreIds: List<Int>): String {
        return genreIds.joinToString(", ") { genreMap[it] ?: "Desconhecido" }
    }
}

package com.example.fakeimdb.ui.moviedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fakeimdb.model.MovieDetailResponse
import com.example.fakeimdb.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : ViewModel() {

    private val _movieDetails = MutableLiveData<MovieDetailResponse>()
    val movieDetails: LiveData<MovieDetailResponse> get() = _movieDetails

    // Função para buscar os detalhes de um filme específico
    fun getMovieDetails(movieId: Int) {
        val apiKey = "7b1b54d3177cf9fd220c94991c98b592" // Substitua pela sua chave de API válida
        val language = "pt-BR" // Idioma em português

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
}


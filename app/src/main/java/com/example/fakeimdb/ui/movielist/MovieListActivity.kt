package com.example.fakeimdb.ui.movielist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeimdb.databinding.MovieListActivityBinding

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: MovieListActivityBinding
    private val viewModel: MovieListViewModel by viewModels() // Usa o viewModels() para acessar o ViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o adaptador com uma lista vazia inicialmente
        movieAdapter = MovieAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = movieAdapter

        // Observar o LiveData de filmes no ViewModel
        viewModel.movies.observe(this, Observer { movies ->
            // Atualiza o adapter quando os filmes mudam
            movieAdapter.updateMovies(movies)
        })

        // Busca os filmes populares
        viewModel.getPopularMovies()
    }
}

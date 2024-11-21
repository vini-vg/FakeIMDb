package com.example.fakeimdb.ui.movielist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeimdb.databinding.MovieListActivityBinding
import com.example.fakeimdb.ui.moviedetail.MovieDetailActivity

class MovieListActivity : AppCompatActivity() {
    private lateinit var binding: MovieListActivityBinding
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(emptyList())
        binding.recyclerView.adapter = movieAdapter

        // Observar os filmes populares
        viewModel.movies.observe(this, { movies ->
            movieAdapter.updateMovies(movies)
        })

        // Carregar os filmes
        viewModel.getPopularMovies()

        // Configurar o clique no item do RecyclerView
        movieAdapter.setOnMovieClickListener { movieId ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("MOVIE_ID", movieId) // Envia o ID correto do filme
            startActivity(intent)
        }
    }
}

package com.example.fakeimdb.ui.favorites

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeimdb.databinding.ActivityFavoritesBinding
import com.example.fakeimdb.ui.movielist.MovieAdapter

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar o RecyclerView
        movieAdapter = MovieAdapter(emptyList()) // Adapter com lista inicial vazia
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = movieAdapter

        // Observar os dados de filmes favoritos do ViewModel
        favoritesViewModel.favoriteMovies.observe(this, Observer { favoriteMovies ->
            favoriteMovies?.let {
                movieAdapter.updateMovies(it) // Atualiza a lista no adapter
            }
        })
    }
}

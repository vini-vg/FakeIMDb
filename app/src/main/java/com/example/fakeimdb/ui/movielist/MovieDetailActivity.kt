package com.example.fakeimdb.ui.moviedetail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fakeimdb.R
import com.example.fakeimdb.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera o ID do filme enviado via Intent
        val movieId = intent.getIntExtra("MOVIE_ID", -1)

        if (movieId != -1) {
            viewModel.getMovieDetails(movieId)
        }

        // Observando os detalhes do filme
        viewModel.movieDetails.observe(this, { movieDetails ->
            // Atualiza a UI com os detalhes do filme
            binding.movieTitle.text = movieDetails.title
            binding.movieDescription.text = movieDetails.overview
            binding.movieRating.text = "⭐ Avaliação: ${movieDetails.vote_average}"

            // Verifica se o poster_path não é nulo
            val posterUrl = movieDetails.poster_path?.let {
                "https://image.tmdb.org/t/p/w500$it"
            } ?: "https://path_to_default_placeholder_image.jpg"

            // Carrega o poster com Glide
            Glide.with(this)
                .load(posterUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.moviePoster)
        })
    }
}


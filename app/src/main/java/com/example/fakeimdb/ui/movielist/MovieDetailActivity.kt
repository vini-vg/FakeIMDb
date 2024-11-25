package com.example.fakeimdb.ui.moviedetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fakeimdb.R
import com.example.fakeimdb.databinding.ActivityMovieDetailBinding
import com.example.fakeimdb.data.FavoriteMovie
import com.example.fakeimdb.model.MovieDetailResponse

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("MOVIE_ID", -1)
        if (movieId == -1) {
            Toast.makeText(this, "ID do filme inválido!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Observar os detalhes do filme
        viewModel.movieDetails.observe(this) { movieDetails ->
            movieDetails?.let { updateUI(it) }
        }

        // Carregar os detalhes do filme
        viewModel.getMovieDetails(movieId)

        // Verificar se o filme está nos favoritos
        viewModel.isFavorite(movieId).observe(this) { isFavorite ->
            if (isFavorite) {
                binding.favoriteButton.text = getString(R.string.remove_from_favorites)
                binding.favoriteButton.setOnClickListener {
                    val favoriteMovie = convertToFavoriteMovie(viewModel.movieDetails.value!!)
                    viewModel.removeMovieFromFavorites(favoriteMovie)
                    Toast.makeText(this, "${favoriteMovie.title} removido dos favoritos!", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.favoriteButton.text = getString(R.string.add_to_favorites)
                binding.favoriteButton.setOnClickListener {
                    val favoriteMovie = convertToFavoriteMovie(viewModel.movieDetails.value!!)
                    viewModel.addMovieToFavorites(favoriteMovie)
                    Toast.makeText(this, "${favoriteMovie.title} adicionado aos favoritos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Atualizar a UI com os detalhes do filme
    private fun updateUI(movieDetails: MovieDetailResponse) {
        binding.movieTitle.text = movieDetails.title
        binding.movieOverview.text = movieDetails.overview
        binding.movieRating.text = getString(R.string.movie_rating, movieDetails.vote_average.toString())

        // Construir a URL do poster
        val posterUrl = "https://image.tmdb.org/t/p/w500${movieDetails.poster_path}"
        Glide.with(this)
            .load(posterUrl)
            .placeholder(R.drawable.placeholder_image)
            .into(binding.moviePoster)
    }

    // Converte os detalhes do filme em um objeto FavoriteMovie
    private fun convertToFavoriteMovie(movieDetails: MovieDetailResponse): FavoriteMovie {
        return FavoriteMovie(
            id = movieDetails.id,
            title = movieDetails.title,
            overview = movieDetails.overview,
            posterPath = movieDetails.poster_path ?: "",
            rating = movieDetails.vote_average,  // Adicionando rating
            favorite = 0 // Definir o valor padrão para não favorito
        )
    }
}

package com.example.fakeimdb.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakeimdb.databinding.ItemMovieBinding
import com.example.fakeimdb.data.FavoriteMovie
import com.example.fakeimdb.model.MovieResponseItem

// Adapter genérico que pode lidar com MovieResponseItem e FavoriteMovie
class MovieAdapter(private var movies: List<Any>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // Atualiza a lista de filmes, pode ser tanto de MovieResponseItem quanto FavoriteMovie
    fun updateMovies(newMovies: List<Any>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    // Configura o clique no item
    private var onMovieClickListener: ((Int) -> Unit)? = null

    fun setOnMovieClickListener(listener: (Int) -> Unit) {
        onMovieClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        // Bind os dados dependendo do tipo de objeto (MovieResponseItem ou FavoriteMovie)
        fun bind(movie: Any) {
            when (movie) {
                is MovieResponseItem -> {
                    // Exibir informações do MovieResponseItem (API)
                    binding.movieTitle.text = movie.title
                    binding.movieOverview.text = movie.overview

                    // Construir a URL da imagem
                    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

                    // Usar Glide para carregar a imagem
                    Glide.with(binding.root.context)
                        .load(imageUrl)
                        .placeholder(com.example.fakeimdb.R.drawable.placeholder_image)
                        .into(binding.moviePoster)

                    itemView.setOnClickListener {
                        onMovieClickListener?.invoke(movie.id)
                    }
                }
                is FavoriteMovie -> {
                    // Exibir informações do FavoriteMovie
                    binding.movieTitle.text = movie.title
                    binding.movieOverview.text = movie.overview

                    // Mostrar a imagem de poster, se disponível
                    Glide.with(binding.root.context)
                        .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                        .placeholder(com.example.fakeimdb.R.drawable.placeholder_image)
                        .into(binding.moviePoster)

                    itemView.setOnClickListener {
                        onMovieClickListener?.invoke(movie.id)
                    }
                }
            }
        }
    }
}

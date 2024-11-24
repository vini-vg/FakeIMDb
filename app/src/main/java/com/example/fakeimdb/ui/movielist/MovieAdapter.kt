package com.example.fakeimdb.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakeimdb.databinding.ItemMovieBinding
import com.example.fakeimdb.model.MovieResponseItem

class MovieAdapter(private var movies: List<MovieResponseItem>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // Atualiza a lista de filmes
    fun updateMovies(newMovies: List<MovieResponseItem>) {
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

        fun bind(movie: MovieResponseItem) {
            binding.movieTitle.text = movie.title
            binding.movieOverview.text = movie.overview

            // Construir a URL da imagem
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.poster_path}"

            // Usar Glide para carregar a imagem
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(com.example.fakeimdb.R.drawable.placeholder_image) // Imagem de placeholder enquanto carrega
                .into(binding.moviePoster)

            // Configurar o clique no item
            itemView.setOnClickListener {
                onMovieClickListener?.invoke(movie.id)
            }
        }
    }
}

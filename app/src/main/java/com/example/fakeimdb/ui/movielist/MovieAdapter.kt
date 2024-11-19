package com.example.fakeimdb.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakeimdb.databinding.ItemMovieBinding
import com.example.fakeimdb.model.Movie

class MovieAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = movies.size

    // Função para atualizar a lista de filmes
    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged() // Notifica o adaptador para atualizar a UI
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.tvTitle.text = movie.title
            Glide.with(binding.imageView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(binding.imageView)
        }
    }
}

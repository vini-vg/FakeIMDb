package com.example.fakeimdb.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fakeimdb.databinding.ItemMovieBinding
import com.example.fakeimdb.data.FavoriteMovie

class FavoritesAdapter(private var movies: List<FavoriteMovie>) :
    RecyclerView.Adapter<FavoritesAdapter.FavoriteMovieViewHolder>() {

    private var onMovieClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onMovieClickListener?.invoke(movie.id)
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateMovies(newMovies: List<FavoriteMovie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    fun setOnMovieClickListener(listener: (Int) -> Unit) {
        onMovieClickListener = listener
    }

    class FavoriteMovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: FavoriteMovie) {
            binding.movieTitle.text = movie.title
            // Carregar a imagem do poster, ex.: com Glide ou Picasso
            // Glide.with(binding.moviePoster.context).load(movie.posterPath).into(binding.moviePoster)
        }
    }
}

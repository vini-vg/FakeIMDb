package com.example.fakeimdb.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakeimdb.R
import com.example.fakeimdb.model.MovieResponseItem

class MovieAdapter(private var movies: List<MovieResponseItem>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var onMovieClickListener: ((Int) -> Unit)? = null

    // ViewHolder para armazenar os elementos do layout
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.moviePoster)
        val title: TextView = itemView.findViewById(R.id.movieTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.title.text = movie.title
        Glide.with(holder.poster.context)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .into(holder.poster)

        // Listener de clique
        holder.itemView.setOnClickListener {
            onMovieClickListener?.invoke(movie.id)
        }
    }

    override fun getItemCount(): Int = movies.size

    fun updateMovies(newMovies: List<MovieResponseItem>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    // Função para configurar o listener de clique
    fun setOnMovieClickListener(listener: (Int) -> Unit) {
        onMovieClickListener = listener
    }
}

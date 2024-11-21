package com.example.fakeimdb.ui.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakeimdb.R
import com.example.fakeimdb.databinding.ItemMovieBinding
import com.example.fakeimdb.model.Movie

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // ViewHolder para armazenar os elementos do layout
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.moviePoster)
        val title: TextView = itemView.findViewById(R.id.movieTitle)
        val genre: TextView = itemView.findViewById(R.id.movieGenre)
        val rating: TextView = itemView.findViewById(R.id.movieRating)
        val description: TextView = itemView.findViewById(R.id.movieDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        // Inflar o layout do item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        // Bind dos dados nos elementos visuais
        holder.title.text = movie.title
        holder.genre.text = "Gênero: ${movie.genre}"
        holder.rating.text = "⭐ Avaliação: ${movie.rating}"
        holder.description.text = movie.description

        // Carregar a imagem do poster (usando Glide ou Picasso)
        Glide.with(holder.poster.context)
            .load(movie.posterUrl) // URL da imagem
            .placeholder(R.drawable.placeholder_image) // Imagem padrão
            .into(holder.poster)
    }

    override fun getItemCount(): Int = movies.size
}
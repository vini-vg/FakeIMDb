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

class MovieAdapter(
    private var movies: List<MovieResponseItem>,
    private val viewModel: MovieListViewModel // Passando o ViewModel para o Adapter
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

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

        // Usando o ViewModel para pegar o nome do(s) gênero(s)
        val genres = movie.genre_ids?.let { viewModel.getGenresName(it) }

        // Bind dos dados nos elementos visuais
        holder.title.text = movie.title
        holder.genre.text = "Gênero: $genres" // Exibe os gêneros
        holder.rating.text = "⭐ Avaliação: ${movie.vote_average}"
        holder.description.text = movie.overview

        // Carregar a imagem do poster (usando Glide ou Picasso)
        Glide.with(holder.poster.context)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}") // URL da imagem
            .placeholder(R.drawable.placeholder_image) // Imagem padrão
            .into(holder.poster)
    }

    override fun getItemCount(): Int = movies.size

    // Método para atualizar a lista de filmes
    fun updateMovies(newMovies: List<MovieResponseItem>) {
        movies = newMovies
        notifyDataSetChanged() // Notifica o adaptador sobre as mudanças
    }
}

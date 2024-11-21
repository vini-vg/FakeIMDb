package com.example.fakeimdb.ui.movielist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakeimdb.R
import com.example.fakeimdb.model.Movie

class MovieActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_movie)

        // RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Lista de filmes fictícia
        val movieList = listOf(
            Movie(1, "Filme 1", "Ação", 4.5, "Um filme cheio de ação.", "https://image.tmdb.org/t/p/1.jpg"),
            Movie(2, "Filme 2", "Drama", 4.8, "Uma história emocionante.", "https://image.tmdb.org/t/p/2.jpg"),
            Movie(3, "Filme 3", "Comédia", 3.9, "Uma comédia divertida.", "https://image.tmdb.org/t/p/3.jpg")
        )

        // Configurar o Adapter
        recyclerView.adapter = MovieAdapter(movieList)
    }
}
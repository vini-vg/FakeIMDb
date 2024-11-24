package com.example.fakeimdb.ui.movielist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeimdb.databinding.MovieListActivityBinding
import com.example.fakeimdb.ui.moviedetail.MovieDetailActivity

class MovieListActivity : AppCompatActivity() {
    private lateinit var binding: MovieListActivityBinding
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        movieAdapter = MovieAdapter(emptyList())
        binding.recyclerView.adapter = movieAdapter

        // Exibir ProgressBar enquanto os filmes são carregados
        binding.progressBar.visibility = View.VISIBLE

        // Observar os filmes
        viewModel.movies.observe(this, { movies ->
            binding.progressBar.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
            movieAdapter.updateMovies(movies)
        })

        // Carregar os filmes
        viewModel.getAllMovies()

        // Configurar o clique no item do RecyclerView
        movieAdapter.setOnMovieClickListener { movieId ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("MOVIE_ID", movieId) // Envia o ID correto do filme
            startActivity(intent)
        }

        // Configura a barra de pesquisa
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // Não fazemos nada ao pressionar Enter
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredMovies = viewModel.filterMovies(it)
                    movieAdapter.updateMovies(filteredMovies)
                }
                return true
            }
        })
    }
}

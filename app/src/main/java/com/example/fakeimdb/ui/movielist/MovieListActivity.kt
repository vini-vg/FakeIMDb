package com.example.fakeimdb.ui.movielist


import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fakeimdb.R
import com.example.fakeimdb.databinding.MovieListActivityBinding
import com.example.fakeimdb.ui.favorites.FavoritesActivity
import com.example.fakeimdb.ui.moviedetail.MovieDetailActivity
import com.example.fakeimdb.ui.movielist.MovieAdapter
import com.example.fakeimdb.ui.movielist.MovieListViewModel
import com.google.android.material.navigation.NavigationView

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: MovieListActivityBinding
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando o DrawerLayout e NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

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

        // Configurar Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Configurar o NavigationView
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_favorites -> {
                    // Quando o item "Filmes Favoritos" for clicado
                    startActivity(Intent(this, FavoritesActivity::class.java))
                }
            }
            // Fechar o Drawer após a seleção
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // Botão de abrir o Drawer
        toolbar.setNavigationIcon(R.drawable.ic_menu) // Ícone do hambúrguer
        toolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    // Para lidar com o clique nos itens de navegação (quando clicar fora da tela)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

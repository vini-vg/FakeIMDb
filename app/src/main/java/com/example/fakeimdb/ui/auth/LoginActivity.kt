package com.example.fakeimdb.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.fakeimdb.R
import com.example.fakeimdb.data.AppDatabase
import com.example.fakeimdb.databinding.LoginActivityBinding
import com.example.fakeimdb.ui.movielist.MovieListActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verifica o tema atual
        val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO

        // Define a imagem com base no tema
        if (isDarkMode) {
            binding.imageView.setImageResource(R.drawable.movie_roll_dark)
        } else {
            binding.imageView.setImageResource(R.drawable.movie_roll)
        }

        // Botão para ir para a tela de registro
        binding.btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Botão para login
        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // Lidar com autenticação dentro de uma coroutine
            lifecycleScope.launch {
                val isAuthenticated = authenticateUser(username, password)

                if (isAuthenticated) {
                    // Login bem-sucedido, navegar para a próxima tela
                    startActivity(Intent(this@LoginActivity, MovieListActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Usuário ou senha incorretos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    // Torne a função suspensa e use comContext(Dispatchers.IO) para garantir o acesso ao banco de dados fora da thread principal
    private suspend fun authenticateUser(username: String, password: String): Boolean {
        val db = AppDatabase.getDatabase(applicationContext)

        // A consulta ao banco de dados deve ser feita em uma thread de fundo
        return withContext(Dispatchers.IO) { // Utilizando Dispatchers.IO para acessar o banco de dados
            val user = db.userDao().getUserByUsernameAndPassword(username, password)
            user != null
        }
    }
}

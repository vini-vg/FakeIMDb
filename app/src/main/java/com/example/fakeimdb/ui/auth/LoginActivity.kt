package com.example.fakeimdb.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fakeimdb.data.AppDatabase
import com.example.fakeimdb.databinding.LoginActivityBinding
import com.example.fakeimdb.ui.movielist.MovieListActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (authenticateUser(username, password)) {
                // Login bem-sucedido, navegar para a próxima tela
                startActivity(Intent(this, MovieListActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun authenticateUser(username: String, password: String): Boolean {
        var isAuthenticated = false

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)
            val user = db.userDao().getUserByUsernameAndPassword(username, password)

            if (user != null) {
                isAuthenticated = true
            }
        }

        return isAuthenticated
    }
}


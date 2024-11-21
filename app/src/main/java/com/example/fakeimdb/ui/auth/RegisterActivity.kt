package com.example.fakeimdb.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fakeimdb.data.AppDatabase
import com.example.fakeimdb.data.User
import com.example.fakeimdb.databinding.RegisterActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: RegisterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(username, password)
            }
        }
    }

    private fun registerUser(username: String, password: String) {
        lifecycleScope.launch {
            try {
                val db = AppDatabase.getDatabase(applicationContext)
                val userDao = db.userDao()

                // Verifica se o nome de usuário já existe
                val existingUser = withContext(Dispatchers.IO) {
                    userDao.getUserByUsername(username)
                }

                if (existingUser != null) {
                    Toast.makeText(this@RegisterActivity, "Usuário já existe", Toast.LENGTH_SHORT).show()
                } else {
                    // Insere o novo usuário no banco de dados
                    val user = User(username = username, password = password)
                    withContext(Dispatchers.IO) {
                        userDao.insert(user)
                    }
                    Toast.makeText(this@RegisterActivity, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()

                    // Navega para a tela de login
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, "Erro ao registrar o usuário: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

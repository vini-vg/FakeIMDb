package com.example.fakeimdb.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.fakeimdb.data.AppDatabase
import com.example.fakeimdb.data.User
import com.example.fakeimdb.databinding.RegisterActivityBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: RegisterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                registerUser(username, password)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(username: String, password: String) {
        val user = User(username = username, password = password)

        // Usando Room para salvar o usuário no banco de dados
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(applicationContext)
            db.userDao().insert(user)
            Toast.makeText(this@RegisterActivity, "Usuário registrado com sucesso!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }
}


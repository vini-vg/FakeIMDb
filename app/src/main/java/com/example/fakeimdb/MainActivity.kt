package com.example.fakeimdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.fakeimdb.databinding.ActivityMainBinding
import com.example.fakeimdb.ui.auth.LoginActivity
import android.content.res.Configuration

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Detecta o tema atual do sistema (claro ou escuro)
        val isSystemInDarkMode =
            (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

        if (isSystemInDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        // Configura o estado inicial do CheckBox com base no tema atual
        binding.btDarkMode.isChecked = isSystemInDarkMode

        // Configura a ação do botão "Entrar"
        binding.btnEnter.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Configura o comportamento do botão de dark mode
        binding.btDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val currentMode = AppCompatDelegate.getDefaultNightMode()

            if (isChecked && currentMode != AppCompatDelegate.MODE_NIGHT_YES) {
                Log.d("MainActivity", "Dark Mode Enabled")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else if (!isChecked && currentMode != AppCompatDelegate.MODE_NIGHT_NO) {
                Log.d("MainActivity", "Light Mode Enabled")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}

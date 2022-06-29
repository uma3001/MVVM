package com.example.mvvm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.mvvm.ui.NewsActivity

class MainActivity : AppCompatActivity() {

    lateinit var Preferencs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val icon = findViewById<ImageView>(R.id.splash_icon)
        icon.alpha = 0f
        icon.animate().setDuration(1500).alpha(1f).withEndAction {
            retrive()
        }
    }

    fun retrive() {
        Preferencs = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val email = Preferencs.getString("EMAIL", "")
        val password = Preferencs.getString("PASSWORD", "")

        if (email != null && password != null) {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}
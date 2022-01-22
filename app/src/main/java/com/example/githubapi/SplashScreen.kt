package com.example.githubapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val gitSplashIV = findViewById<ImageView>(R.id.IV_git_splash)

        gitSplashIV.alpha = 0f
        gitSplashIV.animate().setDuration(2500).alpha(1f).withEndAction {

            val i = Intent(this, MainActivity::class.java)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            startActivity(i)
            finish()
        }
    }
}
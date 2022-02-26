package com.example.musicplayer


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // object player
        val player = Player(this)

        val btnPlay = findViewById<Button>(R.id.play_pause)
        btnPlay.setOnClickListener {
            player.play()
        }

        val btnNext = findViewById<Button>(R.id.next)
        btnNext.setOnClickListener {
            player.playNext()
        }

        val btnPrevious = findViewById<Button>(R.id.previous)
        btnPrevious.setOnClickListener {
            player.playPrevious()
        }

        // info activity
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setOnClickListener {
            val intent = Intent(this, DetailsActivity::class.java)
            startActivity(intent)
        }
    }
}
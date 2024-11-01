package com.example.myapplication.task5

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMediaPlayerBinding

class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaPlayer: MediaPlayer = MediaPlayer.create(applicationContext, R.raw.what_is_love)

        binding.playButton.setOnClickListener {
            mediaPlayer.start()
        }

        binding.pauseButton.setOnClickListener {
            mediaPlayer.pause()
        }

        binding.stopButton.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer.prepare()
        }
    }
}

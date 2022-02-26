package com.example.musicplayer

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri

class Player(activity: MainActivity) {
    private var index = 0
    private var paused = false
    private var stopped = true
    private var context = activity.applicationContext
    private var pausePosition = 0
    private var visualizer = Visualizer(activity)
    private val playlist = arrayListOf(R.raw.diamonds, R.raw.bad_reputation, R.raw.devil_pray, R.raw.paint_in_black)
    private val mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )

        setOnCompletionListener {
            playNext()
        }
    }

    fun play() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            pausePosition = mediaPlayer.currentPosition
            paused = true
            visualizer.playing(false)
        }
        else if (!mediaPlayer.isPlaying) {
            if (!paused) {
                reset()
                stopped = false
                visualizer.playing(true)
            }
            else {
                mediaPlayer.seekTo(pausePosition)
                mediaPlayer.start()
                paused = false
                visualizer.playing(true)
            }
        }
    }

    fun playNext() {
        if (!paused && !stopped) {
            mediaPlayer.stop()

            if (index < playlist.size - 1 && !stopped) {
                index++
                reset()
            }

            checkStatusStopped()
        }
    }

    fun playPrevious() {
        if (!paused && !stopped) {
            mediaPlayer.stop()

            if (index > 0) {
                index--
                reset()
            }

            checkStatusStopped()
        }
    }

    private fun reset() {
        mediaPlayer.reset()
        val name = context.resources.getResourceEntryName(playlist[index])
        val uri = Uri.parse("android.resource://com.example.musicplayer/raw/$name")
        mediaPlayer.setDataSource(context, uri)
        mediaPlayer.prepare()
        mediaPlayer.start()
        visualizer.showMetadata(uri)
    }

    private fun checkStatusStopped() {
        if (!mediaPlayer.isPlaying) {
            visualizer.playing(false)
            index = 0
            stopped = true
            visualizer.showDefault()
        }
    }
}
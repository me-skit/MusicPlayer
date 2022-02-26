package com.example.musicplayer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Visualizer(private val activity: MainActivity) {
    private val mediaRetriever = MediaMetadataRetriever()
    private val btnPlay = activity.findViewById<Button>(R.id.play_pause)

    fun playing(playing: Boolean) {
        btnPlay.text = if (playing)  "||" else ">"
    }

    fun showMetadata(uri: Uri) {
        mediaRetriever.setDataSource(activity.applicationContext, uri)

        val artistName = mediaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val artistTextView = activity.findViewById<TextView>(R.id.artistName)
        artistTextView.text = artistName

        val songName = mediaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val songTextView = activity.findViewById<TextView>(R.id.songName)
        songTextView.text = songName

        val imageView = activity.findViewById<ImageView>(R.id.imageView)
        if (mediaRetriever.embeddedPicture == null) {
            imageView.setImageResource(R.drawable.album_art)
        }
        else {
            val art = mediaRetriever.embeddedPicture
            val songCover = BitmapFactory.decodeByteArray(art, 0, art!!.size)
            val bitmap = Bitmap.createScaledBitmap(songCover, 1000, 1000, true)
            imageView.setImageBitmap(bitmap)
        }

        val duration = mediaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val lengthTextView = activity.findViewById<TextView>(R.id.length)
        val text = "00:${duration!!.toInt().div( 1000)}"
        lengthTextView.text = text
    }

    fun showDefault() {
        val songTextView = activity.findViewById<TextView>(R.id.songName)
        songTextView.text = activity.getString(R.string.default_song_name)

        val artistTextView = activity.findViewById<TextView>(R.id.artistName)
        artistTextView.text = activity.getString(R.string.default_artist_name)

        val imageView = activity.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(R.drawable.album_art)

        val elapsedTextView = activity.findViewById<TextView>(R.id.elapsed)
        elapsedTextView.text = activity.getString(R.string.elapsed)

        val lengthTextView = activity.findViewById<TextView>(R.id.length)
        lengthTextView.text = activity.getString(R.string.length)
    }
}
package com.example.webview

import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource


class MainActivity : ComponentActivity() {
    private var player: ExoPlayer? = null
    // Create a data source factory.
    private val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = intent.getStringExtra("url").toString()

        initPlayer(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
    override fun onResume() {
        super.onResume()
        play()
    }

    @UnstableApi
    private fun initPlayer(url:String) {
        player = ExoPlayer.Builder(this)
            .build()
            .apply {
                val source = getHlsMediaSource(url)
                setMediaSource(source)
                prepare()
                player?.play()

            }
    }

    @UnstableApi
    private fun getHlsMediaSource(url:String): MediaSource {
        // Create a HLS media source pointing to a playlist uri.
        return HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(url))
    }

    private fun releasePlayer() {
        player?.apply {
            playWhenReady = false
            release()
        }
        player = null
    }

    private fun pause() {
        player?.playWhenReady = false
    }

    private fun play() {
        player?.playWhenReady = true
    }

}


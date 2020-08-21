package com.flipsidegroup.nmt.system.player

import android.content.Context
import android.net.Uri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

import com.github.ajalt.timberkt.Timber
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_IDLE
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.util.Util
import com.vibesoflove.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoPlayer @Inject constructor(
    val context: Context
) : LifecycleObserver {

    private var lifecycle: Lifecycle? = null
    val player: ExoPlayer = SimpleExoPlayer.Builder(context).build()
    private var playWhenReady = true

    private var _playerState = MutableStateFlow(STATE_IDLE)
    private var _isPlaying = MutableStateFlow(false)
    private var _currentError = MutableStateFlow<ExoPlaybackException?>(null)

    val errors: Flow<ExoPlaybackException> = _currentError.filterNotNull()
    val isPlaying: Flow<Boolean> = _isPlaying
    var playerState: Flow<Int> = _playerState

    private var currentWindow: Int? = null
    private var currentPosition: Long? = null

    val videoListener = object : Player.EventListener {
        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
            error.printStackTrace()
            _currentError.value = error
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            _playerState.value = playbackState
        }
    }

    fun initialise(lifecycle: Lifecycle, url: Int) {
        this.lifecycle = lifecycle.apply { addObserver(this@VideoPlayer) }
        player.prepare(prepareFile(url))
        player.addListener(videoListener)
        player.playWhenReady = playWhenReady
        player.repeatMode = Player.REPEAT_MODE_ONE

    }

    fun initialiseFromApi(lifecycle: Lifecycle,url:String){
        this.lifecycle = lifecycle.apply { addObserver(this@VideoPlayer) }
        player.prepare(buildMediaSource(url))
        player.addListener(videoListener)
        player.playWhenReady = playWhenReady
    }


    private fun buildMediaSource(url: String): ProgressiveMediaSource {
        val mUri: Uri = Uri.parse(url)
        val dataSourceFactory = DefaultDataSourceFactory(
                context, Util.getUserAgent(context, context.getString(R.string.app_name))
        )
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri)
    }

    private fun prepareFile(file: Int): ProgressiveMediaSource {
        val rawDataSource = RawResourceDataSource(context)
        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(file)))
        val dataSourceFactory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.getString(R.string.app_name))
        )
        val defFactory = DefaultExtractorsFactory().setAdtsExtractorFlags(AdtsExtractor.FLAG_ENABLE_CONSTANT_BITRATE_SEEKING)
        return ProgressiveMediaSource.Factory(dataSourceFactory,defFactory)
                .createMediaSource(rawDataSource.uri)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        playWhenReady = player.playWhenReady
        player.playWhenReady = true

        currentWindow?.let { window ->
            currentPosition?.let {
                player.seekTo(window, it)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause() {
        playWhenReady = player.playWhenReady
        player.playWhenReady = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        player.playWhenReady = playWhenReady
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        player.stop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        currentWindow = player.currentWindowIndex
        currentPosition = player.currentPosition
        player.stop()
        player.removeListener(videoListener)
        lifecycle?.removeObserver(this)
    }
}

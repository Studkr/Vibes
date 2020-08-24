package com.flipsidegroup.nmt.screen.app.map.audio

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.github.ajalt.timberkt.Timber
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ts.AdtsExtractor
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
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
class AudioPlayer @Inject constructor(
        val context: Context
) : LifecycleObserver {

    private var lifecycle: Lifecycle? = null
    val exoPlayer: ExoPlayer = SimpleExoPlayer.Builder(context).build()
    private var _playerState = MutableStateFlow(Player.STATE_IDLE)
    private var _isPlaying = MutableStateFlow(false)
    private var _currentError = MutableStateFlow<ExoPlaybackException?>(null)
    private var playWhenReady = true
    val errors: Flow<ExoPlaybackException> = _currentError.filterNotNull()
    val isPlayerChange = MutableStateFlow(" ")
     val conteMediaSource = ConcatenatingMediaSource()

    private var currentWindow: Int? = null
    private var currentPosition: Long? = null


    fun initPlayer(lifecycle: Lifecycle,list:List<String>) {
        this.lifecycle = lifecycle.apply { addObserver(this@AudioPlayer) }
        exoPlayer.prepare(testPlayList(list))
        //exoPlayer.prepare(prepareFile(url))
        exoPlayer.playWhenReady = playWhenReady
    }


     val audioListener = object : Player.EventListener {
        override fun onPlayerError(error: ExoPlaybackException) {
            super.onPlayerError(error)
            error.printStackTrace()
            _currentError.value = error
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _isPlaying.value = isPlaying
            playWhenReady = isPlaying
            isPlayerChange.value = exoPlayer.currentTag.toString()
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            _playerState.value = playbackState

        }


     }

    fun changePlayWhenReady() {
        playWhenReady = true
    }

    fun testPlayList(list : List<String>):ConcatenatingMediaSource{
        list.mapIndexed { index, i ->
            val file = context.resources.getIdentifier(i, "raw", context.packageName)
            val rawDataSource = RawResourceDataSource(context)
            rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(file)))
            val dataSourceFactory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, context.getString(R.string.app_name))
            )
            val defFactory = DefaultExtractorsFactory().setAdtsExtractorFlags(AdtsExtractor.FLAG_ENABLE_CONSTANT_BITRATE_SEEKING)
            conteMediaSource.addMediaSource(ProgressiveMediaSource.Factory(dataSourceFactory, defFactory)
                    .setTag(i)
                    .createMediaSource(rawDataSource.uri))
        }
        return conteMediaSource
    }


    private fun prepareFile(file: Int): ProgressiveMediaSource {
        val rawDataSource = RawResourceDataSource(context)
        rawDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(file)))
        val dataSourceFactory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, context.getString(R.string.app_name))
        )
        val defFactory = DefaultExtractorsFactory().setAdtsExtractorFlags(AdtsExtractor.FLAG_ENABLE_CONSTANT_BITRATE_SEEKING)
        return ProgressiveMediaSource.Factory(dataSourceFactory, defFactory)
                .createMediaSource(rawDataSource.uri)
    }


    fun saveState() {
        currentWindow = exoPlayer.currentWindowIndex
        currentPosition = exoPlayer.currentPosition
        exoPlayer.stop()
    }

    fun rewindAudio() {
        exoPlayer.seekTo(exoPlayer.currentPosition - 10000)
    }

    fun forwardAudio() {
        exoPlayer.seekTo(exoPlayer.currentPosition + 10000)
    }

    fun toggleAudio(): Boolean {
        return if (exoPlayer.audioComponent?.volume != 0f) {
            exoPlayer.audioComponent?.volume = 0f
            false
        } else {
            exoPlayer.audioComponent?.volume = 1f
            true
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        exoPlayer.setForegroundMode(true)
        playWhenReady = exoPlayer.playWhenReady
        exoPlayer.addListener(audioListener)
        currentWindow?.let { window ->
            currentPosition?.let {
                exoPlayer.seekTo(window, it)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume() {
        exoPlayer.playWhenReady = playWhenReady
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        currentWindow = exoPlayer.currentWindowIndex
        currentPosition = exoPlayer.currentPosition
        exoPlayer.removeListener(audioListener)
    }
}
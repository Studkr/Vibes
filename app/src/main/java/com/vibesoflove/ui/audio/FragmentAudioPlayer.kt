package com.vibesoflove.ui.audio

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.github.ajalt.timberkt.Timber
import com.vibesoflove.R
import com.vibesoflove.playlist
import com.vibesoflove.system.BaseFragment
import kotlinx.android.synthetic.main.custom_audio_controller.*
import kotlinx.android.synthetic.main.fragment_audio_player_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class FragmentAudioPlayer : BaseFragment(R.layout.fragment_audio_player_fragment) {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FragmentAudioPlayerViewModel by viewModels { factory }

    @Inject
    lateinit var audioPlayer: AudioPlayer

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        audioController.player = audioPlayer.exoPlayer

        observe(viewModel.curentAudioList) {
            val list: MutableList<String> = arrayListOf()
            it.map { contentModel ->
                list.add(contentModel.audioFileName)
            }
            if (it.size == list.size) initPlayer(list)

            val i = Intent(requireContext(), AudioService::class.java)
            requireContext().startService(i)
        }
        forwardButton.setOnClickListener {
            audioPlayer.exoPlayer.next()
        }

        rewindButton.setOnClickListener {
            audioPlayer.exoPlayer.previous()
        }

        observe(viewModel.currentSong) {

        }

        observe(viewModel.currentPlayList) {
            initPlayList(it)
            it.find { it.isPlaying == true }?.placeholder?.let { it1 -> songImage.setBackgroundResource(it1) }
        }
    }

    fun initPlayer(list: List<String>) {
        audioPlayer.testPlayList(list)
        audioPlayer.exoPlayer.setForegroundMode(true)
        if (list.isNotEmpty()) audioPlayer.initPlayer(lifecycle, list)

    }

    private fun initPlayList(list: List<CurrentPlayList>) {
        playListView.withModels {
            list.mapIndexed { index, s ->
                playlist {
                    id(index)
                    song(s)
                }
            }
        }
    }
}




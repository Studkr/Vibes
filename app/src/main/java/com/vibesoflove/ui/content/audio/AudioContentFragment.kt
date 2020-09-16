package com.vibesoflove.ui.content.audio

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.flipsidegroup.nmt.system.loadImageCrop
import com.github.ajalt.timberkt.Timber

import com.vibesoflove.R
import com.vibesoflove.contentAudio
import com.vibesoflove.model.AudioFirebaseModel
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.audio.AudioService
import kotlinx.android.synthetic.main.audio_content_fragment.*
import kotlinx.android.synthetic.main.custom_audio_controller.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class AudioContentFragment : BaseFragment(R.layout.audio_content_fragment) {

    @Inject
    lateinit var factory:ViewModelFactory

    @Inject
    lateinit var audioPlayer: AudioPlayer

    private val viewModel: AudioContentViewModel by viewModels{factory}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        back.setOnClickListener {
            parentFragment?.childFragmentManager?.popBackStack()
        }
        audioControllerContent.player = audioPlayer.exoPlayer
        audioControllerContent.showController()

        audioList.layoutManager = GridLayoutManager(requireContext(),2)
        audioList.setItemSpacingDp(16)

        observe(viewModel.currentPlaylist){
            progressBar.isVisible = it.isEmpty()
            setData(it)
        }

        observe(viewModel.isPlaying){
            if (it) {
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                        AudioService.startService(requireContext(), "start")
                    }
                }
            }
            audioControllerContent.isVisible = it
        }

        forwardButton.setOnClickListener {
            audioPlayer.exoPlayer.next()
        }

        rewindButton.setOnClickListener {
            audioPlayer.exoPlayer.previous()
        }

        observe(viewModel.currentPlaylist){
            playAudio(it)
        }

    }


    private fun playAudio(model:List<AudioListModule>){
        audioPlayer.initFromApi(lifecycle,model)
        audioPlayer.exoPlayer.setForegroundMode(true)
    }

   private fun setData(list:List<AudioListModule>){
       audioList.withModels {
        list.mapIndexed { index, audioFirebaseModel ->
                contentAudio {
                    id(index)
                    audioModel(audioFirebaseModel)
                    viewModel(viewModel)
                }
            }
        }
    }

}
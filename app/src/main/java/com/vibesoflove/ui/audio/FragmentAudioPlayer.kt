package com.vibesoflove.ui.audio


import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.flipsidegroup.nmt.system.player.VideoPlayer
import com.vibesoflove.R
import com.vibesoflove.playlist
import com.vibesoflove.system.BaseFragment
import kotlinx.android.synthetic.main.custom_audio_controller.*
import kotlinx.android.synthetic.main.fragment_audio_player_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class FragmentAudioPlayer : BaseFragment(R.layout.fragment_audio_player_fragment) {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FragmentAudioPlayerViewModel by viewModels { factory }

    @Inject
    lateinit var audioPlayer: AudioPlayer

    @Inject
    lateinit var videoPlayer: VideoPlayer

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        audioController.player = audioPlayer.exoPlayer
        videoView.player = videoPlayer.player

        observe(viewModel.curentAudioList) {
            val list: MutableList<String> = arrayListOf()
            it.map { contentModel ->
                list.add(contentModel.audioFileName)
            }
            if (it.size == list.size) initPlayer(list)

            //
        }
        forwardButton.setOnClickListener {
            audioPlayer.exoPlayer.next()
        }

        rewindButton.setOnClickListener {
            audioPlayer.exoPlayer.previous()
        }


        fullScreen.setOnClickListener {
          viewModel.fullScreenPresed()
        }

        observe(viewModel.isPlaying) {
            if (it) {
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                        AudioService.startService(requireContext(), "start")
                    }
                }
            }
        }
        observe(viewModel.currentPlayList) {
            initPlayList(it)
            it.map {
                if (it.isPlaying) {
                        initVideoPlayer(it.video)
                }
            }
        }
    }

    fun initVideoPlayer(video: String) {
        GlobalScope.launch {
            withContext(Dispatchers.Main){
                videoPlayer.initialise(lifecycle, resources.getIdentifier(video, "raw", requireContext().packageName))
            }
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
                    viewModel(viewModel)
                }
            }
        }
    }

}


@BindingAdapter("app:loadImage")
fun loadImage(imageView:ImageView,image:CurrentPlayList){
    imageView.setImageResource(image.placeholder)
}

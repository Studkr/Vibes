package com.vibesoflove.ui.home


import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.viewModels
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.flipsidegroup.nmt.system.player.VideoPlayer
import com.vibesoflove.R
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.home.adapter.RoomController
import kotlinx.android.synthetic.main.home_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.home_fragment) {

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var exoPlayer: VideoPlayer

    @Inject
    lateinit var audioPlayer: AudioPlayer

    private val viewModel: HomeViewModel by viewModels { factory }

    val contentAdapter = RoomController {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        exoPlayer.initialise(lifecycle, resources.getIdentifier("video", "raw", requireContext().packageName))
        videoView.player = exoPlayer.player

        roomView.adapter = contentAdapter.adapter

        observe(viewModel.content) {
            contentAdapter.setData(it)
        }
    }

}
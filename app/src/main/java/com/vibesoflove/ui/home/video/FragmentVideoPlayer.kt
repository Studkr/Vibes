package com.vibesoflove.ui.home.video

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.system.player.VideoPlayer
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.model.Video
import com.vibesoflove.model.VideoFile
import com.vibesoflove.system.BaseFragment
import kotlinx.android.synthetic.main.fragment_video_player_fragment.*
import javax.inject.Inject

class FragmentVideoPlayer : BaseFragment(R.layout.fragment_video_player_fragment) {


    @Inject
    lateinit var exoPlayer: VideoPlayer

    @Inject
    lateinit var factory:ViewModelFactory

    private val viewModel: FragmentVideoPlayerViewModel by viewModels { factory }

    val video : VideoFile by argumentDelegate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        videoView.player = exoPlayer.player
        exoPlayer.initialiseFromApi(lifecycle,video.link)
    }


}
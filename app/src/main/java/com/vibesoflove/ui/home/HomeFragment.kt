package com.vibesoflove.ui.home


import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.github.ajalt.timberkt.Timber
import com.vibesoflove.R
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.home.adapter.RoomController
import kotlinx.android.synthetic.main.home_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class HomeFragment : BaseFragment(R.layout.home_fragment) {

    @Inject
    lateinit var audioPlayer: AudioPlayer

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: HomeViewModel by viewModels { factory }

    private val contentAdapter = RoomController({
        viewModel.openCategory(it)
    }, {

    }, {

    }, {
        viewModel.openMyMixFragment(it)
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        roomView.adapter = contentAdapter.adapter

        audioPlayerView.player = audioPlayer.exoPlayer

        observe(viewModel.contentCategory) {
            contentAdapter.setData(it)
            progressHome.isVisible = it.video.isEmpty()
            roomView.isVisible = it.video.isNotEmpty()
        }

        observe(viewModel.openVideoFragment) {
            parentFragment?.findNavController()?.navigate(R.id.toVideoPlayer, bundleOf("video" to it))
        }

        observe(viewModel.openAudioPlayer) {
            parentFragment?.findNavController()?.navigate(R.id.toAudioContent)
        }

        observe(viewModel.openMyMix) {
            parentFragment?.findNavController()?.navigate(R.id.toMyMixFragment)
        }

        observe(viewModel.isPlaying){
           audioPlayerView.isVisible = it
        }

        observe(viewModel.errorMessage){
         progressHome.isVisible = false
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Error")
                    .setMessage(it)
                    .setIcon(R.drawable.ic_error)
                    .setPositiveButton("Ok") {
                        dialog, id ->
                        dialog.cancel()
                        System.exit(0)
                    }
                    .setNegativeButton("Retry"){ dialog, id ->
                        viewModel.updateData()
                        progressHome.isVisible = true
                    }
            builder.create()
            builder.setCancelable(false)
            builder.show()
        }

        observe(viewModel.openContent) {
            parentFragment?.findNavController()?.navigate(R.id.toContentFragment, bundleOf("data" to it))
        }
    }

}
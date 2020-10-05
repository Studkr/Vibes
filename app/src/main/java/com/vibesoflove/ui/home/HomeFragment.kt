package com.vibesoflove.ui.home


import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.screen.app.map.audio.AudioPlayer
import com.github.ajalt.timberkt.Timber
import com.vibesoflove.R
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.system.showChromeTab
import com.vibesoflove.ui.home.adapter.RoomController
import com.vibesoflove.ui.home.homeItem.HomeItemFragment
import kotlinx.android.synthetic.main.home_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject
import kotlin.system.exitProcess

class HomeFragment : BaseFragment(R.layout.home_fragment) {

    @Inject
    lateinit var audioPlayer: AudioPlayer

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: HomeViewModel by viewModels { factory }

    private val contentAdapter = RoomController({
        viewModel.openCategory(it)
    }, {
        viewModel.photoClicked(it)

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

        pixelImage.setOnClickListener {
            viewModel.logoClicked()
        }

        observe(viewModel.savedMix){

            mixViewPager.adapter = object :FragmentStateAdapter(this){
                override fun getItemCount(): Int = it.size

                override fun createFragment(position: Int): Fragment  = HomeItemFragment().apply {
                    this.arguments = bundleOf(
                            "data" to it.get(position)
                    )
                }

            }

        }

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

        observe(viewModel.photoChoose) {
            parentFragment?.findNavController()?.navigate(R.id.toPhotoFragment, bundleOf("data" to it.current, "photoMass" to it.array))
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
                        exitProcess(0)
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

        observe(viewModel.openPixels){
            showChromeTab(it)
        }
    }

}
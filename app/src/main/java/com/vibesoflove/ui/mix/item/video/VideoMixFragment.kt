package com.vibesoflove.ui.mix.item.video

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.vibesoflove.R
import com.vibesoflove.saved
import com.vibesoflove.savedVideo
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.mix.item.MixModel
import kotlinx.android.synthetic.main.item_mix_fragment.*
import kotlinx.android.synthetic.main.video_mix_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class VideoMixFragment : BaseFragment(R.layout.video_mix_fragment) {

    companion object {
        fun newInstance() = VideoMixFragment()
    }

    @Inject
    lateinit var factory:ViewModelFactory

    private val viewModel: VideoMixViewModel by viewModels { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        videoDataView.setItemSpacingDp(16)
        videoDataView.layoutManager = GridLayoutManager(requireContext(), 2)


        observe(viewModel.videoList){
            progressBar.isVisible = it.isEmpty()
            setData(it)
        }
    }
    private fun setData(list: List<MixModel>) {
        videoDataView.withModels {
            list.mapIndexed { index, contentModel ->
                savedVideo {
                    id(index)
                    viewModel(viewModel)
                    image(contentModel)
                }
            }
        }
    }

}
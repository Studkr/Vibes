package com.vibesoflove.ui.mix.item.video

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.vibesoflove.R
import com.vibesoflove.savedVideo
import com.vibesoflove.system.BaseFragment
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
    private fun setData(list: List<MixVideo>) {
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
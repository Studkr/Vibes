package com.vibesoflove.ui.mix.item.photo

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.vibesoflove.R
import com.vibesoflove.savedPhoto
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.mix.item.MixModel
import kotlinx.android.synthetic.main.audio_mix_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class PhotoMixFragment : BaseFragment(R.layout.audio_mix_fragment) {

    companion object {
        fun newInstance() = PhotoMixFragment()
    }

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: PhotoMixViewModel by viewModels { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        photoView.setItemSpacingDp(16)
        photoView.layoutManager = GridLayoutManager(requireContext(), 2)


        observe(viewModel.videoList) {
            progressBar.isVisible = it.isEmpty()
            setData(it)
        }
    }

    fun setData(data: List<MixModel>) {
        photoView.withModels {
            data.mapIndexed { index, mixModel ->
                savedPhoto {
                    id(index)
                    viewModel(viewModel)
                    image(mixModel.placeholder)
                }
            }
        }
    }
}
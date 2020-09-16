package com.vibesoflove.ui.mix.item.audio

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.vibesoflove.R
import com.vibesoflove.db.AudioEntity
import com.vibesoflove.itemAudio
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.home.adapter.audio
import kotlinx.android.synthetic.main.audio_item_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class AudioItemFragment : BaseFragment(R.layout.audio_item_fragment) {

    @Inject
    lateinit var factory :ViewModelFactory
    private val viewModel: AudioItemViewModel by viewModels { factory }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        audioSavedList.layoutManager = GridLayoutManager(requireContext(),2)
        audioSavedList.setItemSpacingDp(16)

        observe(viewModel.savedAudioList){
            setData(it)
        }
    }

    private fun setData(list: List<AudioEntity>){
        audioSavedList.withModels {
            list.mapIndexed {index , model ->
                itemAudio {
                    id(index)
                    audioModel(model)
                    viewModel(viewModel)
                }
            }
        }
    }
}
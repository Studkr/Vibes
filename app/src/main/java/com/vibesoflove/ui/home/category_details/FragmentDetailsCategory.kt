package com.vibesoflove.ui.home.category_details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.github.ajalt.timberkt.Timber
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.home.category_details.adapter.CategoryController
import kotlinx.android.synthetic.main.fragment_details_category_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class FragmentDetailsCategory : BaseFragment(R.layout.fragment_details_category_fragment) {


    @Inject
    lateinit var factory:ViewModelFactory

    private val viewModel: FragmentDetailsCategoryViewModel by viewModels { factory }

    val data :String by argumentDelegate()
    private val controller = CategoryController{
            viewModel.openVideo(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadData(data)

        videoList.adapter = controller.adapter

        videoList.layoutManager = GridLayoutManager(requireContext(),2)
        videoList.setItemSpacingDp(10)

        observe(viewModel.openVideo){
            parentFragment?.findNavController()?.navigate(R.id.toVideoPlayer, bundleOf(
                    "video" to it
            ))
        }

        observe(viewModel.videoList){
            videoList.isVisible = true
            progressBar.isVisible = it.videos.isEmpty()
            controller.setData(it)
        }
    }

}
package com.vibesoflove.ui.home.photo.photoitem

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.system.loadImageCrop
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.system.BaseFragment
import kotlinx.android.synthetic.main.fragment_photo_item.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class PhotoItemFragment : BaseFragment(R.layout.fragment_photo_item) {

    companion object {
        fun newInstance() = PhotoItemFragment()
    }

    @Inject
    lateinit var factory:ViewModelFactory

    private val viewModel: PhotoItemViewModel by viewModels{factory}

    val currentItem: Long by argumentDelegate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.loadPhotoById(currentItem)


        observe(viewModel.choosePhoto){
            photoView.loadImageCrop(it.src.large)
        }
    }

}
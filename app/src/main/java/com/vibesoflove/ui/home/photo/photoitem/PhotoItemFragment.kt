package com.vibesoflove.ui.home.photo.photoitem

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.system.loadImageCrop
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.system.showChromeTab
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

        infoButton.setOnClickListener {
            viewModel.showInfo()
        }

        observe(viewModel.openInfo){
            showChromeTab(it)
        }
    }

}
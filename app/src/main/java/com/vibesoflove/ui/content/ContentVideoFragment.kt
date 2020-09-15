package com.vibesoflove.ui.content


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.flipsidegroup.nmt.system.loadImageCrop
import com.github.ajalt.timberkt.Timber
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.content
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.audio.CurrentPlayList
import kotlinx.android.synthetic.main.content_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class ContentVideoFragment : BaseFragment(R.layout.content_fragment) {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: ContentViewModel by viewModels { factory }

    val data by argumentDelegate<String>()

    private val controller = ContentController{
        viewModel.savedVideo(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        back.setOnClickListener {
            parentFragment?.childFragmentManager?.popBackStack()
        }

        viewModel.loadData(data)
        contentView.adapter = controller.adapter

        contentTitle.text = data

        contentView.setItemSpacingDp(8)

        contentView.layoutManager = GridLayoutManager(requireContext(), 2)

        observe(viewModel.currentPopularVideo) {
            progressContent.isVisible = it.isEmpty()
            contentView.isVisible = !progressContent.isVisible
            controller.setData(it)
        }

        observe(viewModel.currentPopularPhoto){
            progressContent.isVisible = it.isEmpty()
            contentView.isVisible = !progressContent.isVisible
            controller.setData(it)
        }
    }



}

@BindingAdapter("app:loadImageNetwork")
fun loadImageNetwork(imageView: ImageView, image: String) {
    imageView.loadImageCrop(image)
}


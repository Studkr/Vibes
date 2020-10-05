package com.vibesoflove.ui.home.photo

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.home.photo.photoitem.PhotoItemFragment
import kotlinx.android.synthetic.main.fragment_photo.*
import javax.inject.Inject

class PhotoFragment : BaseFragment(R.layout.fragment_photo) {

    companion object {
        fun newInstance() = PhotoFragment()
    }


    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: PhotoViewModel by viewModels { factory }

    val data: Long by argumentDelegate()
    val photoMass: List<Long> by argumentDelegate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        closeButton.setOnClickListener {
            parentFragment?.childFragmentManager?.popBackStack()
        }

        photoViewPager.setPageTransformer(FadeOutTransformation())

        photoViewPager.post {
            val page = photoMass.indexOf(data)
            photoViewPager.currentItem = page
        }

        photoViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

            }
        })

        photoViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = photoMass.size

            override fun createFragment(position: Int): Fragment = PhotoItemFragment().apply {
                this.arguments = bundleOf(
                        "currentItem" to photoMass[position]
                )
            }

        }
    }

}
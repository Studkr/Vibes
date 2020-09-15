package com.vibesoflove.ui.mix

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator
import com.vibesoflove.R
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.mix.adapter.MyMixFragmentAdapter
import com.vibesoflove.ui.mix.adapter.Tabs
import com.vibesoflove.ui.mix.animation.ZoomOutPageTransformer
import com.vibesoflove.ui.mix.item.ItemMixFragment
import kotlinx.android.synthetic.main.my_mix_container_fragment.*
import java.lang.IndexOutOfBoundsException
import javax.inject.Inject

class MyMixContainer : BaseFragment(R.layout.my_mix_container_fragment) {

    companion object {
        fun newInstance() = MyMixContainer()
    }

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: MyMixContainerViewModel by viewModels { factory }

    val mixList = arrayListOf("My Mix", "Video", "Photo", "Music", "Vibration")


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mixBack.setOnClickListener {
            parentFragment?.childFragmentManager?.popBackStack()
        }

        mixViewPager.setPageTransformer(ZoomOutPageTransformer())
        val vpAdapter = MyMixFragmentAdapter(childFragmentManager, lifecycle)

        mixViewPager.adapter = vpAdapter

        TabLayoutMediator(mixTabs, mixViewPager, TabLayoutMediator.TabConfigurationStrategy { tab, position ->
            tab.text = mixList[Tabs.get(position).position]
        }).attach()
    }

}
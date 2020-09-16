package com.vibesoflove.ui.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.model.CategoryFirebaseModel
import com.vibesoflove.system.BaseFragment
import com.vibesoflove.ui.content.item.ContentVideoFragment
import com.vibesoflove.ui.mix.animation.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.category_container_fragment.*
import pro.shineapp.rentout.system.ext.observe
import javax.inject.Inject

class CategoryContainerFragment : BaseFragment(R.layout.category_container_fragment) {

    @Inject
    lateinit var factory:ViewModelFactory

    private val viewModel: CategoryContainerViewModel by viewModels { factory }

    val data by argumentDelegate<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        contentTitle.text = data

        back.setOnClickListener {
            parentFragment?.childFragmentManager?.popBackStack()
        }

        observe(viewModel.firebaseCategory){
            if(it.isNotEmpty()){
                setUpViewPager(it)
            }
        }
        viewPager.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun setUpViewPager(categoryList: List<CategoryFirebaseModel>){

        viewPager.adapter = object :FragmentStateAdapter(this){
            override fun getItemCount(): Int  = categoryList.size

            override fun createFragment(position: Int): Fragment = ContentVideoFragment().apply {
                this.arguments = bundleOf(
                        "data" to this@CategoryContainerFragment.data,
                        "name" to categoryList[position].name
                )
            }
        }

        TabLayoutMediator(categoryTabs,viewPager,object :TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                tab.text = categoryList[position].name
            }
        }).attach()

    }


}
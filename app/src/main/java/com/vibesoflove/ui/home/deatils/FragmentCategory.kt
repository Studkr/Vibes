package com.vibesoflove.ui.home.deatils

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.flipsidegroup.nmt.di.viewmodel.ViewModelFactory
import com.idapgroup.argumentdelegate.argumentDelegate
import com.vibesoflove.R
import com.vibesoflove.model.CategoryModel
import com.vibesoflove.system.BaseFragment
import javax.inject.Inject

class FragmentCategory : BaseFragment(R.layout.fragment_category_fragment) {



   @Inject
   lateinit var factory:ViewModelFactory

    private  val viewModel: FragmentCategoryViewModel by viewModels{factory}

    val model :CategoryModel by argumentDelegate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }



}
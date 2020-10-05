package com.vibesoflove.ui.home.homeItem

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
import com.vibesoflove.db.DataBaseEntity
import com.vibesoflove.system.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class HomeItemFragment : BaseFragment(R.layout.fragment_home_item) {

    companion object {
        fun newInstance() = HomeItemFragment()
    }

    @Inject
    lateinit var factory:ViewModelFactory
    private val viewModel: HomeItemViewModel by viewModels { factory }

    val data :DataBaseEntity by argumentDelegate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

      com.github.ajalt.timberkt.Timber.i{
          "$data"
      }
    }

}
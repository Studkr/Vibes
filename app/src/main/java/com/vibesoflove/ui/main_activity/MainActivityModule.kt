package com.vibesoflove.ui.main_activity

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import com.vibesoflove.ui.MainActivity
import com.vibesoflove.ui.home.HomeFragmentModule
import com.vibesoflove.ui.home.HomeFragmentViewModelModule
import com.vibesoflove.ui.home.deatils.FragmentCategoryModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @ContributesAndroidInjector(modules = [
        MainActivityViewModelModule::class,
        HomeFragmentModule::class,
        FragmentCategoryModule::class
    ])
    fun activity(): MainActivity
}

@Module
interface MainActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun viewModel(viewModel: MainActivityViewModel): ViewModel
}
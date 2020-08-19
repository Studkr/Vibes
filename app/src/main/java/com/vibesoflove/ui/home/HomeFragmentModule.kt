package com.vibesoflove.ui.home

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HomeFragmentModule {
    @ContributesAndroidInjector(modules = [
        HomeFragmentViewModelModule::class
    ])

    fun inject(): HomeFragment
}

@Module
interface HomeFragmentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun viewModel(viewModel: HomeViewModel): ViewModel
}
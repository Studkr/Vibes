package com.vibesoflove.ui.home.video

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface FragmentVIdeoPlayerModule {
    @ContributesAndroidInjector(modules =[
        FragmentVideoPlayerViewModelModule::class
    ])
    fun inject(): FragmentVideoPlayer
}

@Module
interface FragmentVideoPlayerViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(FragmentVideoPlayerViewModel::class)
    fun viewModel (viewModel: FragmentVideoPlayerViewModel):ViewModel
}
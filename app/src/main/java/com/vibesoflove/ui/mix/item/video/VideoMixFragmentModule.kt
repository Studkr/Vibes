package com.vibesoflove.ui.mix.item.video

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface VideoMixFragmentModule {
    @ContributesAndroidInjector(modules = [VideoMixFragmentViewModelModule::class])
    fun inject():VideoMixFragment
}

@Module
interface VideoMixFragmentViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(VideoMixViewModel::class)
    fun viewModel (viewModel: VideoMixViewModel):ViewModel
}
package com.vibesoflove.ui.audio

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface FragmentAudioPlayerModule {
    @ContributesAndroidInjector(modules = [
        FragmentAudioPlayerViewModelModule::class
    ])
    fun inject():FragmentAudioPlayer
}

@Module
interface FragmentAudioPlayerViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(FragmentAudioPlayerViewModel::class)
    fun viewModel(viewModel: FragmentAudioPlayerViewModel):ViewModel
}
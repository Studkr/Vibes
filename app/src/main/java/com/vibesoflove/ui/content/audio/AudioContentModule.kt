package com.vibesoflove.ui.content.audio

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface AudioContentModule{
    @ContributesAndroidInjector(modules = [AudioContentViewModelModule::class])
    fun inject():AudioContentFragment
}

@Module
interface AudioContentViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(AudioContentViewModel::class)
    fun viewModel (viewModel: AudioContentViewModel):ViewModel
}
package com.vibesoflove.ui.mix.item.audio

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface AudioItemModule {
    @ContributesAndroidInjector(modules = [AudioItemViewModelModule::class])
    fun inject():AudioItemFragment
}

@Module
interface AudioItemViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(AudioItemViewModel::class)
    fun viewModel (viewModel: AudioItemViewModel):ViewModel
}
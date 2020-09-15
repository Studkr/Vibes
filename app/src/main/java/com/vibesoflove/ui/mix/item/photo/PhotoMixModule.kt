package com.vibesoflove.ui.mix.item.photo

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface PhotoMixModule {
    @ContributesAndroidInjector(modules = [PhotoMixViewModelModule::class])
    fun inject():PhotoMixFragment
}

@Module
interface PhotoMixViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(PhotoMixViewModel::class)
    fun viewModel (viewModel: PhotoMixViewModel):ViewModel
}
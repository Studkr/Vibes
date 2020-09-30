package com.vibesoflove.ui.home.photo.photoitem

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface PhotoItemModule {
    @ContributesAndroidInjector(modules = [PhotoItemViewModelModule::class])
    fun inject(): PhotoItemFragment
}

@Module
interface PhotoItemViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PhotoItemViewModel::class)
    fun viewModel(viewModel: PhotoItemViewModel): ViewModel
}
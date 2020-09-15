package com.vibesoflove.ui.content

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface ContentFragmentModule {
    @ContributesAndroidInjector(modules = [
        ContentFragmentViewModelModule::class
    ])
    fun inject(): ContentVideoFragment
}

@Module
interface ContentFragmentViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ContentViewModel::class)
    fun viewModel(viewModel: ContentViewModel): ViewModel
}
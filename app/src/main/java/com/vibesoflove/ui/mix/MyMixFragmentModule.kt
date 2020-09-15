package com.vibesoflove.ui.mix

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface MyMixFragmentModule {
    @ContributesAndroidInjector(modules = [MyMixViewModelModule::class])
    fun inject(): MyMixContainer
}

@Module
interface MyMixViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyMixContainerViewModel::class)
    fun viewModel(viewModel: MyMixContainerViewModel): ViewModel
}
package com.vibesoflove.ui.content

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface CategoryContainerModule {
    @ContributesAndroidInjector(modules = [CategoryContainerViewModelModule::class])
    fun inject():CategoryContainerFragment
}

@Module
interface CategoryContainerViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(CategoryContainerViewModel::class)
    fun viewModel(viewModel: CategoryContainerViewModel) :ViewModel
}
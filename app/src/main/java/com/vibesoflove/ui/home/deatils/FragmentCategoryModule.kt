package com.vibesoflove.ui.home.deatils

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface FragmentCategoryModule {
    @ContributesAndroidInjector(modules = [
        FragmentCategoryViewModelModule::class
    ])
    fun inject(): FragmentCategory
}

@Module
interface FragmentCategoryViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FragmentCategoryViewModel::class)
    fun viewModel(viewModel: FragmentCategoryViewModel): ViewModel
}
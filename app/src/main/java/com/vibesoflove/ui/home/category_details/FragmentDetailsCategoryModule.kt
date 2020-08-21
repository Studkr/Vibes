package com.vibesoflove.ui.home.category_details

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface FragmentDetailsCategoryModule {
    @ContributesAndroidInjector(modules = [
        FragmentCategoryViewModelModule::class
    ])
    fun inject():FragmentDetailsCategory
}

@Module
interface FragmentCategoryViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(FragmentDetailsCategoryViewModel::class)
    fun viewModel (viewModel: FragmentDetailsCategoryViewModel):ViewModel
}
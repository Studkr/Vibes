package com.vibesoflove.ui.mix.item

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface ItemMixModule {
    @ContributesAndroidInjector(modules = [
        ItemMixViewModelModule::class
    ])
    fun inject():ItemMixFragment
}

@Module
interface ItemMixViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(ItemMixViewModel::class)
    fun viewModel (viewModel: ItemMixViewModel):ViewModel
}
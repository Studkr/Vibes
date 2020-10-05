package com.vibesoflove.ui.home.homeItem

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface HomeItemModule{
    @ContributesAndroidInjector(modules = [HomeItemVieModelModule::class])
    fun inject():HomeItemFragment
}
@Module
interface HomeItemVieModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(HomeItemViewModel::class)
    fun viewModel (viewModel: HomeItemViewModel):ViewModel
}
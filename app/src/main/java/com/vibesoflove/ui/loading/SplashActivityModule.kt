package com.vibesoflove.ui.loading

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface SplashActivityModule {
    @ContributesAndroidInjector(
            modules = [
                SplashViewModelModule::class
            ]
    )
    fun activity(): SplashActivity
}
@Module
interface  SplashViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun viewModel (viewModel: SplashViewModel):ViewModel
}

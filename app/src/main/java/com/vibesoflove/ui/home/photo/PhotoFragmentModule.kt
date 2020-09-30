package com.vibesoflove.ui.home.photo

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import com.vibesoflove.ui.home.photo.photoitem.PhotoItemModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface PhotoFragmentModule {
    @ContributesAndroidInjector(modules = [
        PhotoFragmentViewModelModule::class,
        PhotoItemModule::class
    ])
    fun inject():PhotoFragment

}

@Module
interface PhotoFragmentViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(PhotoViewModel::class)
    fun viewModel (viewModel: PhotoViewModel):ViewModel
}
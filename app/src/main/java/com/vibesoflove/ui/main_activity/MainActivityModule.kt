package com.vibesoflove.ui.main_activity

import androidx.lifecycle.ViewModel
import com.flipsidegroup.nmt.di.viewmodel.ViewModelKey
import com.vibesoflove.ui.MainActivity
import com.vibesoflove.ui.audio.FragmentAudioPlayerModule
import com.vibesoflove.ui.content.CategoryContainerModule
import com.vibesoflove.ui.content.item.ContentFragmentModule
import com.vibesoflove.ui.content.audio.AudioContentModule
import com.vibesoflove.ui.home.HomeFragmentModule
import com.vibesoflove.ui.home.category_details.FragmentDetailsCategoryModule
import com.vibesoflove.ui.home.deatils.FragmentCategoryModule
import com.vibesoflove.ui.home.video.FragmentVIdeoPlayerModule
import com.vibesoflove.ui.mix.MyMixFragmentModule
import com.vibesoflove.ui.mix.item.ItemMixModule
import com.vibesoflove.ui.mix.item.audio.AudioItemModule
import com.vibesoflove.ui.mix.item.photo.PhotoMixModule
import com.vibesoflove.ui.mix.item.video.VideoMixFragmentModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @ContributesAndroidInjector(modules = [
        MainActivityViewModelModule::class,
        HomeFragmentModule::class,
        FragmentCategoryModule::class,
        FragmentDetailsCategoryModule::class,
        FragmentVIdeoPlayerModule::class,
        FragmentAudioPlayerModule::class,
        MyMixFragmentModule::class,
        ItemMixModule::class,
        ContentFragmentModule::class,
        PhotoMixModule::class,
        VideoMixFragmentModule::class,
        AudioContentModule::class,
        AudioItemModule::class,
        CategoryContainerModule::class
    ])
    fun activity(): MainActivity
}

@Module
interface MainActivityViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    fun viewModel(viewModel: MainActivityViewModel): ViewModel
}
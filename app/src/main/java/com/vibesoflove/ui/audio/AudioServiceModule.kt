package com.vibesoflove.ui.audio

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AudioServiceModule {
    @ContributesAndroidInjector()
    fun inject() : AudioService
}
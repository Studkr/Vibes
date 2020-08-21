package com.vibesoflove.ui.home.service

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AudioServiceModule {
    @ContributesAndroidInjector()
    fun inject() : AudioService
}
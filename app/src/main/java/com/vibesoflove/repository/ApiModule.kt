package com.vibesoflove.repository

import com.vibesoflove.repository.api.PixelsApi
import com.vibesoflove.rest.RetrofitModule
import com.vibesoflove.system.PHOTO_API
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [
    RetrofitModule::class
])
class ApiModule @Inject constructor(
) {
    @Singleton
    @Provides
    fun provideImage (@Named(PHOTO_API)retrofit: Retrofit)= retrofit.create(PixelsApi::class.java)
}
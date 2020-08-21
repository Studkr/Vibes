package com.vibesoflove.repository

import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.repository.repositoryimpl.PixelRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    ApiModule::class
])
class RepositoryModule {
    @Provides
    fun provideImage (impl:PixelRepositoryImpl):PixelRepository = impl

}
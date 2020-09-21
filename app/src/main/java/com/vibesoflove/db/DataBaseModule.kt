package com.vibesoflove.db

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class DataBaseModule @Inject constructor(
        val context: Context
) {
    val db = DataBase(context)

    @Singleton
    @Provides
    fun provideDataBase(): DataBase = DataBase.invoke(context)

    @Singleton
    @Provides
    fun provideDao(): DataBaseDao = DataBase.invoke(context).dao()

    @Singleton
    @Provides
    fun providePhotoDao():PhotoDao = db.photoDao()

    @Singleton
    @Provides
    fun provideVideoDao(): VideoDao = db.videoDao()

    @Singleton
    @Provides
    fun provideAudio():AudioDao = db.audioDao()

    @Singleton
    @Provides
    fun provideMix():MixContainerDao = db.mix()
}
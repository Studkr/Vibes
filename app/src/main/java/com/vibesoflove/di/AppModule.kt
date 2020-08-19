package com.vibesoflove.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Named
import javax.inject.Singleton

@Module(
        includes = [
            CoroutineContextModule::class,

        ]
)
class AppModule(val app: Application) {

    @Provides
    fun context(): Context = app
}

const val ROH_USE_CASE = "SEARCH_LIST_USE_CASE_COROUTINE_CONTEXT"

@Module
class CoroutineContextModule {

    @Provides
    @Singleton
    @Named(ROH_USE_CASE)
    fun providesCoroutineContext() = Job() + Dispatchers.Default


}
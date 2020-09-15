package com.vibesoflove.di

import android.app.Application
import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.vibesoflove.db.DataBaseModule
import com.vibesoflove.repository.RepositoryModule
import com.vibesoflove.repository.repository.DataBaseRepository
import com.vibesoflove.repository.repositoryimpl.DataBaseRepositoryImpl
import com.vibesoflove.ui.audio.AudioServiceModule
import com.vibesoflove.ui.loading.SplashActivityModule
import com.vibesoflove.ui.main_activity.MainActivityModule
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Named
import javax.inject.Singleton

@Module(
        includes = [
            CoroutineContextModule::class,
            SplashActivityModule::class,
            MainActivityModule::class,
            RepositoryModule::class,
            AudioServiceModule::class,
            DataBaseModule::class
        ]
)
class AppModule(val app: Application) {

    @Provides
    fun context(): Context = app

    @Provides
    @Singleton
    fun fireStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun dataBaseRepository(impl:DataBaseRepositoryImpl) : DataBaseRepository = impl
}

const val ROH_USE_CASE = "SEARCH_LIST_USE_CASE_COROUTINE_CONTEXT"

@Module
class CoroutineContextModule {

    @Provides
    @Singleton
    @Named(ROH_USE_CASE)
    fun providesCoroutineContext() = Job() + Dispatchers.Default


}
package com.vibesoflove.di

import com.vibesoflove.db.DataBaseModule
import com.vibesoflove.system.PHOTO_API
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class
        ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
        fun dataBase (dataBase : DataBaseModule): Builder
    }

    @Named(PHOTO_API)
    fun retrofit(): Retrofit

    @Named(PHOTO_API)
    fun httpClient(): OkHttpClient

}
package com.vibesoflove.rest

import com.vibesoflove.system.PHOTO_API
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Named
import javax.inject.Singleton
import retrofit2.Converter

@Module(includes = [JsonModule::class])
class RetrofitModule {

    @Provides
    @Singleton
    @Named(PHOTO_API)
    fun retrofit(
            @Named(PHOTO_API) baseUrl: String,
            @Named(PHOTO_API)okHttpClient: OkHttpClient,
            converterFactory: Converter.Factory
    ) = createRetrofit(
            baseUrl = baseUrl,
            okHttpClient = okHttpClient,
            converterFactory = converterFactory
    )

    @Provides
    @Singleton
    @Named(PHOTO_API)
    fun baseUrl() = PHOTO_API


    @Provides
    @Singleton
    @Named(PHOTO_API)
    fun okHttpClient(
            @Named(PHOTO_API) loggingInterceptor: HttpLoggingInterceptor
    ) = createOkHttpClient(loggingInterceptor)

    @Provides
    @Singleton
    @Named(PHOTO_API)
    fun loggingInterceptor() = getLoggingInterceptor()

}
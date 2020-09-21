package com.vibesoflove.repository.api

import com.vibesoflove.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PixelsApi {
    @GET("/videos/search")
    suspend fun searchVideo(@Query("query") category: String, @Header("Authorization") auth: String): VideoModel

    @GET("/v1/search")
    suspend fun findPhotoCategory(@Query("query")category:String,@Header("Authorization") auth: String): PopularPhoto

    @GET("/videos/popular")
    suspend fun popularVideo(@Header("Authorization") auth: String): PopularVideoModel

    @GET("v1/curated")
    suspend fun getPopularPhoto(@Header("Authorization") auth: String): PopularPhoto

    @GET("videos/videos/{id}")
    suspend fun findVideoById(@Path("id") id :Long,@Header("Authorization") auth: String): VideoPopular

    @GET("v1/photos/{id}")
    suspend fun findPhotoById(@Path("id")id:Long,@Header("Authorization") auth: String):Photo

}
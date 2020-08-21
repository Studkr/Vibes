package com.vibesoflove.repository.api

import com.vibesoflove.model.VideoModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PixelsApi {
    @GET("/videos/search")
    suspend fun searchVideo(@Query("query") category: String, @Header("Authorization")  auth:String) :VideoModel
}
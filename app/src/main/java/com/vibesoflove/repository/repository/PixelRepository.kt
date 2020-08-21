package com.vibesoflove.repository.repository

import com.vibesoflove.model.VideoModel
import retrofit2.http.GET
import retrofit2.http.Query


interface PixelRepository {
    suspend fun getVideoCategory(category:String) :VideoModel
}
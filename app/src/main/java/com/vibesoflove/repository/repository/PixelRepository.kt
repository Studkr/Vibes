package com.vibesoflove.repository.repository

import com.vibesoflove.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query


interface PixelRepository {

    suspend fun getVideoCategory(category:String) :VideoModel

    suspend fun getPopularVideo():PopularVideoModel

    suspend fun getPopularPhoto():PopularPhoto

   suspend fun findVideoById(id:Long): VideoPopular

    suspend fun findPhotoById(id:Long):Photo
}
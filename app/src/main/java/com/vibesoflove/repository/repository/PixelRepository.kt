package com.vibesoflove.repository.repository

import com.vibesoflove.model.*


interface PixelRepository {

    suspend fun getVideoCategory(category:String) :List<Video>

    suspend fun getPopularVideo():PopularVideoModel

    suspend fun getPopularPhoto():PopularPhoto

   suspend fun findVideoById(id:Long): VideoPopular

    suspend fun findPhotoById(id:Long):Photo
}
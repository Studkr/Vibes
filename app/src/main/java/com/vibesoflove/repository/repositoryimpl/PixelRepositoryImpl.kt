package com.vibesoflove.repository.repositoryimpl

import com.vibesoflove.model.*
import com.vibesoflove.repository.api.PixelsApi
import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.system.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PixelRepositoryImpl @Inject constructor(
        private val api : PixelsApi
):PixelRepository{
    override suspend fun getVideoCategory(category: String): VideoModel  = withContext(Dispatchers.IO){
        api.searchVideo(category, API_KEY)
    }

    override suspend fun getPopularVideo(): PopularVideoModel = withContext(Dispatchers.IO) {
        api.popularVideo(API_KEY)
    }

    override suspend fun getPopularPhoto(): PopularPhoto = withContext(Dispatchers.IO) {
       api.getPopularPhoto(API_KEY)
    }

    override suspend fun findVideoById(id: Long): VideoPopular = withContext(Dispatchers.IO){api.findVideoById(id, API_KEY)}
    override suspend fun findPhotoById(id: Long): Photo = withContext(Dispatchers.IO){
        api.findPhotoById(id, API_KEY)
    }
}
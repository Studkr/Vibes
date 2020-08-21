package com.vibesoflove.repository.repositoryimpl

import com.vibesoflove.model.VideoModel
import com.vibesoflove.repository.api.PixelsApi
import com.vibesoflove.repository.repository.PixelRepository
import com.vibesoflove.system.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PixelRepositoryImpl @Inject constructor(
        private val api : PixelsApi
):PixelRepository{
    override suspend fun getVideoCategory(category: String): VideoModel  = withContext(Dispatchers.IO){
        api.searchVideo(category, API_KEY)
    }

}
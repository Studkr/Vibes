package com.vibesoflove.repository.repository

import com.vibesoflove.db.*
import kotlinx.coroutines.flow.Flow

interface DataBaseRepository {

    suspend fun insert(model:DataBaseEntity)
    suspend fun delete(model:DataBaseEntity)
     fun getAll():Flow<List<DataBaseEntity>>


    fun getSavedVideo(): Flow<List<VideoEntity>>
    suspend fun insertVideo(model:VideoEntity)
    suspend fun deleteVideo(model:VideoEntity)


    suspend fun insertPhoto(model: PhotoEntity)
    suspend fun deletePhoto(model: PhotoEntity)
    fun getSavedPhoto():Flow<List<PhotoEntity>>

    suspend fun insertAudio(model:AudioEntity)
    suspend fun deleteAudio(model:AudioEntity)
    fun getAudioList():Flow<List<AudioEntity>>


    suspend fun insertInContainer(model : MixContainer)
    suspend fun deleteFromContainer(model: MixContainer)
    fun getContainer():Flow<List<MixContainer>>
    suspend fun updateData(model: MixContainer)
}
package com.vibesoflove.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Insert
    suspend fun insertVideo(model:VideoEntity)

    @Delete
    suspend fun delete(model: VideoEntity)

    @Query("SELECT * FROM videoEntity")
    fun getAll(): Flow<List<VideoEntity>>
}
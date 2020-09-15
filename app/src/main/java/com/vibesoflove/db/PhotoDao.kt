package com.vibesoflove.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Insert
    fun insertPhoto(model: PhotoEntity)

    @Delete
    fun delete(model: PhotoEntity)

    @Query("SELECT * FROM saved_photo")
    fun getSavedPhoto(): Flow<List<PhotoEntity>>
}
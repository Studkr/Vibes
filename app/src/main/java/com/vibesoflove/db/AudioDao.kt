package com.vibesoflove.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioDao {
    @Insert
    fun insert(audioModel :AudioEntity)

    @Delete
    fun delete(audioModel: AudioEntity)

    @Query("SELECT*FROM audio")
    fun getAudioList(): Flow<List<AudioEntity>>
}
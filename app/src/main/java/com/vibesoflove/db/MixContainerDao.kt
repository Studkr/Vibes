package com.vibesoflove.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MixContainerDao {
    @Insert()
    fun insert(model:MixContainer)

    @Delete()
    fun delete(model: MixContainer)

    @Query("SELECT * FROM mix_container")
    fun getData(): Flow<List<MixContainer>>

    @Update
    fun update(model: MixContainer)
}
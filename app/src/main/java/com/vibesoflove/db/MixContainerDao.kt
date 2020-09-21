package com.vibesoflove.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MixContainerDao {
    @Insert()
    fun insert(model:MixContainer)

    @Delete()
    fun delete(model: MixContainer)

    @Query("SELECT * FROM mix_container")
    fun getData(): Flow<MixContainer>
}
package com.vibesoflove.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mix_container")
data class MixContainer(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "videoId")
        val videoId : Long,
        @ColumnInfo(name = "audioId")
        val audioId : Long,
        @ColumnInfo(name = "imageId")
        val imageId :Long
)
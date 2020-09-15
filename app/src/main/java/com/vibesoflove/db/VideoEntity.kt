package com.vibesoflove.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videoEntity")
data class VideoEntity(
        @PrimaryKey(autoGenerate = false)
        val id:Long,
)
package com.vibesoflove.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_photo")
data class PhotoEntity(
        @PrimaryKey(autoGenerate = false)
        val id:Long,
)
package com.vibesoflove.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "audio")
data class AudioEntity(
        @PrimaryKey(autoGenerate = false)
        val id: Int,

        @ColumnInfo(name = "audioName")
        val audioName: String,

        @ColumnInfo(name = "audioLink")
        val link: String,

        @ColumnInfo(name = "image")
        val audioImage: String
)
package com.vibesoflove.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class DataBaseEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        @ColumnInfo(name = "mixName")
        val mixName: String,
        @ColumnInfo(name = "videoLink")
        val videoLink: String,
        @ColumnInfo(name = "audioLink")
        val audioLink: String,
        @ColumnInfo(name = "vibroLink")
        val vibroLink: String,
        @ColumnInfo(name = "photoLink")
        val photoLink: String,
        @ColumnInfo(name = "placeholder")
        val placeholder: String
)
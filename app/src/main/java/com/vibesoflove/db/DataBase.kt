package com.vibesoflove.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [
    DataBaseEntity::class,
    VideoEntity::class,
    PhotoEntity::class,
    AudioEntity::class
], version = 4)
abstract class DataBase : RoomDatabase() {

    abstract fun dao(): DataBaseDao
    abstract fun videoDao(): VideoDao
    abstract fun photoDao():PhotoDao
    abstract fun audioDao():AudioDao
    companion object {
        operator fun invoke(context: Context) = Room.databaseBuilder(
                context,
                DataBase::class.java,
                "vibration",
                ).fallbackToDestructiveMigration()
                .build()
    }
}
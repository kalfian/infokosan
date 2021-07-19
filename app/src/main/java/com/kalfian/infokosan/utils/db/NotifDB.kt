package com.kalfian.infokosan.utils.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kalfian.infokosan.models.favorite.Favorite

//Database annotation to specify the entities and set version
@Database(entities = [Favorite::class], version = 2, exportSchema = false)
abstract class NotifDB : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: NotifDB? = null

        fun getDatabase(context: Context): NotifDB {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotifDB::class.java,
                    "favorite_db"
                )
                    .allowMainThreadQueries() //allows Room to executing task in main thread
                    .fallbackToDestructiveMigration() //allows Room to recreate database if no migrations found
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getNotifDao() : FavoriteDao
}
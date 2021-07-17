package com.kalfian.infokosan.utils.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kalfian.infokosan.models.favorite.Favorite

//Database annotation to specify the entities and set version
@Database(entities = [Favorite::class], version = 2, exportSchema = false)
abstract class FavoriteDB : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDB? = null

        fun getDatabase(context: Context): FavoriteDB {
            return INSTANCE ?: synchronized(this) {
                // Create database here
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDB::class.java,
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

    abstract fun getFavoriteDao() : FavoriteDao
}
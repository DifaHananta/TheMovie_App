package com.project.themovie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMovie::class], version = 1)
abstract class FavoriteMovieDatabase : RoomDatabase() {
    companion object {
        private var INSTANCE: FavoriteMovieDatabase? = null

        fun getDatabase(context: Context): FavoriteMovieDatabase {
            if (INSTANCE == null) {
                synchronized(FavoriteMovieDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteMovieDatabase::class.java, "favorite_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteMovieDatabase
        }
    }

    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
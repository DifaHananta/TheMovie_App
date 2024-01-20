package com.project.themovie.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteMovieDao {
    @Insert
    fun addFavorite(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movie")
    fun getFavorite(): LiveData<List<FavoriteMovie>>

    @Query("SELECT count(*) FROM favorite_movie WHERE favorite_movie.id = :id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM favorite_movie WHERE favorite_movie.id = :id")
    fun removeFavorite(id: Int): Int
}
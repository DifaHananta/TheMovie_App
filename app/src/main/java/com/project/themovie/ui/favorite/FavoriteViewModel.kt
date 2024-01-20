package com.project.themovie.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.project.themovie.data.local.FavoriteMovie
import com.project.themovie.data.local.FavoriteMovieDao
import com.project.themovie.data.local.FavoriteMovieDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private val favoriteMovieDao: FavoriteMovieDao?
    private val favoriteMovieDatabase: FavoriteMovieDatabase?

    init {
        favoriteMovieDatabase = FavoriteMovieDatabase.getDatabase(application)
        favoriteMovieDao = favoriteMovieDatabase.favoriteMovieDao()
    }

    fun getFavorite(): LiveData<List<FavoriteMovie>>? {
        return favoriteMovieDao?.getFavorite()
    }

}
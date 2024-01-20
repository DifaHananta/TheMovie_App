package com.project.themovie.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.themovie.data.local.FavoriteMovie
import com.project.themovie.data.local.FavoriteMovieDao
import com.project.themovie.data.local.FavoriteMovieDatabase
import com.project.themovie.data.response.DetailResponse
import com.project.themovie.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailViewModel(application: Application): AndroidViewModel(application) {

    val movie = MutableLiveData<DetailResponse>()
    private val favoriteMovieDao: FavoriteMovieDao?
    private val favoriteMovieDatabase: FavoriteMovieDatabase?
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        favoriteMovieDatabase = FavoriteMovieDatabase.getDatabase(application)
        favoriteMovieDao = favoriteMovieDatabase?.favoriteMovieDao()
    }

    fun setMovieDetail(id: String) {
        ApiConfig.getApiService().getDetailMovie(id)
            .enqueue(object : Callback<DetailResponse> {
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    if (response.isSuccessful) {
                        movie.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getMovieDetail(): LiveData<DetailResponse> {
        return movie
    }

    fun addFavorite(id: Int, title: String, poster: String) {
        executorService.execute {
            val movie = FavoriteMovie(id, poster)
            favoriteMovieDao?.addFavorite(movie)
        }
    }

    suspend fun checkFavorite(id: Int) = favoriteMovieDao?.checkFavorite(id)

    fun removeFavorite(id: Int) {
        executorService.execute {
            favoriteMovieDao?.removeFavorite(id)
        }
    }

}
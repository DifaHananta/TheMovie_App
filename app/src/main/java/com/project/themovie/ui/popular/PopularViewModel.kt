package com.project.themovie.ui.popular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.themovie.data.response.MovieListResponse
import com.project.themovie.data.response.ResultsItem
import com.project.themovie.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularViewModel {

    val listPopular = MutableLiveData<ArrayList<ResultsItem>>()

    fun setPopularMovie() {
        ApiConfig.getApiService().getPopularMovies()
            .enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful) {
                        val movies = response.body()?.results
                        if (!movies.isNullOrEmpty()) {
                            listPopular.postValue(movies as ArrayList<ResultsItem>)
                        } else {
                            listPopular.postValue(ArrayList())
                        }
                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }

            })
    }

    fun getPopularMovie(): LiveData<ArrayList<ResultsItem>> {
        return listPopular
    }

}
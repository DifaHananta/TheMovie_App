package com.project.themovie.ui.highRated

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.themovie.data.response.HighRatedResponse
import com.project.themovie.data.response.ResultsItem
import com.project.themovie.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HighRatedViewModel : ViewModel() {

    val listHighRated = MutableLiveData<ArrayList<ResultsItem>>()

    fun setHighRatedMovie() {
        ApiConfig.getApiService().getHighRatedMovies()
            .enqueue(object : Callback<HighRatedResponse> {
                override fun onResponse(
                    call: Call<HighRatedResponse>,
                    response: Response<HighRatedResponse>
                ) {
                    if (response.isSuccessful) {
                        val movies = response.body()?.results
                        if (!movies.isNullOrEmpty()) {
                            listHighRated.postValue(movies as ArrayList<ResultsItem>)
                        } else {
                            listHighRated.postValue(ArrayList())
                        }
                    }
                }

                override fun onFailure(call: Call<HighRatedResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }

            })
    }

    fun getListHighRated(): LiveData<ArrayList<ResultsItem>> {
        return listHighRated
    }


}
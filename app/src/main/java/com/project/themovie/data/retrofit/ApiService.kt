package com.project.themovie.data.retrofit

import com.project.themovie.data.response.DetailResponse
import com.project.themovie.data.response.MovieListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/top_rated")
    fun getHighRatedMovies(
        @Query("page") page: Int = 1,
//        @Query("size") size: Int = 20
    ): Call<MovieListResponse>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int = 1,
    ): Call<MovieListResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") id: String
    ): Call<DetailResponse>
}
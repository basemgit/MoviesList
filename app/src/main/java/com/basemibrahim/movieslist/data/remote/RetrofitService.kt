package com.basemibrahim.movieslist.data.remote


import com.basemibrahim.movieslist.data.model.api.MoviesResponse
import com.basemibrahim.movieslist.utils.Constants.Companion.NOW_PLAYING_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET(NOW_PLAYING_URL)
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): Response<MoviesResponse>
}
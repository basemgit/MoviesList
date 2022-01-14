package com.basemibrahim.movieslist.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val retrofitService: RetrofitService) {

    suspend fun getNowPlayingMovies(page: Int) =
        retrofitService.getNowPlayingMovies(page)

}
package com.basemibrahim.movieslist.data.remote


import com.basemibrahim.movieslist.data.model.api.movie.MoviesResponse
import com.basemibrahim.movieslist.data.model.api.reviews.ReviewsResponse
import com.basemibrahim.movieslist.utils.Constants.Companion.NOW_PLAYING_URL
import com.basemibrahim.movieslist.utils.Constants.Companion.REVIEWS_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
    @GET(NOW_PLAYING_URL)
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): Response<MoviesResponse>

    @GET(REVIEWS_URL)
    suspend fun getReviews(
        @Path("movie_id", encoded = true) movieId: Int, @Query("page") page: Int
    ): Response<ReviewsResponse>
}
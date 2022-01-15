package com.basemibrahim.movieslist.data

import com.basemibrahim.movieslist.data.local.LocalDataSource
import com.basemibrahim.movieslist.data.model.api.BaseApiResponse
import com.basemibrahim.movieslist.data.model.api.movie.MoviesResponse
import com.basemibrahim.movieslist.data.model.api.reviews.ReviewsResponse
import com.basemibrahim.movieslist.data.remote.RemoteDataSource
import com.basemibrahim.movieslist.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource

) : BaseApiResponse() {

    suspend fun getNowplayingMovies(page: Int): Flow<NetworkResult<MoviesResponse>> {
        return flow<NetworkResult<MoviesResponse>> {
            emit(safeApiCall { remoteDataSource.getNowPlayingMovies(page) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getReviews(movieId: Int, page: Int): Flow<NetworkResult<ReviewsResponse>> {
        return flow<NetworkResult<ReviewsResponse>> {
            emit(safeApiCall { remoteDataSource.getReviews(movieId,page)})
        }.flowOn(Dispatchers.IO)
    }


    suspend fun saveMoviesResponse(response: MoviesResponse) {
        localDataSource.saveMoviesResponse(response)
    }

    suspend fun getResponseFromDb(): Flow<MoviesResponse> {
        return flow<MoviesResponse> {
            emit(localDataSource.getResponse())
        }.flowOn(Dispatchers.IO)
    }





}
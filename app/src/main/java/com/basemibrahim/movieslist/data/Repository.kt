package com.basemibrahim.movieslist.data

import com.basemibrahim.movieslist.data.model.api.BaseApiResponse
import com.basemibrahim.movieslist.data.model.api.MoviesResponse
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
) : BaseApiResponse() {

    suspend fun getNowplayingMovies(page: Int): Flow<NetworkResult<MoviesResponse>> {
        return flow<NetworkResult<MoviesResponse>> {
            emit(safeApiCall { remoteDataSource.getNowPlayingMovies(page) })
        }.flowOn(Dispatchers.IO)
    }





}
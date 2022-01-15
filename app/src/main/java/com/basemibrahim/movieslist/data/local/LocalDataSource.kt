

package com.basemibrahim.movieslist.data.local

import com.basemibrahim.movieslist.data.local.db.dao.MoviesDao
import com.basemibrahim.movieslist.data.model.api.movie.MoviesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalDataSource @Inject constructor(private val moviesDao: MoviesDao) {

    suspend  fun saveMoviesResponse (response: MoviesResponse) {
       withContext(Dispatchers.IO)
       {
           moviesDao.insertResponse(response)
       }
    }

    suspend fun getResponse() : MoviesResponse {
       var response : MoviesResponse
       withContext(Dispatchers.IO)
       {
           response = moviesDao.getResponse()
       }
       return response
    }


}

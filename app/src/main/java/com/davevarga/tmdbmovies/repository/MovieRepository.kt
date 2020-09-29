package com.davevarga.tmdbmovies.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.davevarga.tmdbmovies.db.MovieDao
import com.davevarga.tmdbmovies.models.Movie
import com.davevarga.tmdbmovies.network.GetData
import com.davevarga.tmdbmovies.network.ServiceBuilder
import com.davevarga.tmdbmovies.ui.FilterFragment
import com.davevarga.tmdbmovies.ui.MovieRecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val movieDao: MovieDao) {
    fun getMovieList(): LiveData<List<Movie>> = movieDao.getMovieList()

    suspend fun insertMovieList(minYear: String,  maxYear: String) {
        withContext(Dispatchers.IO) {
            val movieList = ServiceBuilder.getNetworkClient(GetData::class.java)
                .getDataByReleaseWindow(
                    "d00127676d268780e41811f616e4fbb0",
                    minYear + "-01-01",
                    maxYear + "-12-31"
                )


            if(movieList.isSuccessful){

                movieList.body()?.let {
                    movieDao.insertMovieList(it.movieList)
                    Log.d("Repo", movieList.body()?.movieList?.size.toString())

                }
            }
        }
    }

    suspend fun deleteAll() = movieDao.deleteAll()
}
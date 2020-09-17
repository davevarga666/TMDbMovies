package com.davevarga.tmdbmovies.repository

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

    suspend fun insertMovieList() {
        withContext(Dispatchers.IO) {
            val movieList = ServiceBuilder.getNetworkClient(GetData::class.java)
                .getDataByReleaseWindow(
                    "d00127676d268780e41811f616e4fbb0",
                    FilterFragment.range.minYear + "-01-01",
                    FilterFragment.range.maxYear + "-12-31"
                )
            movieDao.insertMovieList(MovieRecyclerAdapter(movieList.body()!!.movieList, null).items)
        }
    }
}
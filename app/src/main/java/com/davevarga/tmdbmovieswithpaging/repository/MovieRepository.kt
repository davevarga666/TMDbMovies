package com.davevarga.tmdbmovieswithpaging.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.davevarga.tmdbmovieswithpaging.models.Movie
import com.davevarga.tmdbmovieswithpaging.network.GetData
import com.davevarga.tmdbmovieswithpaging.network.POST_PER_PAGE
import io.reactivex.disposables.CompositeDisposable

class MovieRepository(private val apiService : GetData) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable, minYear: String, maxYear: String) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable, minYear, maxYear)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun refresh() {
        moviesDataSourceFactory.refresh()
    }

//    fun getNetworkState(): LiveData<NetworkState> {
//        return Transformations.switchMap<MovieDataSource, NetworkState>(
//            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
//    }

}
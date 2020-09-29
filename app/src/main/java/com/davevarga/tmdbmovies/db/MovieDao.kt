package com.davevarga.tmdbmovies.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davevarga.tmdbmovies.models.Movie
import com.davevarga.tmdbmovies.models.MovieList

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_table")
    fun getMovieList(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieList(movieList: List<Movie>)

    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()

}
package com.davevarga.tmdbmovies.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davevarga.tmdbmovies.models.Movie
import com.davevarga.tmdbmovies.models.MovieList
import com.davevarga.tmdbmovies.models.Years
import com.davevarga.tmdbmovies.utils.Converters
import java.time.Year

@Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(
            context: Context
        ): AppDatabase = instance
            ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    )
                        .also { instance = it }
            }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "movieDB"
            ).fallbackToDestructiveMigration().build()
        }
    }
}
package br.edu.ifsp.scl.sdm.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.scl.sdm.moviesmanager.model.entity.MovieProperties

@Dao
interface MovieDao {
    companion object {
        const val MOVIE_TABLE = "movie"
    }

    @Insert
    fun insertMovie(movie: MovieProperties)

    @Query("SELECT * FROM $MOVIE_TABLE")
    fun retrieveMovies(): List<MovieProperties>

    @Update
    fun updateMovie(movie: MovieProperties)

    @Delete
    fun deleteMovie(movie: MovieProperties)

    @Query("SELECT * FROM $MOVIE_TABLE WHERE genre = :genre")
    fun retrieveMoviesByGenre(genre: String): List<MovieProperties>
}

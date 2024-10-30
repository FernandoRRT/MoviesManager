package br.edu.ifsp.scl.sdm.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.ifsp.scl.sdm.moviesmanager.model.dao.MovieDao
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = MovieDao.MOVIE_TABLE)
data class Movie(
    @PrimaryKey
    var name: String, // Nome único do filme
    var year: Int, // Ano de lançamento do filme
    var studio: String, // Produtora ou estúdio
    var duration: Int, // Tempo de duração em minutos
    var watched: Boolean = false, // Flag para indicar se foi assistido
    var rating: Float? = null, // Nota do usuário (0-10)
    var genre: String // Gênero do filme
) : Parcelable

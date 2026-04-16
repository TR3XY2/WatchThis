package com.watchthis.data

import com.watchthis.business.WatchThisRepository
import com.watchthis.business.model.Movie
import com.watchthis.business.model.Phrase
import com.watchthis.data.db.WatchThisDao
import com.watchthis.data.db.entity.FavoriteEntity
import com.watchthis.data.db.entity.SavedWordEntity
import com.watchthis.data.db.entity.WordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WatchThisRepositoryImpl(
    private val dao: WatchThisDao
) : WatchThisRepository {

    override suspend fun searchMovies(term: String): List<Movie> = withContext(Dispatchers.IO) {
        dao.searchMovies(term).map { it.toDomain() }
    }

    override suspend fun getPopularMovies(limit: Int): List<Movie> = withContext(Dispatchers.IO) {
        dao.getPopularMovies(limit).map { it.toDomain() }
    }

    override suspend fun getMoviePhrases(movieId: Int, term: String?): List<Phrase> = withContext(Dispatchers.IO) {
        dao.getMoviePhrases(movieId, term).map { it.toDomain() }
    }

    override suspend fun addFavorite(userId: Int, movieId: Int): Boolean = withContext(Dispatchers.IO) {
        dao.insertFavorite(FavoriteEntity(userId = userId, movieId = movieId)) != -1L
    }

    override suspend fun getFavorites(userId: Int): List<Movie> = withContext(Dispatchers.IO) {
        dao.getFavorites(userId).map { it.toDomain() }
    }

    override suspend fun saveWord(userId: Int, word: String): Boolean = withContext(Dispatchers.IO) {
        val existingWordId = dao.getWordId(word)
        val wordId = if (existingWordId == null) {
            dao.insertWord(WordEntity(word = word)).toInt().takeIf { it > 0 } ?: dao.getWordId(word)
        } else {
            existingWordId
        } ?: return@withContext false

        dao.insertSavedWord(SavedWordEntity(userId = userId, wordId = wordId)) != -1L
    }
}

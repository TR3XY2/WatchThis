package com.watchthis.data

import com.watchthis.business.WatchThisRepository
import com.watchthis.business.model.Movie
import com.watchthis.business.model.Phrase
import com.watchthis.business.model.SavedWordPair
import com.watchthis.data.db.WatchThisDao
import com.watchthis.data.db.entity.FavoriteEntity
import com.watchthis.data.db.entity.SavedWordEntity
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

    override suspend fun removeFavorite(userId: Int, movieId: Int): Boolean = withContext(Dispatchers.IO) {
        dao.deleteFavorite(userId, movieId) > 0
    }

    override suspend fun saveWordPair(userId: Int, wordUk: String, wordEn: String): Boolean = withContext(Dispatchers.IO) {
        dao.insertSavedWord(
            SavedWordEntity(userId = userId, wordEn = wordEn, wordUk = wordUk)
        ) != -1L
    }

    override suspend fun getSavedWords(userId: Int): List<SavedWordPair> = withContext(Dispatchers.IO) {
        dao.getSavedWordPairs(userId).map { it.toSavedWordPair() }
    }

    override suspend fun deleteSavedWordPair(userId: Int, wordUk: String, wordEn: String): Boolean =
        withContext(Dispatchers.IO) {
            dao.deleteSavedWord(userId, wordEn, wordUk) > 0
        }
}

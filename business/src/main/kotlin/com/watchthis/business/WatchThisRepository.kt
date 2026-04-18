package com.watchthis.business

import com.watchthis.business.model.Movie
import com.watchthis.business.model.Phrase
import com.watchthis.business.model.SavedWordPair

interface WatchThisRepository {
    suspend fun searchMovies(term: String): List<Movie>
    suspend fun getPopularMovies(limit: Int = 20): List<Movie>
    suspend fun getMoviePhrases(movieId: Int, term: String?): List<Phrase>
    suspend fun addFavorite(userId: Int, movieId: Int): Boolean
    suspend fun getFavorites(userId: Int): List<Movie>
    suspend fun removeFavorite(userId: Int, movieId: Int): Boolean
    suspend fun saveWordPair(userId: Int, wordUk: String, wordEn: String): Boolean
    suspend fun getSavedWords(userId: Int): List<SavedWordPair>
    suspend fun deleteSavedWordPair(userId: Int, wordUk: String, wordEn: String): Boolean
}

package com.watchthis.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.watchthis.data.db.entity.FavoriteEntity
import com.watchthis.data.db.entity.MovieEntity
import com.watchthis.data.db.entity.MovieWordEntity
import com.watchthis.data.db.entity.PhraseEntity
import com.watchthis.data.db.entity.PhraseWordEntity
import com.watchthis.data.db.entity.SavedWordEntity
import com.watchthis.data.db.entity.SavedWordPairProjection
import com.watchthis.data.db.entity.UserEntity
import com.watchthis.data.db.entity.WordEntity

@Dao
interface WatchThisDao {
    @Query(
        """
        SELECT DISTINCT m.* FROM Movies m
        LEFT JOIN MovieWords mw ON mw.movie_id = m.id
        LEFT JOIN Words w ON w.id = mw.word_id
        LEFT JOIN Phrases p ON p.movie_id = m.id
        WHERE lower(w.word) = lower(:term)
           OR lower(p.text) LIKE '%' || lower(:term) || '%'
           OR lower(p.translation) LIKE '%' || lower(:term) || '%'
        ORDER BY m.rating DESC, m.year DESC
        LIMIT 100
        """
    )
    suspend fun searchMovies(term: String): List<MovieEntity>

    @Query("SELECT * FROM Movies ORDER BY rating DESC, year DESC LIMIT :limit")
    suspend fun getPopularMovies(limit: Int): List<MovieEntity>

    @Query(
        """
        SELECT * FROM Phrases
        WHERE movie_id = :movieId
          AND (
            :term IS NULL
            OR lower(text) LIKE '%' || lower(:term) || '%'
            OR lower(translation) LIKE '%' || lower(:term) || '%'
          )
        ORDER BY start_time
        """
    )
    suspend fun getMoviePhrases(movieId: Int, term: String?): List<PhraseEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity): Long

    @Query(
        """
        SELECT m.* FROM Favorites f
        JOIN Movies m ON m.id = f.movie_id
        WHERE f.user_id = :userId
        ORDER BY m.rating DESC, m.year DESC
        """
    )
    suspend fun getFavorites(userId: Int): List<MovieEntity>

    @Query("DELETE FROM Favorites WHERE user_id = :userId AND movie_id = :movieId")
    suspend fun deleteFavorite(userId: Int, movieId: Int): Int

    @Query("SELECT id FROM Words WHERE lower(word) = lower(:word) LIMIT 1")
    suspend fun getWordId(word: String): Int?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(wordEntity: WordEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSavedWord(savedWordEntity: SavedWordEntity): Long

    @Query(
        """
        SELECT word_en, word_uk FROM SavedWords
        WHERE user_id = :userId
        ORDER BY word_en COLLATE NOCASE ASC
        """
    )
    suspend fun getSavedWordPairs(userId: Int): List<SavedWordPairProjection>

    @Query(
        """
        DELETE FROM SavedWords
        WHERE user_id = :userId AND word_en = :wordEn AND word_uk = :wordUk
        """
    )
    suspend fun deleteSavedWord(userId: Int, wordEn: String, wordUk: String): Int

    @Query("SELECT COUNT(*) FROM Users")
    suspend fun usersCount(): Int

    @Query("SELECT COUNT(*) FROM Movies")
    suspend fun moviesCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsers(items: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(items: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWords(items: List<WordEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhrases(items: List<PhraseEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieWords(items: List<MovieWordEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhraseWords(items: List<PhraseWordEntity>)
}

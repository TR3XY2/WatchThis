package com.watchthis.business

import com.watchthis.business.model.Movie
import com.watchthis.business.model.Phrase
import com.watchthis.business.model.SavedWordPair

class WatchThisInteractor(
    private val repository: WatchThisRepository
) {
    fun validateSearchTerm(term: String): ValidationResult {
        if (term.isBlank()) return ValidationResult(false, ValidationError.BLANK_TERM)
        if (term.trim().length < 2) return ValidationResult(false, ValidationError.SHORT_TERM)
        return ValidationResult(true)
    }

    fun validateUserId(userIdRaw: String): ValidationResult {
        if (userIdRaw.isBlank()) return ValidationResult(false, ValidationError.BLANK_USER_ID)
        val id = userIdRaw.toIntOrNull() ?: return ValidationResult(false, ValidationError.INVALID_USER_ID_NUM)
        if (id <= 0) return ValidationResult(false, ValidationError.NON_POSITIVE_USER_ID)
        return ValidationResult(true)
    }

    fun validateWordPair(wordUk: String, wordEn: String): ValidationResult {
        if (wordUk.isBlank()) return ValidationResult(false, ValidationError.BLANK_UK)
        if (wordEn.isBlank()) return ValidationResult(false, ValidationError.BLANK_EN)
        if (wordUk.trim().length < 2) return ValidationResult(false, ValidationError.SHORT_UK)
        if (wordEn.trim().length < 2) return ValidationResult(false, ValidationError.SHORT_EN)
        if (!WordScriptValidator.looksLikeUkrainianScript(wordUk)) {
            return ValidationResult(false, ValidationError.WRONG_SCRIPT_UK)
        }
        if (!WordScriptValidator.looksLikeEnglishScript(wordEn)) {
            return ValidationResult(false, ValidationError.WRONG_SCRIPT_EN)
        }
        return ValidationResult(true)
    }

    suspend fun searchMovies(term: String): List<Movie> = repository.searchMovies(term.trim())

    suspend fun loadPopularMovies(): List<Movie> = repository.getPopularMovies(20)

    suspend fun loadPhrases(movieId: Int, term: String?): List<Phrase> =
        repository.getMoviePhrases(movieId, term?.trim()?.takeIf { it.isNotEmpty() })

    suspend fun addFavorite(userIdRaw: String, movieId: Int): Boolean =
        repository.addFavorite(userIdRaw.trim().toInt(), movieId)

    suspend fun getFavorites(userIdRaw: String): List<Movie> =
        repository.getFavorites(userIdRaw.trim().toInt())

    suspend fun removeFavorite(userIdRaw: String, movieId: Int): Boolean =
        repository.removeFavorite(userIdRaw.trim().toInt(), movieId)

    suspend fun saveWordPair(userIdRaw: String, wordUk: String, wordEn: String): Boolean =
        repository.saveWordPair(
            userIdRaw.trim().toInt(),
            wordUk.trim(),
            wordEn.trim()
        )

    suspend fun getSavedWords(userIdRaw: String): List<SavedWordPair> =
        repository.getSavedWords(userIdRaw.trim().toInt())

    suspend fun deleteSavedWordPair(userIdRaw: String, wordUk: String, wordEn: String): Boolean =
        repository.deleteSavedWordPair(
            userIdRaw.trim().toInt(),
            wordUk.trim(),
            wordEn.trim()
        )

    suspend fun getOrCreateUserByGoogleId(googleId: String, email: String?, name: String?): Int =
        repository.getOrCreateUserByGoogleId(googleId, email, name)
}

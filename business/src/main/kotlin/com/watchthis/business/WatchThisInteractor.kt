package com.watchthis.business

import com.watchthis.business.model.Movie
import com.watchthis.business.model.Phrase

class WatchThisInteractor(
    private val repository: WatchThisRepository
) {
    fun validateSearchTerm(term: String): ValidationResult {
        if (term.isBlank()) return ValidationResult(false, "Введи слово або фразу")
        if (term.trim().length < 2) return ValidationResult(false, "Мінімум 2 символи")
        return ValidationResult(true)
    }

    fun validateUserId(userIdRaw: String): ValidationResult {
        if (userIdRaw.isBlank()) return ValidationResult(false, "Введи User ID")
        val id = userIdRaw.toIntOrNull() ?: return ValidationResult(false, "User ID має бути числом")
        if (id <= 0) return ValidationResult(false, "User ID має бути > 0")
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

    suspend fun saveWord(userIdRaw: String, word: String): Boolean =
        repository.saveWord(userIdRaw.trim().toInt(), word.trim().lowercase())
}

package com.watchthis.data.db

import com.watchthis.data.db.entity.MovieEntity
import com.watchthis.data.db.entity.MovieWordEntity
import com.watchthis.data.db.entity.PhraseEntity
import com.watchthis.data.db.entity.PhraseWordEntity
import com.watchthis.data.db.entity.UserEntity
import com.watchthis.data.db.entity.WordEntity

object DemoDataSeeder {
    suspend fun seed(dao: WatchThisDao) {
        if (dao.usersCount() > 0) return

        dao.insertUsers(
            listOf(
                UserEntity(email = "demo1@watchthis.local", name = "User One", googleId = "g1", createdAt = "2026-04-16 10:00:00"),
                UserEntity(email = "demo2@watchthis.local", name = "User Two", googleId = "g2", createdAt = "2026-04-16 10:00:00")
            )
        )

        dao.insertMovies(
            listOf(
                MovieEntity(title = "Inception", description = "Dream infiltration mission", year = 2010, rating = 8.8f, posterUrl = null),
                MovieEntity(title = "The Matrix", description = "Simulation and reality", year = 1999, rating = 8.7f, posterUrl = null),
                MovieEntity(title = "Interstellar", description = "Space and time travel", year = 2014, rating = 8.6f, posterUrl = null),
                MovieEntity(title = "The Dark Knight", description = "Gotham and Joker", year = 2008, rating = 9.0f, posterUrl = null),
                MovieEntity(title = "Avatar", description = "Pandora and nature", year = 2009, rating = 7.8f, posterUrl = null),
                MovieEntity(title = "Titanic", description = "Love story on a ship", year = 1997, rating = 7.9f, posterUrl = null),
                MovieEntity(title = "Gladiator", description = "Roman empire revenge", year = 2000, rating = 8.5f, posterUrl = null),
                MovieEntity(title = "Whiplash", description = "Music and discipline", year = 2014, rating = 8.5f, posterUrl = null),
                MovieEntity(title = "Dune", description = "Desert planet and prophecy", year = 2021, rating = 8.0f, posterUrl = null),
                MovieEntity(title = "Arrival", description = "Aliens and language learning", year = 2016, rating = 7.9f, posterUrl = null)
            )
        )

        dao.insertWords(
            listOf(
                WordEntity(word = "dream"),
                WordEntity(word = "reality"),
                WordEntity(word = "space"),
                WordEntity(word = "love"),
                WordEntity(word = "language"),
                WordEntity(word = "music"),
                WordEntity(word = "desert"),
                WordEntity(word = "justice"),
                WordEntity(word = "future"),
                WordEntity(word = "time")
            )
        )

        dao.insertPhrases(
            listOf(
                PhraseEntity(movieId = 1, text = "We need to go deeper.", translation = "Нам треба піти глибше.", startTime = "00:21:00", endTime = "00:21:04"),
                PhraseEntity(movieId = 2, text = "What is real?", translation = "Що таке реальність?", startTime = "00:35:10", endTime = "00:35:13"),
                PhraseEntity(movieId = 3, text = "Love transcends dimensions.", translation = "Любов долає виміри.", startTime = "01:22:01", endTime = "01:22:06"),
                PhraseEntity(movieId = 4, text = "Why so serious?", translation = "Чому такий серйозний?", startTime = "00:48:00", endTime = "00:48:03"),
                PhraseEntity(movieId = 5, text = "I see you.", translation = "Я тебе бачу.", startTime = "01:10:00", endTime = "01:10:02"),
                PhraseEntity(movieId = 6, text = "I will never let go.", translation = "Я ніколи не відпущу.", startTime = "02:15:00", endTime = "02:15:03"),
                PhraseEntity(movieId = 7, text = "Are you not entertained?", translation = "Ви не розважені?", startTime = "01:30:00", endTime = "01:30:04"),
                PhraseEntity(movieId = 8, text = "Not my tempo.", translation = "Не мій темп.", startTime = "00:42:00", endTime = "00:42:02"),
                PhraseEntity(movieId = 9, text = "Fear is the mind-killer.", translation = "Страх вбиває розум.", startTime = "00:55:10", endTime = "00:55:13"),
                PhraseEntity(movieId = 10, text = "Language is the foundation.", translation = "Мова - це основа.", startTime = "01:00:00", endTime = "01:00:05")
            )
        )

        dao.insertMovieWords(
            listOf(
                MovieWordEntity(movieId = 1, wordId = 1, count = 7),
                MovieWordEntity(movieId = 2, wordId = 2, count = 6),
                MovieWordEntity(movieId = 3, wordId = 3, count = 8),
                MovieWordEntity(movieId = 3, wordId = 4, count = 2),
                MovieWordEntity(movieId = 6, wordId = 4, count = 4),
                MovieWordEntity(movieId = 10, wordId = 5, count = 5),
                MovieWordEntity(movieId = 8, wordId = 6, count = 5),
                MovieWordEntity(movieId = 9, wordId = 7, count = 4),
                MovieWordEntity(movieId = 4, wordId = 8, count = 4),
                MovieWordEntity(movieId = 3, wordId = 10, count = 6)
            )
        )

        dao.insertPhraseWords(
            listOf(
                PhraseWordEntity(phraseId = 1, wordId = 1),
                PhraseWordEntity(phraseId = 2, wordId = 2),
                PhraseWordEntity(phraseId = 3, wordId = 4),
                PhraseWordEntity(phraseId = 8, wordId = 6),
                PhraseWordEntity(phraseId = 9, wordId = 7),
                PhraseWordEntity(phraseId = 10, wordId = 5)
            )
        )
    }
}

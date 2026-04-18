package com.watchthis.data.db

import com.watchthis.data.db.entity.MovieEntity
import com.watchthis.data.db.entity.MovieWordEntity
import com.watchthis.data.db.entity.PhraseEntity
import com.watchthis.data.db.entity.PhraseWordEntity
import com.watchthis.data.db.entity.UserEntity
import com.watchthis.data.db.entity.WordEntity

object DemoDataSeeder {
    suspend fun seed(dao: WatchThisDao) {
        if (dao.moviesCount() > 0) return

        dao.insertUsers(
            listOf(
                UserEntity(email = "demo1@watchthis.local", name = "User One", googleId = "g1", createdAt = "2026-04-16 10:00:00"),
                UserEntity(email = "demo2@watchthis.local", name = "User Two", googleId = "g2", createdAt = "2026-04-16 10:00:00")
            )
        )

        dao.insertMovies(
            listOf(
                MovieEntity(title = "Inception", description = "Dream infiltration mission", year = 2010, rating = 8.8f, posterUrl = "https://m.media-amazon.com/images/I/71uKM+LdgFL.jpg"),
                MovieEntity(title = "The Matrix", description = "Simulation and reality", year = 1999, rating = 8.7f, posterUrl = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg"),
                MovieEntity(title = "Interstellar", description = "Space and time travel", year = 2014, rating = 8.6f, posterUrl = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg"),
                MovieEntity(title = "The Dark Knight", description = "Gotham and Joker", year = 2008, rating = 9.0f, posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"),
                MovieEntity(title = "Avatar", description = "Pandora and nature", year = 2009, rating = 7.8f, posterUrl = "https://image.tmdb.org/t/p/w500/kyeqWdyUXW608qlYkRqosgbbJyK.jpg"),
                MovieEntity(title = "Titanic", description = "Love story on a ship", year = 1997, rating = 7.9f, posterUrl = "https://image.tmdb.org/t/p/w500/9xjZS2rlVxm8SFx8kPC3aIGCOYQ.jpg"),
                MovieEntity(title = "Gladiator", description = "Roman empire revenge", year = 2000, rating = 8.5f, posterUrl = "https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg"),
                MovieEntity(title = "Whiplash", description = "Music and discipline", year = 2014, rating = 8.5f, posterUrl = "https://image.tmdb.org/t/p/w500/7fn624j5lj3xTme2SgiLCeuedmO.jpg"),
                MovieEntity(title = "Dune", description = "Desert planet and prophecy", year = 2021, rating = 8.0f, posterUrl = "https://image.tmdb.org/t/p/w500/d5NXSklXo0qyIYkgV94XAgMIckC.jpg"),
                MovieEntity(title = "Arrival", description = "Aliens and language learning", year = 2016, rating = 7.9f, posterUrl = "https://image.tmdb.org/t/p/w500/x2FJsf1ElAgr63Y3PNPtJrcmpoe.jpg")
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
                WordEntity(word = "time"),
                WordEntity(word = "fear"),
                WordEntity(word = "hero"),
                WordEntity(word = "train"),
                WordEntity(word = "world"),
                WordEntity(word = "ocean"),
                WordEntity(word = "entertainment"),
                WordEntity(word = "honor"),
                WordEntity(word = "tempo"),
                WordEntity(word = "spice"),
                WordEntity(word = "mind")
            )
        )

        dao.insertPhrases(
            listOf(
                // Inception (movieId 1) — phrases 1–5
                PhraseEntity(movieId = 1, text = "We need to go deeper.", translation = "Нам треба піти глибше.", startTime = "00:21:00", endTime = "00:21:04"),
                PhraseEntity(movieId = 1, text = "You're waiting for a train.", translation = "Ти чекаєш на потяг.", startTime = "00:45:10", endTime = "00:45:14"),
                PhraseEntity(movieId = 1, text = "A dream within a dream.", translation = "Сон у сні.", startTime = "00:33:00", endTime = "00:33:03"),
                PhraseEntity(movieId = 1, text = "This is a kick.", translation = "Це поштовх.", startTime = "01:12:00", endTime = "01:12:02"),
                PhraseEntity(movieId = 1, text = "The dream has become their reality.", translation = "Сон став їхньою реальністю.", startTime = "01:40:00", endTime = "01:40:05"),
                // The Matrix (2) — 6–10
                PhraseEntity(movieId = 2, text = "There is no spoon.", translation = "Ложки не існує.", startTime = "01:05:00", endTime = "01:05:03"),
                PhraseEntity(movieId = 2, text = "Red pill or blue pill.", translation = "Червона чи синя пігулка.", startTime = "00:28:00", endTime = "00:28:04"),
                PhraseEntity(movieId = 2, text = "Welcome to the real world.", translation = "Ласкаво просимо до справжнього світу.", startTime = "00:52:00", endTime = "00:52:03"),
                PhraseEntity(movieId = 2, text = "I know kung fu.", translation = "Я знаю кунг-фу.", startTime = "01:10:00", endTime = "01:10:02"),
                PhraseEntity(movieId = 2, text = "What is real?", translation = "Що таке реальність?", startTime = "00:35:10", endTime = "00:35:13"),
                // Interstellar (3) — 11–15
                PhraseEntity(movieId = 3, text = "Love transcends dimensions.", translation = "Любов долає виміри.", startTime = "01:22:01", endTime = "01:22:06"),
                PhraseEntity(movieId = 3, text = "We used to look up at the sky.", translation = "Ми колись дивилися в небо.", startTime = "00:15:00", endTime = "00:15:04"),
                PhraseEntity(movieId = 3, text = "Do not go gentle into that good night.", translation = "Не йди тихо в ту добру ніч.", startTime = "02:00:00", endTime = "02:00:05"),
                PhraseEntity(movieId = 3, text = "Murphy's law doesn't mean something bad will happen.", translation = "Закон Мерфі не означає, що станеться щось погане.", startTime = "01:05:00", endTime = "01:05:06"),
                PhraseEntity(movieId = 3, text = "Time is relative.", translation = "Час відносний.", startTime = "01:45:00", endTime = "01:45:02"),
                // The Dark Knight (4) — 16–20
                PhraseEntity(movieId = 4, text = "Why so serious?", translation = "Чому такий серйозний?", startTime = "00:48:00", endTime = "00:48:03"),
                PhraseEntity(movieId = 4, text = "I'm the hero Gotham deserves.", translation = "Я герой, якого заслуговує Готем.", startTime = "01:55:00", endTime = "01:55:04"),
                PhraseEntity(movieId = 4, text = "Some men just want to watch the world burn.", translation = "Деякі люди просто хочуть дивитися, як світ горить.", startTime = "01:20:00", endTime = "01:20:05"),
                PhraseEntity(movieId = 4, text = "You either die a hero...", translation = "Або ти помираєш героєм...", startTime = "01:30:00", endTime = "01:30:03"),
                PhraseEntity(movieId = 4, text = "It's not about what I want.", translation = "Справа не в тому, чого я хочу.", startTime = "01:40:00", endTime = "01:40:03"),
                // Avatar (5) — 21–25
                PhraseEntity(movieId = 5, text = "I see you.", translation = "Я тебе бачу.", startTime = "01:10:00", endTime = "01:10:02"),
                PhraseEntity(movieId = 5, text = "Everything is connected.", translation = "Усе пов'язане.", startTime = "00:55:00", endTime = "00:55:03"),
                PhraseEntity(movieId = 5, text = "The sky people have sent us a message.", translation = "Люди з неба надіслали нам послання.", startTime = "00:40:00", endTime = "00:40:04"),
                PhraseEntity(movieId = 5, text = "We must protect the mother.", translation = "Ми маємо захистити мати-землю.", startTime = "01:25:00", endTime = "01:25:03"),
                PhraseEntity(movieId = 5, text = "This is our land.", translation = "Це наша земля.", startTime = "02:10:00", endTime = "02:10:02"),
                // Titanic (6) — 26–30
                PhraseEntity(movieId = 6, text = "I'm the king of the world!", translation = "Я король світу!", startTime = "00:38:00", endTime = "00:38:03"),
                PhraseEntity(movieId = 6, text = "I'll never let go.", translation = "Я ніколи не відпущу.", startTime = "02:15:00", endTime = "02:15:03"),
                PhraseEntity(movieId = 6, text = "Draw me like one of your French girls.", translation = "Намалюй мене, як одну з твоїх французьких дівчат.", startTime = "00:42:00", endTime = "00:42:04"),
                PhraseEntity(movieId = 6, text = "A woman's heart is a deep ocean of secrets.", translation = "Жіноче серце — глибокий океан таємниць.", startTime = "01:50:00", endTime = "01:50:04"),
                PhraseEntity(movieId = 6, text = "It doesn't look any bigger than the Mauritania.", translation = "Не виглядає більшою за «Мавританію».", startTime = "00:12:00", endTime = "00:12:04"),
                // Gladiator (7) — 31–35
                PhraseEntity(movieId = 7, text = "Are you not entertained?", translation = "Ви не розважені?", startTime = "01:30:00", endTime = "01:30:04"),
                PhraseEntity(movieId = 7, text = "Strength and honor.", translation = "Сила та честь.", startTime = "00:22:00", endTime = "00:22:02"),
                PhraseEntity(movieId = 7, text = "What we do in life echoes in eternity.", translation = "Те, що ми робимо в житті, лунає в вічності.", startTime = "00:18:00", endTime = "00:18:04"),
                PhraseEntity(movieId = 7, text = "My name is Maximus.", translation = "Мене звати Максимус.", startTime = "00:50:00", endTime = "00:50:03"),
                PhraseEntity(movieId = 7, text = "At my signal, unleash hell.", translation = "За моїм сигналом — пустіть пекло.", startTime = "01:05:00", endTime = "01:05:03"),
                // Whiplash (8) — 36–40
                PhraseEntity(movieId = 8, text = "Not my tempo.", translation = "Не мій темп.", startTime = "00:42:00", endTime = "00:42:02"),
                PhraseEntity(movieId = 8, text = "There are no two words more harmful than good job.", translation = "Немає двох слів шкідливіших за «добра робота».", startTime = "00:55:00", endTime = "00:55:05"),
                PhraseEntity(movieId = 8, text = "I was there to push people beyond what's expected.", translation = "Я був там, щоб виштовхувати людей за межі очікуваного.", startTime = "01:10:00", endTime = "01:10:05"),
                PhraseEntity(movieId = 8, text = "Either you're rushing or you're dragging.", translation = "Або ти поспішаєш, або тянеш.", startTime = "00:38:00", endTime = "00:38:03"),
                PhraseEntity(movieId = 8, text = "I know it was you.", translation = "Я знаю, що це був ти.", startTime = "01:25:00", endTime = "01:25:02"),
                // Dune (9) — 41–45
                PhraseEntity(movieId = 9, text = "Fear is the mind-killer.", translation = "Страх вбиває розум.", startTime = "00:55:10", endTime = "00:55:13"),
                PhraseEntity(movieId = 9, text = "The spice must flow.", translation = "Пряність має текти.", startTime = "01:15:00", endTime = "01:15:03"),
                PhraseEntity(movieId = 9, text = "I must not fear.", translation = "Я не повинен боятися.", startTime = "00:56:00", endTime = "00:56:02"),
                PhraseEntity(movieId = 9, text = "My road leads into the desert.", translation = "Мій шлях веде в пустелю.", startTime = "00:30:00", endTime = "00:30:03"),
                PhraseEntity(movieId = 9, text = "This is only the beginning.", translation = "Це лише початок.", startTime = "02:20:00", endTime = "02:20:03"),
                // Arrival (10) — 46–50
                PhraseEntity(movieId = 10, text = "Language is the foundation.", translation = "Мова — це основа.", startTime = "01:00:00", endTime = "01:00:05"),
                PhraseEntity(movieId = 10, text = "If you immerse yourself in another language, you rewire your brain.", translation = "Якщо зануритися в іншу мову, можна перепрошити мозок.", startTime = "00:45:00", endTime = "00:45:06"),
                PhraseEntity(movieId = 10, text = "We do not know if they understand weapon and tool.", translation = "Ми не знаємо, чи розуміють вони різницю між зброєю й інструментом.", startTime = "01:20:00", endTime = "01:20:05"),
                PhraseEntity(movieId = 10, text = "Time is non-linear from their perspective.", translation = "Час нелінійний з їхньої точки зору.", startTime = "01:35:00", endTime = "01:35:04"),
                PhraseEntity(movieId = 10, text = "Now is not the time for small talk.", translation = "Зараз не час для пустої балаканини.", startTime = "00:25:00", endTime = "00:25:03")
            )
        )

        dao.insertMovieWords(
            listOf(
                MovieWordEntity(movieId = 1, wordId = 1, count = 7),
                MovieWordEntity(movieId = 1, wordId = 2, count = 4),
                MovieWordEntity(movieId = 2, wordId = 2, count = 6),
                MovieWordEntity(movieId = 2, wordId = 14, count = 3),
                MovieWordEntity(movieId = 3, wordId = 3, count = 8),
                MovieWordEntity(movieId = 3, wordId = 4, count = 2),
                MovieWordEntity(movieId = 3, wordId = 10, count = 6),
                MovieWordEntity(movieId = 4, wordId = 8, count = 5),
                MovieWordEntity(movieId = 4, wordId = 12, count = 4),
                MovieWordEntity(movieId = 5, wordId = 4, count = 3),
                MovieWordEntity(movieId = 6, wordId = 4, count = 4),
                MovieWordEntity(movieId = 6, wordId = 15, count = 3),
                MovieWordEntity(movieId = 7, wordId = 16, count = 5),
                MovieWordEntity(movieId = 7, wordId = 17, count = 4),
                MovieWordEntity(movieId = 8, wordId = 6, count = 5),
                MovieWordEntity(movieId = 8, wordId = 18, count = 6),
                MovieWordEntity(movieId = 9, wordId = 7, count = 4),
                MovieWordEntity(movieId = 9, wordId = 19, count = 5),
                MovieWordEntity(movieId = 10, wordId = 5, count = 5),
                MovieWordEntity(movieId = 10, wordId = 20, count = 4)
            )
        )

        dao.insertPhraseWords(
            listOf(
                PhraseWordEntity(phraseId = 1, wordId = 1),
                PhraseWordEntity(phraseId = 2, wordId = 13),
                PhraseWordEntity(phraseId = 3, wordId = 1),
                PhraseWordEntity(phraseId = 4, wordId = 10),
                PhraseWordEntity(phraseId = 5, wordId = 2),
                PhraseWordEntity(phraseId = 6, wordId = 20),
                PhraseWordEntity(phraseId = 7, wordId = 2),
                PhraseWordEntity(phraseId = 8, wordId = 14),
                PhraseWordEntity(phraseId = 9, wordId = 2),
                PhraseWordEntity(phraseId = 10, wordId = 2),
                PhraseWordEntity(phraseId = 11, wordId = 4),
                PhraseWordEntity(phraseId = 12, wordId = 3),
                PhraseWordEntity(phraseId = 13, wordId = 10),
                PhraseWordEntity(phraseId = 14, wordId = 9),
                PhraseWordEntity(phraseId = 15, wordId = 10),
                PhraseWordEntity(phraseId = 16, wordId = 12),
                PhraseWordEntity(phraseId = 17, wordId = 12),
                PhraseWordEntity(phraseId = 18, wordId = 14),
                PhraseWordEntity(phraseId = 19, wordId = 12),
                PhraseWordEntity(phraseId = 20, wordId = 8),
                PhraseWordEntity(phraseId = 21, wordId = 4),
                PhraseWordEntity(phraseId = 22, wordId = 14),
                PhraseWordEntity(phraseId = 23, wordId = 3),
                PhraseWordEntity(phraseId = 24, wordId = 4),
                PhraseWordEntity(phraseId = 25, wordId = 7),
                PhraseWordEntity(phraseId = 26, wordId = 14),
                PhraseWordEntity(phraseId = 27, wordId = 4),
                PhraseWordEntity(phraseId = 28, wordId = 4),
                PhraseWordEntity(phraseId = 29, wordId = 15),
                PhraseWordEntity(phraseId = 30, wordId = 3),
                PhraseWordEntity(phraseId = 31, wordId = 16),
                PhraseWordEntity(phraseId = 32, wordId = 17),
                PhraseWordEntity(phraseId = 33, wordId = 10),
                PhraseWordEntity(phraseId = 34, wordId = 12),
                PhraseWordEntity(phraseId = 35, wordId = 16),
                PhraseWordEntity(phraseId = 36, wordId = 18),
                PhraseWordEntity(phraseId = 37, wordId = 6),
                PhraseWordEntity(phraseId = 38, wordId = 9),
                PhraseWordEntity(phraseId = 39, wordId = 18),
                PhraseWordEntity(phraseId = 40, wordId = 5),
                PhraseWordEntity(phraseId = 41, wordId = 11),
                PhraseWordEntity(phraseId = 42, wordId = 19),
                PhraseWordEntity(phraseId = 43, wordId = 11),
                PhraseWordEntity(phraseId = 44, wordId = 7),
                PhraseWordEntity(phraseId = 45, wordId = 9),
                PhraseWordEntity(phraseId = 46, wordId = 5),
                PhraseWordEntity(phraseId = 47, wordId = 5),
                PhraseWordEntity(phraseId = 48, wordId = 20),
                PhraseWordEntity(phraseId = 49, wordId = 10),
                PhraseWordEntity(phraseId = 50, wordId = 10)
            )
        )
    }
}

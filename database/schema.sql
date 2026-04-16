CREATE TABLE IF NOT EXISTS Users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255),
    name VARCHAR(255),
    google_id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    year INT,
    rating FLOAT,
    poster_url TEXT
);

CREATE TABLE IF NOT EXISTS Phrases (
    id SERIAL PRIMARY KEY,
    movie_id INT REFERENCES Movies(id) ON DELETE CASCADE,
    text TEXT,
    translation TEXT,
    start_time TIME,
    end_time TIME
);

CREATE TABLE IF NOT EXISTS Words (
    id SERIAL PRIMARY KEY,
    word VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS MovieWords (
    id SERIAL PRIMARY KEY,
    movie_id INT REFERENCES Movies(id) ON DELETE CASCADE,
    word_id INT REFERENCES Words(id) ON DELETE CASCADE,
    count INT DEFAULT 0,
    CONSTRAINT uq_movie_word UNIQUE(movie_id, word_id)
);

CREATE TABLE IF NOT EXISTS PhraseWords (
    id SERIAL PRIMARY KEY,
    phrase_id INT REFERENCES Phrases(id) ON DELETE CASCADE,
    word_id INT REFERENCES Words(id) ON DELETE CASCADE,
    CONSTRAINT uq_phrase_word UNIQUE(phrase_id, word_id)
);

CREATE TABLE IF NOT EXISTS Favorites (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE,
    movie_id INT REFERENCES Movies(id) ON DELETE CASCADE,
    CONSTRAINT uq_favorite UNIQUE(user_id, movie_id)
);

CREATE TABLE IF NOT EXISTS SavedWords (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES Users(id) ON DELETE CASCADE,
    word_id INT REFERENCES Words(id) ON DELETE CASCADE,
    CONSTRAINT uq_saved_word UNIQUE(user_id, word_id)
);

CREATE INDEX IF NOT EXISTS idx_words_word ON Words(lower(word));
CREATE INDEX IF NOT EXISTS idx_phrases_text ON Phrases USING gin(to_tsvector('simple', coalesce(text, '')));
CREATE INDEX IF NOT EXISTS idx_phrases_translation ON Phrases USING gin(to_tsvector('simple', coalesce(translation, '')));

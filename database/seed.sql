INSERT INTO Users(email, name, google_id)
VALUES ('demo@watchthis.local', 'Demo User', 'google-demo-1')
ON CONFLICT DO NOTHING;

INSERT INTO Movies(title, description, year, rating, poster_url)
VALUES
    ('Inception', 'A thief enters dreams to steal secrets.', 2010, 8.8, 'https://image.tmdb.org/t/p/w500/inception.jpg'),
    ('The Matrix', 'A programmer discovers reality is a simulation.', 1999, 8.7, 'https://image.tmdb.org/t/p/w500/matrix.jpg'),
    ('Interstellar', 'A space journey to save humanity.', 2014, 8.6, 'https://image.tmdb.org/t/p/w500/interstellar.jpg')
ON CONFLICT DO NOTHING;

INSERT INTO Words(word)
VALUES ('dream'), ('reality'), ('space')
ON CONFLICT (word) DO NOTHING;

INSERT INTO Phrases(movie_id, text, translation, start_time, end_time)
VALUES
    ((SELECT id FROM Movies WHERE title = 'Inception' LIMIT 1), 'We need to go deeper.', 'Nam potribno porynuty hlybshe.', '00:21:00', '00:21:04'),
    ((SELECT id FROM Movies WHERE title = 'The Matrix' LIMIT 1), 'What is real?', 'Shcho take realnist?', '00:35:10', '00:35:13'),
    ((SELECT id FROM Movies WHERE title = 'Interstellar' LIMIT 1), 'Love is the one thing we can perceive.', 'Lyubov - tse te, shcho my mozhemo vidchuty.', '01:22:01', '01:22:06')
ON CONFLICT DO NOTHING;

INSERT INTO MovieWords(movie_id, word_id, count)
SELECT m.id, w.id, 1
FROM Movies m
JOIN Words w ON (m.title = 'Inception' AND w.word = 'dream')
   OR (m.title = 'The Matrix' AND w.word = 'reality')
   OR (m.title = 'Interstellar' AND w.word = 'space')
ON CONFLICT (movie_id, word_id) DO NOTHING;

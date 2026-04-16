package com.watchthis.data

import com.watchthis.business.model.Movie
import com.watchthis.business.model.Phrase
import com.watchthis.data.db.entity.MovieEntity
import com.watchthis.data.db.entity.PhraseEntity

fun MovieEntity.toDomain(): Movie = Movie(
    id = id,
    title = title,
    description = description,
    year = year,
    rating = rating,
    posterUrl = posterUrl
)

fun PhraseEntity.toDomain(): Phrase = Phrase(
    id = id,
    movieId = movieId,
    text = text,
    translation = translation,
    startTime = startTime,
    endTime = endTime
)

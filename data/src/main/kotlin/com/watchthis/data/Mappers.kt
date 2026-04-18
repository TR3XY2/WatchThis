package com.watchthis.data

import com.watchthis.business.model.Movie
import com.watchthis.business.model.Phrase
import com.watchthis.business.model.SavedWordPair
import com.watchthis.data.db.entity.MovieEntity
import com.watchthis.data.db.entity.PhraseEntity
import com.watchthis.data.db.entity.SavedWordPairProjection

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

fun SavedWordPairProjection.toSavedWordPair(): SavedWordPair = SavedWordPair(
    wordEn = wordEn,
    wordUk = wordUk
)

package com.watchthis.business.model

data class Phrase(
    val id: Int,
    val movieId: Int,
    val text: String,
    val translation: String,
    val startTime: String,
    val endTime: String
)

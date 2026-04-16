package com.watchthis.business.model

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val year: Int,
    val rating: Float,
    val posterUrl: String?
)

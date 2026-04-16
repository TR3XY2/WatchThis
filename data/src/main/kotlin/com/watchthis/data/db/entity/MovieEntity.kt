package com.watchthis.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val year: Int,
    val rating: Float,
    @ColumnInfo(name = "poster_url") val posterUrl: String?
)

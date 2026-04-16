package com.watchthis.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "MovieWords",
    foreignKeys = [
        ForeignKey(entity = MovieEntity::class, parentColumns = ["id"], childColumns = ["movie_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = WordEntity::class, parentColumns = ["id"], childColumns = ["word_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["movie_id"]),
        Index(value = ["word_id"]),
        Index(value = ["movie_id", "word_id"], unique = true)
    ]
)
data class MovieWordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "word_id") val wordId: Int,
    val count: Int
)

package com.watchthis.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Favorites",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = MovieEntity::class, parentColumns = ["id"], childColumns = ["movie_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["movie_id"]),
        Index(value = ["user_id", "movie_id"], unique = true)
    ]
)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int
)

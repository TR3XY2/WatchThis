package com.watchthis.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "SavedWords",
    foreignKeys = [
        ForeignKey(entity = UserEntity::class, parentColumns = ["id"], childColumns = ["user_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["user_id", "word_en", "word_uk"], unique = true)
    ]
)
data class SavedWordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "word_en") val wordEn: String,
    @ColumnInfo(name = "word_uk") val wordUk: String
)

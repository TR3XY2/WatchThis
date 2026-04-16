package com.watchthis.data.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Words",
    indices = [Index(value = ["word"], unique = true)]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String
)

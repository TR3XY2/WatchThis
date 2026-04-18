package com.watchthis.data.db.entity

import androidx.room.ColumnInfo

data class SavedWordPairProjection(
    @ColumnInfo(name = "word_en") val wordEn: String,
    @ColumnInfo(name = "word_uk") val wordUk: String
)

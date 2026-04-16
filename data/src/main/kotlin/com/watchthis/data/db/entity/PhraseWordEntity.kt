package com.watchthis.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "PhraseWords",
    foreignKeys = [
        ForeignKey(entity = PhraseEntity::class, parentColumns = ["id"], childColumns = ["phrase_id"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = WordEntity::class, parentColumns = ["id"], childColumns = ["word_id"], onDelete = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["phrase_id"]),
        Index(value = ["word_id"]),
        Index(value = ["phrase_id", "word_id"], unique = true)
    ]
)
data class PhraseWordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "phrase_id") val phraseId: Int,
    @ColumnInfo(name = "word_id") val wordId: Int
)

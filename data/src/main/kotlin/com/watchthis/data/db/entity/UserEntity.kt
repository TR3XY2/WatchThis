package com.watchthis.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val name: String,
    @ColumnInfo(name = "google_id") val googleId: String,
    @ColumnInfo(name = "created_at") val createdAt: String
)

package com.watchthis.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.watchthis.data.db.entity.FavoriteEntity
import com.watchthis.data.db.entity.MovieEntity
import com.watchthis.data.db.entity.MovieWordEntity
import com.watchthis.data.db.entity.PhraseEntity
import com.watchthis.data.db.entity.PhraseWordEntity
import com.watchthis.data.db.entity.SavedWordEntity
import com.watchthis.data.db.entity.UserEntity
import com.watchthis.data.db.entity.WordEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserEntity::class,
        MovieEntity::class,
        PhraseEntity::class,
        WordEntity::class,
        MovieWordEntity::class,
        PhraseWordEntity::class,
        FavoriteEntity::class,
        SavedWordEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class WatchThisDatabase : RoomDatabase() {
    abstract fun watchThisDao(): WatchThisDao

    companion object {
        @Volatile
        private var INSTANCE: WatchThisDatabase? = null

        fun getInstance(context: Context): WatchThisDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WatchThisDatabase::class.java,
                    "watchthis.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                        CoroutineScope(Dispatchers.IO).launch {
                            DemoDataSeeder.seed(it.watchThisDao())
                        }
                    }
            }
        }
    }
}

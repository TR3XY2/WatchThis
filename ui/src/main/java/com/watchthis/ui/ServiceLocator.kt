package com.watchthis.ui

import android.content.Context
import com.watchthis.business.WatchThisInteractor
import com.watchthis.data.WatchThisRepositoryImpl
import com.watchthis.data.db.WatchThisDatabase

object ServiceLocator {
    @Volatile
    private var interactor: WatchThisInteractor? = null

    fun provideInteractor(context: Context): WatchThisInteractor {
        return interactor ?: synchronized(this) {
            interactor ?: run {
                val dao = WatchThisDatabase.getInstance(context).watchThisDao()
                val repository = WatchThisRepositoryImpl(dao)
                WatchThisInteractor(repository).also { interactor = it }
            }
        }
    }
}

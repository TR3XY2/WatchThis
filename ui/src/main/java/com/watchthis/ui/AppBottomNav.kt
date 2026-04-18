package com.watchthis.ui

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.watchthis.business.WatchThisInteractor

enum class AppBottomNavTab {
    HOME,
    FAVORITES,
    PROFILE
}

object AppBottomNav {
    /**
     * @param userIdProvider current user id for favorites (read at navigation time)
     */
    fun setup(
        activity: AppCompatActivity,
        bottomNav: BottomNavigationView,
        current: AppBottomNavTab,
        userIdProvider: () -> String
    ) {
        val interactor = ServiceLocator.provideInteractor(activity.applicationContext)
        val highlightId = when (current) {
            AppBottomNavTab.HOME -> R.id.nav_home
            AppBottomNavTab.FAVORITES -> R.id.nav_favorites
            AppBottomNavTab.PROFILE -> R.id.nav_profile
        }
        bottomNav.selectedItemId = highlightId

        bottomNav.setOnItemSelectedListener { item ->
            val userId = userIdProvider()
            when (item.itemId) {
                R.id.nav_home -> handleHome(activity)
                R.id.nav_favorites -> handleFavorites(activity, bottomNav, interactor, highlightId, userId)
                R.id.nav_profile -> handleProfile(activity, userId, current == AppBottomNavTab.PROFILE)
                else -> false
            }
        }
    }

    private fun handleHome(activity: AppCompatActivity): Boolean {
        if (activity is MainActivity) return true
        activity.startActivity(
            Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
        )
        return true
    }

    private fun handleFavorites(
        activity: AppCompatActivity,
        bottomNav: BottomNavigationView,
        interactor: WatchThisInteractor,
        highlightId: Int,
        userId: String
    ): Boolean {
        val validation = interactor.validateUserId(userId)
        if (!validation.isValid) {
            Toast.makeText(activity, validation.error?.toMessage(activity).orEmpty(), Toast.LENGTH_SHORT).show()
            bottomNav.selectedItemId = highlightId
            return false
        }
        if (activity is FavoritesActivity) return true
        val id = userId.trim().toIntOrNull() ?: 1
        activity.startActivity(
            Intent(activity, FavoritesActivity::class.java).apply {
                putExtra("user_id", id)
            }
        )
        return true
    }

    private fun handleProfile(activity: AppCompatActivity, userId: String, alreadyThere: Boolean): Boolean {
        if (alreadyThere) return true
        activity.startActivity(
            Intent(activity, ProfileActivity::class.java).apply {
                putExtra("user_id", userId)
            }
        )
        return true
    }
}

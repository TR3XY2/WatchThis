package com.watchthis.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

object LocaleHelper {
    private const val PREFS = "watchthis_prefs"
    private const val KEY_LANG = "ui_language"

    fun applySavedLocale(context: Context) {
        val tag = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_LANG, null)
            ?: return
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }

    fun saveLocale(context: Context, languageTag: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LANG, languageTag)
            .apply()
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageTag))
    }

    fun getSavedLanguageTag(context: Context): String? =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_LANG, null)
}

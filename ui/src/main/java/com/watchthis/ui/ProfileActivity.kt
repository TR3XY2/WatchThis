package com.watchthis.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {
    private var suppressSpinnerCallback = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val userId = intent.getStringExtra("user_id") ?: "1"

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarProfile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val uiSpinner = findViewById<Spinner>(R.id.spinnerUiLanguage)
        val learningSpinner = findViewById<Spinner>(R.id.spinnerLearningLanguage)

        val langs = listOf(getString(R.string.lang_uk), getString(R.string.lang_en))
        val learning = listOf("English", "Deutsch", "Español")

        uiSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, langs)
        learningSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, learning)

        syncUiLanguageSpinner(uiSpinner)

        uiSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (suppressSpinnerCallback) return
                val tag = if (position == 0) "uk" else "en"
                LocaleHelper.saveLocale(this@ProfileActivity, tag)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        AppBottomNav.setup(this, bottomNav, AppBottomNavTab.PROFILE) { userId }
    }

    override fun onResume() {
        super.onResume()
        findViewById<BottomNavigationView>(R.id.bottomNavigation).selectedItemId = R.id.nav_profile
    }

    private fun syncUiLanguageSpinner(uiSpinner: Spinner) {
        val saved = LocaleHelper.getSavedLanguageTag(this)
        val appLocales = AppCompatDelegate.getApplicationLocales()
        val currentTag = appLocales.get(0)?.language
            ?: resources.configuration.locales.get(0)?.language
        val tag = saved ?: currentTag ?: "en"
        val position = if (tag == "uk") 0 else 1
        suppressSpinnerCallback = true
        uiSpinner.setSelection(position)
        uiSpinner.post { suppressSpinnerCallback = false }
    }
}

package com.watchthis.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarProfile)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val uiSpinner = findViewById<Spinner>(R.id.spinnerUiLanguage)
        val learningSpinner = findViewById<Spinner>(R.id.spinnerLearningLanguage)

        val langs = listOf("Українська", "English")
        val learning = listOf("English", "Deutsch", "Español")

        uiSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, langs)
        learningSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, learning)

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }
}

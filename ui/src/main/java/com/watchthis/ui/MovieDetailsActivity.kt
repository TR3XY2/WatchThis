package com.watchthis.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.watchthis.business.WatchThisInteractor
import kotlinx.coroutines.launch

class MovieDetailsActivity : AppCompatActivity() {
    private val interactor: WatchThisInteractor by lazy { ServiceLocator.provideInteractor(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarDetails)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val movieId = intent.getIntExtra("movie_id", -1)
        val title = intent.getStringExtra("movie_title").orEmpty()
        val desc = intent.getStringExtra("movie_desc").orEmpty()
        val year = intent.getIntExtra("movie_year", 0)
        val rating = intent.getFloatExtra("movie_rating", 0f)
        val term = intent.getStringExtra("search_term")
        val userId = intent.getStringExtra("user_id").orEmpty()

        val tvDetails = findViewById<TextView>(R.id.tvDetails)
        val btnAddFavorite = findViewById<Button>(R.id.btnAddFavorite)

        lifecycleScope.launch {
            val phrases = interactor.loadPhrases(movieId, term)
            val phrasesBlock = if (phrases.isEmpty()) {
                "No phrases found"
            } else {
                phrases.joinToString("\n\n") {
                    "• ${it.text}\n  ${it.translation}\n  ${it.startTime} - ${it.endTime}"
                }
            }

            tvDetails.text = """
                Title: $title
                Year: $year
                Rating: $rating

                Description:
                $desc

                Phrases:
                $phrasesBlock
            """.trimIndent()
        }

        btnAddFavorite.setOnClickListener {
            val validation = interactor.validateUserId(userId)
            if (!validation.isValid) {
                Toast.makeText(this, validation.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val inserted = interactor.addFavorite(userId, movieId)
                Toast.makeText(
                    this@MovieDetailsActivity,
                    if (inserted) "Added to favorites" else "Already in favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

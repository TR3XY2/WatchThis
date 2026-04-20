package com.watchthis.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.watchthis.business.WatchThisInteractor
import com.watchthis.business.model.Phrase
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
        toolbar.title = getString(R.string.toolbar_movie_details)

        val movieId = intent.getIntExtra("movie_id", -1)
        val title = intent.getStringExtra("movie_title").orEmpty()
        val desc = intent.getStringExtra("movie_desc").orEmpty()
        val year = intent.getIntExtra("movie_year", 0)
        val rating = intent.getFloatExtra("movie_rating", 0f)
        val posterUrl = intent.getStringExtra("movie_poster")
        val term = intent.getStringExtra("search_term")
        val userId = UserManager.getCurrentUserIdString()

        val ivPoster = findViewById<ImageView>(R.id.ivPosterDetails)
        val tvDetails = findViewById<TextView>(R.id.tvDetails)
        val btnAddFavorite = findViewById<Button>(R.id.btnAddFavorite)
        val rvPhrases = findViewById<RecyclerView>(R.id.rvPhrases)

        val phraseAdapter = PhraseAdapter { phrase -> confirmSavePhrase(phrase, userId) }
        rvPhrases.layoutManager = LinearLayoutManager(this)
        rvPhrases.adapter = phraseAdapter

        Glide.with(this)
            .load(posterUrl)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .error(android.R.drawable.ic_menu_report_image)
            .into(ivPoster)

        lifecycleScope.launch {
            val phrases = interactor.loadPhrases(movieId, term)

            tvDetails.text = """
                ${getString(R.string.movie_details_title, title)}
                ${getString(R.string.movie_details_year, year)}
                ${getString(R.string.movie_details_rating, rating)}

                ${getString(R.string.movie_details_description_label)}
                $desc
            """.trimIndent()

            if (phrases.isEmpty()) {
                findViewById<TextView>(R.id.tvPhrasesLabel).text =
                    getString(R.string.movie_details_phrases_label) + "\n" + getString(R.string.no_phrases_found)
            } else {
                phraseAdapter.submitList(phrases)
            }
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        AppBottomNav.setup(this, bottomNav, AppBottomNavTab.HOME) { userId }

        btnAddFavorite.setOnClickListener {
            val validation = interactor.validateUserId(userId)
            if (!validation.isValid) {
                Toast.makeText(
                    this,
                    validation.error?.toMessage(this).orEmpty(),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val inserted = interactor.addFavorite(userId, movieId)
                Toast.makeText(
                    this@MovieDetailsActivity,
                    if (inserted) getString(R.string.movie_added_favorite) else getString(R.string.movie_already_favorite),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun confirmSavePhrase(phrase: Phrase, userId: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.phrase_save_dialog_title))
            .setMessage(
                getString(R.string.phrase_save_dialog_message, phrase.text, phrase.translation)
            )
            .setPositiveButton(getString(R.string.phrase_save_dialog_yes)) { _, _ ->
                lifecycleScope.launch {
                    val saved = interactor.saveWordPair(userId, phrase.translation, phrase.text)
                    Toast.makeText(
                        this@MovieDetailsActivity,
                        if (saved) getString(R.string.phrase_save_success)
                        else getString(R.string.phrase_save_already),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .setNegativeButton(getString(R.string.phrase_save_dialog_no), null)
            .show()
    }
}

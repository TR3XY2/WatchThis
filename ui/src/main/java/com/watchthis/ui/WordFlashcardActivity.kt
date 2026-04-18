package com.watchthis.ui

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.watchthis.business.model.SavedWordPair
import java.util.Locale

class WordFlashcardActivity : AppCompatActivity() {

    private lateinit var tvOriginal: TextView
    private lateinit var tvTranslation: TextView
    private lateinit var btnSpeak: ImageButton
    private lateinit var btnNext: Button
    private lateinit var btnRandom: Button

    private var words: List<SavedWordPair> = emptyList()
    private var currentIndex: Int = 0

    private var tts: TextToSpeech? = null
    private var ttsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_flashcard)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarFlashcard)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        tvOriginal = findViewById(R.id.tvFlashcardOriginal)
        tvTranslation = findViewById(R.id.tvFlashcardTranslation)
        btnSpeak = findViewById(R.id.btnSpeak)
        btnNext = findViewById(R.id.btnNextWord)
        btnRandom = findViewById(R.id.btnRandomWord)

        @Suppress("DEPRECATION", "UNCHECKED_CAST")
        val parcels = intent.getSerializableExtra(EXTRA_WORDS) as? ArrayList<*>
        words = parcels
            ?.filterIsInstance<SavedWordPairParcel>()
            ?.map { SavedWordPair(wordEn = it.wordEn, wordUk = it.wordUk) }
            ?: emptyList()
        currentIndex = intent.getIntExtra(EXTRA_START_INDEX, 0)

        if (words.isEmpty()) {
            finish()
            return
        }

        tts = TextToSpeech(this) { status ->
            ttsReady = status == TextToSpeech.SUCCESS
            if (ttsReady) {
                tts?.language = Locale.ENGLISH
            }
        }

        showCurrent()

        btnSpeak.setOnClickListener {
            speakCurrent()
        }

        btnNext.setOnClickListener {
            currentIndex = (currentIndex + 1) % words.size
            showCurrent()
        }

        btnRandom.setOnClickListener {
            if (words.size > 1) {
                var next = (Math.random() * words.size).toInt()
                if (next == currentIndex) next = (next + 1) % words.size
                currentIndex = next
            }
            showCurrent()
        }

        val userId = intent.getStringExtra(EXTRA_USER_ID) ?: "1"
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        AppBottomNav.setup(this, bottomNav, AppBottomNavTab.FAVORITES) { userId }
    }

    private fun showCurrent() {
        val pair = words[currentIndex]
        tvOriginal.text = pair.wordEn
        tvTranslation.text = pair.wordUk
    }

    private fun speakCurrent() {
        if (!ttsReady) {
            Toast.makeText(this, getString(R.string.flashcard_tts_not_ready), Toast.LENGTH_SHORT).show()
            return
        }
        val text = words[currentIndex].wordEn
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "word_$currentIndex")
    }

    override fun onDestroy() {
        tts?.stop()
        tts?.shutdown()
        super.onDestroy()
    }

    companion object {
        const val EXTRA_WORDS = "words"
        const val EXTRA_START_INDEX = "start_index"
        const val EXTRA_USER_ID = "user_id"

        fun start(
            activity: AppCompatActivity,
            words: List<SavedWordPair>,
            startIndex: Int,
            userId: String
        ) {
            val parcels = ArrayList(words.map { SavedWordPairParcel(it.wordEn, it.wordUk) })
            activity.startActivity(
                Intent(activity, WordFlashcardActivity::class.java).apply {
                    putExtra(EXTRA_WORDS, parcels)
                    putExtra(EXTRA_START_INDEX, startIndex)
                    putExtra(EXTRA_USER_ID, userId)
                }
            )
        }
    }
}

data class SavedWordPairParcel(val wordEn: String, val wordUk: String) : java.io.Serializable

package com.watchthis.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.watchthis.business.WatchThisInteractor
import com.watchthis.business.model.Movie
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val interactor: WatchThisInteractor by lazy { ServiceLocator.provideInteractor(applicationContext) }

    private lateinit var etUserId: EditText
    private lateinit var etSearch: EditText
    private lateinit var etWord: EditText
    private lateinit var tvStatus: TextView
    private lateinit var adapter: MovieAdapter

    private var currentMovies: List<Movie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setOnMenuItemClickListener { item ->
            handleTopMenu(item)
            true
        }

        etUserId = findViewById(R.id.etUserId)
        etSearch = findViewById(R.id.etSearch)
        etWord = findViewById(R.id.etWord)
        tvStatus = findViewById(R.id.tvStatus)

        etUserId.setText("1")

        adapter = MovieAdapter(
            onClick = { openMovieDetails(it) },
            onLongClick = { anchor, movie -> showContextMenu(anchor, movie) }
        )

        findViewById<RecyclerView>(R.id.rvMovies).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        findViewById<Button>(R.id.btnSearch).setOnClickListener { searchMovies() }
        findViewById<Button>(R.id.btnPopular).setOnClickListener { loadPopular() }
        findViewById<Button>(R.id.btnFavorites).setOnClickListener { openFavoritesScreen() }
        findViewById<Button>(R.id.btnSaveWord).setOnClickListener { saveWord() }

        findViewById<BottomNavigationView>(R.id.bottomNavigation).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    true
                }
                R.id.nav_favorites -> {
                    openFavoritesScreen()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        loadPopular()
    }

    private fun handleTopMenu(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_popular -> loadPopular()
            R.id.menu_favorites -> openFavoritesScreen()
            R.id.menu_profile -> startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun searchMovies() {
        val validation = interactor.validateSearchTerm(etSearch.text.toString())
        if (!validation.isValid) {
            etSearch.error = validation.message
            return
        }

        lifecycleScope.launch {
            val movies = interactor.searchMovies(etSearch.text.toString())
            currentMovies = movies
            adapter.submitList(movies)
            updateStatus("Знайдено ${movies.size} фільмів")
        }
    }

    private fun loadPopular() {
        lifecycleScope.launch {
            val movies = interactor.loadPopularMovies()
            currentMovies = movies
            adapter.submitList(movies)
            updateStatus("Популярні: ${movies.size}")
        }
    }

    private fun openFavoritesScreen() {
        val validation = interactor.validateUserId(etUserId.text.toString())
        if (!validation.isValid) {
            etUserId.error = validation.message
            return
        }

        val intent = Intent(this, FavoritesActivity::class.java)
        intent.putExtra("user_id", etUserId.text.toString().trim().toInt())
        startActivity(intent)
    }

    private fun saveWord() {
        val userValidation = interactor.validateUserId(etUserId.text.toString())
        if (!userValidation.isValid) {
            etUserId.error = userValidation.message
            return
        }

        val wordValidation = interactor.validateSearchTerm(etWord.text.toString())
        if (!wordValidation.isValid) {
            etWord.error = wordValidation.message
            return
        }

        lifecycleScope.launch {
            val inserted = interactor.saveWord(etUserId.text.toString(), etWord.text.toString())
            val msg = if (inserted) "Слово збережено" else "Слово вже існує"
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            updateStatus(msg)
        }
    }

    private fun showContextMenu(anchor: View, movie: Movie) {
        val popup = PopupMenu(this, anchor)
        popup.menuInflater.inflate(R.menu.movie_context_menu, popup.menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.context_show_phrases -> {
                    openMovieDetails(movie)
                    true
                }
                R.id.context_add_favorite -> {
                    addFavorite(movie)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun addFavorite(movie: Movie) {
        val validation = interactor.validateUserId(etUserId.text.toString())
        if (!validation.isValid) {
            etUserId.error = validation.message
            return
        }

        lifecycleScope.launch {
            val inserted = interactor.addFavorite(etUserId.text.toString(), movie.id)
            val msg = if (inserted) "Додано у вибране" else "Вже у вибраному"
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
            updateStatus(msg)
        }
    }

    private fun openMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movie_id", movie.id)
        intent.putExtra("movie_title", movie.title)
        intent.putExtra("movie_desc", movie.description)
        intent.putExtra("movie_year", movie.year)
        intent.putExtra("movie_rating", movie.rating)
        intent.putExtra("search_term", etSearch.text.toString())
        intent.putExtra("user_id", etUserId.text.toString())
        startActivity(intent)
    }

    private fun updateStatus(text: String) {
        tvStatus.text = text
    }
}

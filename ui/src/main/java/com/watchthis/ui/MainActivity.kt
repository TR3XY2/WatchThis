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
import com.google.android.material.chip.ChipGroup
import com.watchthis.business.WatchThisInteractor
import com.watchthis.business.model.Movie
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val interactor: WatchThisInteractor by lazy { ServiceLocator.provideInteractor(applicationContext) }

    private lateinit var etUserId: EditText
    private lateinit var etSearch: EditText
    private lateinit var tvStatus: TextView
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var adapter: MovieAdapter
    private lateinit var chipGroupSort: ChipGroup

    private var currentMovies: List<Movie> = emptyList()

    private enum class SortOrder { RATING, YEAR, TITLE }
    private var currentSort = SortOrder.RATING

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

        chipGroupSort = findViewById(R.id.chipGroupSort)
        chipGroupSort.setOnCheckedStateChangeListener { _, _ ->
            val checkedId = chipGroupSort.checkedChipId
            currentSort = when (checkedId) {
                R.id.chipSortYear -> SortOrder.YEAR
                R.id.chipSortTitle -> SortOrder.TITLE
                else -> SortOrder.RATING
            }
            applySortAndSubmit()
        }

        bottomNav = findViewById(R.id.bottomNavigation)
        AppBottomNav.setup(this, bottomNav, AppBottomNavTab.HOME) { etUserId.text.toString() }

        loadPopular()
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.nav_home
    }

    private fun handleTopMenu(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_popular -> loadPopular()
            R.id.menu_favorites -> openFavoritesScreen()
            R.id.menu_profile -> startActivity(
                Intent(this, ProfileActivity::class.java).apply {
                    putExtra("user_id", etUserId.text.toString())
                }
            )
        }
    }

    private fun searchMovies() {
        val validation = interactor.validateSearchTerm(etSearch.text.toString())
        if (!validation.isValid) {
            etSearch.error = validation.error?.toMessage(this)
            return
        }

        lifecycleScope.launch {
            val movies = interactor.searchMovies(etSearch.text.toString())
            currentMovies = movies
            applySortAndSubmit()
            updateStatus(getString(R.string.found_movies, movies.size))
        }
    }

    private fun loadPopular() {
        lifecycleScope.launch {
            val movies = interactor.loadPopularMovies()
            currentMovies = movies
            applySortAndSubmit()
            updateStatus(getString(R.string.popular_status, movies.size))
        }
    }

    private fun applySortAndSubmit() {
        val sorted = when (currentSort) {
            SortOrder.RATING -> currentMovies.sortedByDescending { it.rating }
            SortOrder.YEAR -> currentMovies.sortedByDescending { it.year }
            SortOrder.TITLE -> currentMovies.sortedBy { it.title.lowercase() }
        }
        adapter.submitList(sorted)
    }

    /** @return true if Favorites screen was opened */
    private fun openFavoritesScreen(): Boolean {
        val validation = interactor.validateUserId(etUserId.text.toString())
        if (!validation.isValid) {
            etUserId.error = validation.error?.toMessage(this)
            return false
        }
        startActivity(
            Intent(this, FavoritesActivity::class.java).apply {
                putExtra("user_id", etUserId.text.toString().trim().toInt())
            }
        )
        return true
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
            etUserId.error = validation.error?.toMessage(this)
            return
        }

        lifecycleScope.launch {
            val inserted = interactor.addFavorite(etUserId.text.toString(), movie.id)
            val msg = if (inserted) getString(R.string.added_favorite) else getString(R.string.already_favorite)
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
        intent.putExtra("movie_poster", movie.posterUrl)
        intent.putExtra("search_term", etSearch.text.toString())
        intent.putExtra("user_id", etUserId.text.toString())
        startActivity(intent)
    }

    private fun updateStatus(text: String) {
        tvStatus.text = text
    }
}

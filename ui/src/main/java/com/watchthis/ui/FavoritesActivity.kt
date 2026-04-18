package com.watchthis.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.watchthis.business.WatchThisInteractor
import kotlinx.coroutines.launch

class FavoritesActivity : AppCompatActivity() {
    private val interactor: WatchThisInteractor by lazy { ServiceLocator.provideInteractor(applicationContext) }

    private lateinit var adapter: MovieAdapter
    private lateinit var bottomNav: BottomNavigationView

    private val userId: Int by lazy { intent.getIntExtra("user_id", 1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarFavorites)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        findViewById<Button>(R.id.btnSavedWords).setOnClickListener {
            startActivity(Intent(this, SavedWordsActivity::class.java).apply {
                putExtra("user_id", userId)
            })
        }
        adapter = MovieAdapter(
            onClick = { movie ->
                startActivity(Intent(this, MovieDetailsActivity::class.java).apply {
                    putExtra("movie_id", movie.id)
                    putExtra("movie_title", movie.title)
                    putExtra("movie_desc", movie.description)
                    putExtra("movie_year", movie.year)
                    putExtra("movie_rating", movie.rating)
                    putExtra("movie_poster", movie.posterUrl)
                    putExtra("search_term", "")
                    putExtra("user_id", userId.toString())
                })
            },
            onLongClick = { _, _ -> },
            onRemoveFavorite = { movie ->
                lifecycleScope.launch {
                    val removed = interactor.removeFavorite(userId.toString(), movie.id)
                    if (removed) {
                        Toast.makeText(this@FavoritesActivity, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show()
                        refreshList()
                    }
                }
            }
        )

        findViewById<RecyclerView>(R.id.rvFavorites).apply {
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            this.adapter = this@FavoritesActivity.adapter
        }

        bottomNav = findViewById(R.id.bottomNavigation)
        AppBottomNav.setup(this, bottomNav, AppBottomNavTab.FAVORITES) { userId.toString() }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        bottomNav.selectedItemId = R.id.nav_favorites
        refreshList()
    }

    private fun refreshList() {
        lifecycleScope.launch {
            adapter.submitList(interactor.getFavorites(userId.toString()))
        }
    }
}

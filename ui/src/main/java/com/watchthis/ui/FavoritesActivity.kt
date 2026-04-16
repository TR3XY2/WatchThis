package com.watchthis.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.watchthis.business.WatchThisInteractor
import kotlinx.coroutines.launch

class FavoritesActivity : AppCompatActivity() {
    private val interactor: WatchThisInteractor by lazy { ServiceLocator.provideInteractor(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarFavorites)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val userId = intent.getIntExtra("user_id", 1)
        val adapter = MovieAdapter(
            onClick = { movie ->
                startActivity(Intent(this, MovieDetailsActivity::class.java).apply {
                    putExtra("movie_id", movie.id)
                    putExtra("movie_title", movie.title)
                    putExtra("movie_desc", movie.description)
                    putExtra("movie_year", movie.year)
                    putExtra("movie_rating", movie.rating)
                    putExtra("search_term", "")
                    putExtra("user_id", userId.toString())
                })
            },
            onLongClick = { _, _ -> }
        )

        findViewById<RecyclerView>(R.id.rvFavorites).apply {
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
            this.adapter = adapter
        }

        lifecycleScope.launch {
            adapter.submitList(interactor.getFavorites(userId.toString()))
        }
    }
}

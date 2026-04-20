package com.watchthis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.watchthis.business.WatchThisInteractor
import com.watchthis.business.model.SavedWordPair
import kotlinx.coroutines.launch

class SavedWordsActivity : AppCompatActivity() {
    private val interactor: WatchThisInteractor by lazy { ServiceLocator.provideInteractor(applicationContext) }

    private lateinit var etUk: EditText
    private lateinit var etEn: EditText
    private lateinit var tvEmpty: TextView
    private lateinit var rv: RecyclerView
    private lateinit var adapter: SavedWordAdapter
    private lateinit var bottomNav: BottomNavigationView

    private val userId: String by lazy { UserManager.getCurrentUserIdString() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_words)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbarSavedWords)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        etUk = findViewById(R.id.etSavedWordUk)
        etEn = findViewById(R.id.etSavedWordEn)
        tvEmpty = findViewById(R.id.tvSavedWordsEmpty)
        rv = findViewById(R.id.rvSavedWords)
        adapter = SavedWordAdapter(
            onDelete = { pair -> deletePair(pair) },
            onClick = { pair ->
                val allWords = adapter.getItems()
                val index = allWords.indexOf(pair).coerceAtLeast(0)
                WordFlashcardActivity.start(this, allWords, index, userId)
            }
        )
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<Button>(R.id.btnSaveWordPair).setOnClickListener { saveWordPair() }

        bottomNav = findViewById(R.id.bottomNavigation)
        AppBottomNav.setup(this, bottomNav, AppBottomNavTab.FAVORITES) { userId }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun deletePair(pair: SavedWordPair) {
        lifecycleScope.launch {
            val ok = interactor.deleteSavedWordPair(userId, pair.wordUk, pair.wordEn)
            if (ok) {
                Toast.makeText(this@SavedWordsActivity, R.string.word_deleted, Toast.LENGTH_SHORT).show()
                refreshList()
            } else {
                Toast.makeText(this@SavedWordsActivity, R.string.word_delete_failed, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveWordPair() {
        etUk.error = null
        etEn.error = null
        val pairValidation = interactor.validateWordPair(etUk.text.toString(), etEn.text.toString())
        if (!applyWordPairValidationErrors(this, pairValidation, etUk, etEn)) return

        lifecycleScope.launch {
            val inserted = interactor.saveWordPair(userId, etUk.text.toString(), etEn.text.toString())
            val msg = if (inserted) getString(R.string.word_saved) else getString(R.string.word_exists)
            Toast.makeText(this@SavedWordsActivity, msg, Toast.LENGTH_SHORT).show()
            if (inserted) {
                etUk.text.clear()
                etEn.text.clear()
                refreshList()
            }
        }
    }

    private fun refreshList() {
        lifecycleScope.launch {
            val words = interactor.getSavedWords(userId)
            adapter.submitList(words)
            val empty = words.isEmpty()
            tvEmpty.visibility = if (empty) View.VISIBLE else View.GONE
            rv.visibility = if (empty) View.GONE else View.VISIBLE
        }
    }
}

private class SavedWordAdapter(
    private val onDelete: (SavedWordPair) -> Unit,
    private val onClick: (SavedWordPair) -> Unit
) : RecyclerView.Adapter<SavedWordAdapter.VH>() {
    private val items = mutableListOf<SavedWordPair>()

    fun submitList(data: List<SavedWordPair>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun getItems(): List<SavedWordPair> = items.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_word, parent, false)
        return VH(v, onDelete, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class VH(
        itemView: View,
        private val onDelete: (SavedWordPair) -> Unit,
        private val onClick: (SavedWordPair) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val en: TextView = itemView.findViewById(R.id.tvWordEn)
        private val uk: TextView = itemView.findViewById(R.id.tvWordUk)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDeleteSavedWord)

        fun bind(pair: SavedWordPair) {
            val c = itemView.context
            en.text = c.getString(R.string.saved_word_label_en) + ": " + pair.wordEn
            uk.text = c.getString(R.string.saved_word_label_uk) + ": " + pair.wordUk
            btnDelete.setOnClickListener { onDelete(pair) }
            itemView.setOnClickListener { onClick(pair) }
        }
    }
}

package com.watchthis.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.watchthis.business.model.Phrase

class PhraseAdapter(
    private val onClick: (Phrase) -> Unit
) : RecyclerView.Adapter<PhraseAdapter.VH>() {

    private val items = mutableListOf<Phrase>()

    fun submitList(data: List<Phrase>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_phrase, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { onClick(items[position]) }
    }

    override fun getItemCount() = items.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvEn: TextView = itemView.findViewById(R.id.tvPhraseEn)
        private val tvUk: TextView = itemView.findViewById(R.id.tvPhraseUk)

        fun bind(phrase: Phrase) {
            tvEn.text = phrase.text
            tvUk.text = phrase.translation
        }
    }
}

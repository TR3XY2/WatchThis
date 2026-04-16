package com.watchthis.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.watchthis.business.model.Movie

class MovieAdapter(
    private val onClick: (Movie) -> Unit,
    private val onLongClick: (View, Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val items = mutableListOf<Movie>()

    fun submitList(data: List<Movie>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Movie = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { onClick(movie) }
        holder.itemView.setOnLongClickListener {
            onLongClick(holder.itemView, movie)
            true
        }
    }

    override fun getItemCount(): Int = items.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val meta: TextView = itemView.findViewById(R.id.tvMovieMeta)
        private val desc: TextView = itemView.findViewById(R.id.tvMovieDesc)

        fun bind(movie: Movie) {
            title.text = movie.title
            meta.text = "${movie.year} | rating ${movie.rating}"
            desc.text = movie.description
        }
    }
}

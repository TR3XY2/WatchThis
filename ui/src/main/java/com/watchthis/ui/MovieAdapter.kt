package com.watchthis.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.watchthis.business.model.Movie

class MovieAdapter(
    private val onClick: (Movie) -> Unit,
    private val onLongClick: (View, Movie) -> Unit,
    private val onRemoveFavorite: ((Movie) -> Unit)? = null
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
        holder.bind(movie, onRemoveFavorite)
        holder.itemView.setOnClickListener { onClick(movie) }
        holder.itemView.setOnLongClickListener {
            onLongClick(holder.itemView, movie)
            true
        }
    }

    override fun getItemCount(): Int = items.size

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.ivMoviePoster)
        private val title: TextView = itemView.findViewById(R.id.tvMovieTitle)
        private val meta: TextView = itemView.findViewById(R.id.tvMovieMeta)
        private val desc: TextView = itemView.findViewById(R.id.tvMovieDesc)
        private val btnRemove: ImageButton = itemView.findViewById(R.id.btnRemoveFavorite)

        fun bind(movie: Movie, onRemoveFavorite: ((Movie) -> Unit)?) {
            Glide.with(itemView)
                .load(movie.posterUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(poster)
            title.text = movie.title
            meta.text = itemView.context.getString(R.string.movie_row_meta, movie.year, movie.rating)
            desc.text = movie.description
            if (onRemoveFavorite != null) {
                btnRemove.visibility = View.VISIBLE
                btnRemove.setOnClickListener { onRemoveFavorite(movie) }
            } else {
                btnRemove.visibility = View.GONE
                btnRemove.setOnClickListener(null)
            }
        }
    }
}

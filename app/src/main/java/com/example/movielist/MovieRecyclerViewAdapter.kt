package com.example.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.R.id
import com.bumptech.glide.Glide


class MovieRecyclerViewAdapter (
    private val movies: MutableList<Movie>,
)  : RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieViewHolder>()
{
    class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movie? = null
        val mMovieTitle: TextView
        val mMovieDescription: TextView
        val mMovieImage: ImageView

        init {
            mMovieTitle = itemView.findViewById<TextView>(R.id.title)
            mMovieDescription = itemView.findViewById<TextView>(R.id.description)
            mMovieImage = itemView.findViewById<ImageView>(R.id.image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.movie, parent, false)
        return MovieViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies.get(position)

        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500"+movie.movieImageUrl)
            .centerInside()
            .into(holder.mMovieImage)

    }

    override fun getItemCount(): Int {
        return movies.size
    }

}
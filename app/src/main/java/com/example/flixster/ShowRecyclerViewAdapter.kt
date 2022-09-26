package com.example.flixster

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val SHOW_EXTRA = "SHOW_EXTRA"

class ShowRecyclerViewAdapter(
    private val shows: List<Show>, private val context: Context
)  : RecyclerView.Adapter<ShowRecyclerViewAdapter.ShowViewHolder>()
{
    inner class ShowViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        var mItem: Show? = null
        val mShowTitle: TextView
        val mShowImage: ImageView

        init {
            mShowTitle = itemView.findViewById<TextView>(R.id.title)
            mShowImage = itemView.findViewById<ImageView>(R.id.image)

            mView.setOnClickListener(this)


        }

        override fun onClick(p0: View?) {
            val show = shows[adapterPosition]
            val intent = Intent(context, ShowDetailActivity::class.java)
            intent.putExtra(SHOW_EXTRA, show)
            context.startActivity(intent)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.show, parent, false)
        return ShowViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = shows[position]
        holder.mShowTitle.text = show.title
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500"+show.showImageUrl)
            .centerInside()
            .into(holder.mShowImage)

    }


    override fun getItemCount(): Int {
        return shows.size
    }

}
package com.example.flixster

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "ShowDetailActivity"

class ShowDetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_activity_detail)
        var show_image = findViewById<ImageView>(R.id.detailsActivityImage)
        var show_title = findViewById<TextView>(R.id.detailsActivityName)
        var show_description = findViewById<TextView>(R.id.detailsActivityDescription)
        var show_fad = findViewById<TextView>(R.id.detailsActivityFirstAirDate)
        var show_average_vote = findViewById<TextView>(R.id.detailsActivityVotingAverage)



        // TODO: Get the extra from the Intent
        val show = intent.getSerializableExtra(SHOW_EXTRA) as Show

        // TODO: Set the title, byline, and abstract information from the article
        show_title.text = show.title
        show_description.text = show.description
        show_fad.text = "First air date: " + show.firstAirDate
        show_average_vote.text = "Voting average: " + show.voteAverage

        // TODO: Load the media image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500"+show.showImageUrl)
            .into(show_image)
    }
}
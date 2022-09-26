package com.example.flixster

import com.google.gson.annotations.SerializedName

class Movie () {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("movie_image")
    var movieImageUrl: String? = null
}
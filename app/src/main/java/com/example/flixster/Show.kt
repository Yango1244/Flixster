package com.example.flixster

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Show (): Serializable {
    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("show_image")
    var showImageUrl: String? = null

    @SerializedName("first_air_date")
    var firstAirDate: String? = null

    @SerializedName("vote_average")
    var voteAverage: String? = null
}
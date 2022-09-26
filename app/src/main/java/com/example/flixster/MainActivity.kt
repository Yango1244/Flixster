package com.example.flixster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import com.google.gson.Gson
import org.json.JSONException

private const val now_playing_api = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
private const val popular_shows_api = "https://api.themoviedb.org/3/tv/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1"
class MainActivity : AppCompatActivity() {
    // creates mutable list of movies

    var MoviesList: MutableList<Movie> = ArrayList()
    var ShowsList: MutableList<Show> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // assigns views to variables and set up recyclerview
        val Rv = findViewById<RecyclerView>(R.id.Rv)


//        var itemsAdapter = MovieRecyclerViewAdapter(MoviesList)
        var itemsAdapter = ShowRecyclerViewAdapter(ShowsList, this)

        Rv.adapter = itemsAdapter
        Rv.layoutManager = LinearLayoutManager(this)

        var progressBar = findViewById<ContentLoadingProgressBar>(R.id.progress)

        // sets up client and gson
        val client = AsyncHttpClient()
        var gson = Gson()


        // attempts to retrieve api data
        client.get(popular_shows_api, object: JsonHttpResponseHandler(){
            // if fails
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("MainActivity", "On Failure $statusCode")
            }

            // if succeeds
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.e("MainActivity", "On Failure $json")
                try {
                    // shows progress bar and adds each movie from api into list
                    progressBar.show()
//                    val movieJsonArray = json.jsonObject.getJSONArray("results")
//                    for (i in 0 until movieJsonArray.length()) {
//                        var json_obj = movieJsonArray.getJSONObject(i)
//                        var new_show = Movie()
//                        new_show.title = json_obj.getString("original_title")
//                        new_show.description = json_obj.getString("overview")
//                        new_show.movieImageUrl = json_obj.getString("poster_path")
//                        MoviesList.add(new_show)
                    val showJsonArray = json.jsonObject.getJSONArray("results")
                    for (i in 0 until showJsonArray.length()) {
                        var json_obj = showJsonArray.getJSONObject(i)
                        var new_show = Show()
                        new_show.title = json_obj.getString("name")
                        new_show.description = json_obj.getString("overview")
                        new_show.showImageUrl = json_obj.getString("poster_path")
                        new_show.firstAirDate = json_obj.getString("first_air_date")
                        new_show.voteAverage = json_obj.getString("vote_average")
                        ShowsList.add(new_show)
                    }

                }
                // if theres an error, throw error message and hide progress bar
                catch (e: JSONException){
                    Log.e("MainActivity", "Encountered exception $e")
                    progressBar.hide()
                }

                // when done, updates recycleview adapter and hides progress bar to show movies
                itemsAdapter.notifyDataSetChanged()
                progressBar.hide()

            }
        })


    }

}
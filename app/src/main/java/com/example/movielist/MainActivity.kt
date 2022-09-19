package com.example.movielist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.movielist.R.id
import okhttp3.Headers
import org.json.JSONObject
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException

private const val now_playing_api = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
class MainActivity : AppCompatActivity() {
    // creates mutable list of movies

    var MoviesList: MutableList<Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // assigns views to variables and set up recyclerview
        val MovieRv = findViewById<RecyclerView>(R.id.moviesRv)

        var itemsAdapter = MovieRecyclerViewAdapter(MoviesList)

        MovieRv.adapter = itemsAdapter
        MovieRv.layoutManager = LinearLayoutManager(this)

        var progressBar = findViewById<ContentLoadingProgressBar>(R.id.progress)

        // sets up client and gson
        val client = AsyncHttpClient()
        var gson = Gson()

        // attempts to retrieve api data
        client.get(now_playing_api, object: JsonHttpResponseHandler(){
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
                    val movieJsonArray = json.jsonObject.getJSONArray("results")
                    for (i in 0 until movieJsonArray.length()) {
                        var json_obj = movieJsonArray.getJSONObject(i)
                        var new_movie = Movie()
                        new_movie.title = json_obj.getString("original_title")
                        new_movie.description = json_obj.getString("overview")
                        new_movie.movieImageUrl = json_obj.getString("poster_path")
                        MoviesList.add(new_movie)
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
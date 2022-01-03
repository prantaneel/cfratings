package com.example.cf_ratings

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
//import com.android.volley.RequestQueue
//import com.android.volley.Response
//import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.content.ContextCompat
import android.view.View




class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        var ss = intent.getStringExtra("message_key").toString()
        val isEmpty = ss.isNullOrEmpty()
        if(isEmpty) ss = "gorefrog"
        val ratings = findViewById<TextView>(R.id.cfRatings)
        val handle = findViewById<TextView>(R.id.handleName)
        //change bg
        val view = this.window.decorView
        view.setBackgroundColor(Color.parseColor("#000000"))
        //------
        handle.text = ss
        fetchJson(ss, ratings, view)
    }
    @SuppressLint("SetTextI18n")
    fun fetchJson(handle: String, ratings: TextView, view: View){
        println("yay")
        val url = "https://codeforces.com/api/user.rating?handle=${handle}"
        val request = okhttp3.Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val bd = response.body?.string()
                println(bd)
                val gson = GsonBuilder().create()
                val homefeed = gson.fromJson(bd, HomeFeed::class.java)
                val status1 = homefeed.status
                println(status1)
                if(status1=="FAILED"){
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    var lastRating = 0
                    lastRating = homefeed.result[homefeed.result.size-1].newRating
                    var color: String
                    runOnUiThread{
                        ratings.text = lastRating.toString()
                        color = changeTheme(lastRating)
                        view.setBackgroundColor(Color.parseColor(color))
                    }
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                //do nothing
            }
        })
    }
    fun changeTheme(rt: Int) : String{
        if(rt<=1199) return "#808080";
        else if(rt<=1399) return "#008000";
        else if(rt<=1599) return "#03A89E"
        else if(rt<=1899) return "#0000FF"
        else if(rt<=2199) return "#AA00AA"
        else if(rt<=2299) return "#FF8C00"
        else if(rt<=2399) return "#FF8C00"
        else if(rt<=1599) return "#FF0000"
        else if(rt<=2899) return "#FF0000"
        else return "#FF0000"

    }
}
class HomeFeed(val status: String, val result: List<Result>)

class Result(val newRating: Int)
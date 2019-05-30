package com.example.task

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity()
{


    private val games = arrayOf("505230","489940","944010","779340")
    private var url : String = "https://store.steampowered.com/api/appdetails/?appids="
    private val data : String? = null
    private var requestQueue : RequestQueue? = null
    private val tag : String = "MainActivity"
    val list = ArrayList<list_games>()
    var i = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)



        list.add(list_games(R.drawable.ic_launcher_foreground,"505230"))
        list.add(list_games(R.drawable.ic_launcher_foreground,"489940"))
        list.add(list_games(R.drawable.ic_launcher_foreground,"944010"))
        list.add(list_games(R.drawable.ic_launcher_foreground,"779340"))

        var adapater = CustomAdapter(list,this)
        recyclerView.adapter = adapater


    }

}

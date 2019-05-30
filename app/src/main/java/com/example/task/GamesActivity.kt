package com.example.task

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class GamesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        var text = findViewById(R.id.game) as TextView

        var s : String = intent.getStringExtra("ID")

        var requestQueue : RequestQueue = Volley.newRequestQueue(this)
        var url = "https://store.steampowered.com/api/appdetails/?appids="

        var stringRequest = StringRequest(
            Request.Method.GET,url+s, Response.Listener<String>{
                    response ->
                var jsonObject = JSONObject(response)
                var dull : String = jsonObject.getString(s)
                var jsonObject1 = JSONObject(dull)
                val data = JSONObject(jsonObject1.getString("data"))

                var array  = JSONArray(data.getString("movies"))

                var temp = "\n\nName : "+data.getString("name")+"\n\nDescription : "+data.getString("detailed_description")+"\n\n"
                text.text = temp

            },
            Response.ErrorListener { Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show() })
        requestQueue!!.add(stringRequest)

    }
}

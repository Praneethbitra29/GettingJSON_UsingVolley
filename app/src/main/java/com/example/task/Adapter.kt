package com.example.task

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_game.view.*
import org.json.JSONArray
import org.json.JSONObject

class CustomAdapter(val list : ArrayList<list_games>,val context : Context) : RecyclerView.Adapter<ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewTypr: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_game,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        var requestQueue : RequestQueue = Volley.newRequestQueue(context)
        var url = "https://store.steampowered.com/api/appdetails/?appids="

        var stringRequest = StringRequest(
            Request.Method.GET,url+list.get(position).name, Response.Listener<String>{
                    response ->
                    var jsonObject = JSONObject(response)
                    var dull : String = jsonObject.getString(list.get(position).name)
                    var jsonObject1 = JSONObject(dull)
                    val data = JSONObject(jsonObject1.getString("data"))


                    val array = data.getJSONArray("screenshots")

                    val temp = array.getJSONObject(0)

                    Picasso.get().load(temp.getString("path_thumbnail")).into(viewHolder.game_image)

                    //Toast.makeText(context,temp.getString("path_thumbnail"),Toast.LENGTH_SHORT).show()

                //viewHolder.game_image.setImageResource(R.drawable.ic_launcher_foreground)
                viewHolder.game_text.text = data.getString("name")

            },
            Response.ErrorListener { Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show() })
        requestQueue!!.add(stringRequest)

        viewHolder.itemView.setOnClickListener{
                val intent = Intent(context,GamesActivity::class.java)
                intent.putExtra("ID",list.get(position).name)
                startActivity(context,intent,null)
        }

    }


}

class ViewHolder(view : View ):RecyclerView.ViewHolder(view){

    val game_image = view.imageView
    val game_text = view.textView
}
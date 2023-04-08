package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var pokeList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getMainView = findViewById<RecyclerView>(R.id.poke_List)
        getImg()
    }

    private fun getImg() {
        val client = AsyncHttpClient()

        client ["https://pokeapi.co/api/v2/pokemon?offset=20&limit=20", object: JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.d("Success", "Image loaded")
                val getPokeImg = (json?.jsonObject?.getJSONArray("results"))
                if (getPokeImg != null) {
                    for (i in 0 until getPokeImg.length()){
                        pokeList.add(getPokeImg.getString(i))
                    }
                    val adapter = PokeAdapter(pokeList)
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.d("Fail", "Image not loaded")
            }



        }]
    }
}
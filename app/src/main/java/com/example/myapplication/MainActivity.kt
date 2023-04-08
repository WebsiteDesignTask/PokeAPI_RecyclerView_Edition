package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var pokeList: MutableList<Pokemon>
    private lateinit var adapter: PokeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokeList = mutableListOf()
        adapter = PokeAdapter(pokeList)
        val recyclerView = findViewById<RecyclerView>(R.id.poke_List)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        getImg()
    }

    private fun getImg() {
        val client = AsyncHttpClient()

        client ["https://pokeapi.co/api/v2/pokemon?offset=20&limit=20", object: JsonHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON?) {
                Log.d("Success", "Image loaded")
                val pokeData = json?.jsonObject?.getJSONArray("results")

                if (pokeData != null) {
                    for (i in 0 until pokeData.length()) {
                        val jsonObject = pokeData.getJSONObject(i)
                        val pokemonName = jsonObject.getString("name")
                        val pokemonUrl = jsonObject.getString("url")

                        // Fetch additional data for the Pokemon
                        client.get(pokemonUrl, object : JsonHttpResponseHandler() {
                            override fun onFailure(
                                statusCode: Int,
                                headers: Headers?,
                                response: String?,
                                throwable: Throwable?
                            ) {
                                TODO("Not yet implemented")
                            }

                            override fun onSuccess(
                                statusCode: Int,
                                headers: Headers?,
                                json: JSON?
                            ) {
                                val pokemonData = json?.jsonObject
                                val pokemonType =
                                    pokemonData?.getJSONArray("types")?.getJSONObject(0)
                                        ?.getJSONObject("type")?.getString("name")
                                val pokemonHeight = pokemonData?.getInt("height").toString()
                                val pokemonImageUrl = pokemonData?.getJSONObject("sprites")
                                    ?.getString("front_default")

                                pokeList.add(Pokemon(pokemonName, pokemonType, pokemonHeight, pokemonImageUrl))
                                adapter.notifyDataSetChanged()
                            }
                        })
                    }
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
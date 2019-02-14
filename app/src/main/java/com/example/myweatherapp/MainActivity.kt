package com.example.myweatherapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.myweatherapp.models.Forecast
import com.example.myweatherapp.network.WeatherAPI
import com.google.gson.JsonObject

import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var weatherApi : WeatherAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        WeatherApp.getAppComponent().inject(this)

        weatherApi.loadForecasts("casablanca",WeatherAPI.API_KEY).enqueue(object  :Callback<Forecast>{
            override fun onFailure(call: Call<Forecast>, t: Throwable) {
                Log.e("MyTag","Call Failed "+t.message)
            }

            override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                Log.e("MyTag", response.body()?.name)
                Log.e("MyTag", response.body()?.weather?.first()?.description)
            }

        })
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

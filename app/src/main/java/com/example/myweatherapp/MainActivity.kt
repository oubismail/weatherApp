package com.example.myweatherapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.myweatherapp.adapters.ForcastesListAdapter
import com.example.myweatherapp.models.Forecast
import com.example.myweatherapp.models.ForecastList
import com.example.myweatherapp.network.WeatherAPI
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ForcastesListAdapter.OnCityClickListener {

    @Inject
    lateinit var weatherApi : WeatherAPI
    private val myAdapter = ForcastesListAdapter(mutableListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        WeatherApp.getAppComponent().inject(this)
        forecastRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter  = myAdapter
        }

        loadForecasts()

        refreshLayout.setOnRefreshListener {
            loadForecasts()
        }
    }

    private fun loadForecasts() {
        progressLoading.visibility = View.VISIBLE
        forecastRecyclerView.visibility = View.INVISIBLE
        weatherApi.loadForecastsList("2553604,2538474,6547285,2530335,2548885", "metric", WeatherAPI.API_KEY)
            .enqueue(object : Callback<ForecastList> {
                override fun onFailure(call: Call<ForecastList>, t: Throwable) {
                    refreshLayout.isRefreshing = false
                    progressLoading.visibility = View.INVISIBLE
                    forecastRecyclerView.visibility = View.VISIBLE
                    Toast.makeText(this@MainActivity, "Error while loading forecsts", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ForecastList>, response: Response<ForecastList>) {
                    refreshLayout.isRefreshing = false
                    myAdapter.setData(response.body()?.list ?: emptyList())
                    progressLoading.visibility = View.INVISIBLE
                    forecastRecyclerView.visibility = View.VISIBLE
                }
            })
    }

    override fun onCityClick(forecast: Forecast) {
        val data = Gson().toJson(forecast)
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.CITY_FORECAST, data)
        }
        startActivity(intent)
    }

    /* weatherApi.loadForecasts("casablanca",WeatherAPI.API_KEY).enqueue(object  :Callback<Forecast>{
         override fun onFailure(call: Call<Forecast>, t: Throwable) {
             Log.e("MyTag","Call Failed "+t.message)
         }

         override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
             myAdapter.setData(response.body())
             Log.e("MyTag", response.body()?.name)
             Log.e("MyTag", response.body()?.weather?.first()?.description)
         }

     })*/

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

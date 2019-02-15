package com.example.myweatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.myweatherapp.adapters.ForcastesListAdapter
import com.example.myweatherapp.models.Forecast
import com.example.myweatherapp.models.ForecastList
import com.example.myweatherapp.network.WeatherAPI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private  lateinit var locationManager : LocationManager
    private val PERMISSIONS_REQUEST_LOCATION: Int = 12354


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        WeatherApp.getAppComponent().inject(this)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

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
        launchDetailsActivity(forecast)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_location -> {
                checkLocationPermission()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun checkLocationPermission() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            showGPSDialog()
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION)
            } else {
                getLocation()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocation()
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
            location?.let {
                getCityByLocation(location)
            }
        }
    }

    private fun getCityByLocation(location: Location) {
            weatherApi.loadForecastByLocation(location.latitude, location.longitude, "metric", WeatherAPI.API_KEY)
                .enqueue(object : Callback<Forecast>{
                    override fun onFailure(call: Call<Forecast>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error Loading Location Forecast", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
                        response.body()?.let {
                            launchDetailsActivity(it)
                        }
                    }

                })
    }

    private fun launchDetailsActivity(forecast: Forecast) {
        val data = Gson().toJson(forecast)
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.CITY_FORECAST, data)
        }
        startActivity(intent)
    }

    private fun showGPSDialog() {
        AlertDialog.Builder(this).setMessage("Please Enable device GPS")
            .setPositiveButton("OK", null)
            .show()
    }
}

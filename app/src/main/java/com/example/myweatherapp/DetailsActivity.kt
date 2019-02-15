package com.example.myweatherapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.myweatherapp.models.Forecast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val CITY_FORECAST = "city_forecast"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = ""
        if (intent.extras != null && intent.hasExtra(CITY_FORECAST)){
            val forecast = Gson().fromJson(intent.extras!!.get(CITY_FORECAST).toString(), Forecast::class.java)
            forecast?.let {
                title = it.name
                cityName.text = it.name
                cityState.text = it.weather?.first()?.description
                cityTemp.text = it.main?.temp.toString()
                cityHumidity.text = it.main?.humidity.toString()
                cityWindSpeed.text =  it.wind?.speed.toString()
                cityPressure.text = it.main?.pressure.toString()
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem?): Boolean {
        when (menuItem?.itemId) {
            android.R.id.home -> {
                super.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }
}

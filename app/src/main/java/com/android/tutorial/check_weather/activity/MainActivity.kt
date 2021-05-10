package com.android.tutorial.check_weather.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import com.android.tutorial.check_weather.R
import com.android.tutorial.check_weather.model.WeatherResponse
import com.android.tutorial.check_weather.viewmodel.MainViewModel
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var weatherResponse: WeatherResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        btnRefresh.setOnClickListener {
            mainViewModel.loadData(tvCity.text.toString())
            setObserver()
        }
        mainViewModel.loadData("yangon")
        setObserver()

        btnPickLocation.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivityForResult(intent, 22222)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 22222 && data != null) {
            val latitude = data.getDoubleExtra("Lat", 16.7986)
            val longitude = data.getDoubleExtra("Lon", 96.1495)
            Log.d("dataChanged", "main > Lat > $latitude ...... Long > $longitude")
            mainViewModel.loadDataByLatLon(latitude.toString(), longitude.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setObserver() {
        mainViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }
        )

        mainViewModel.weatherData.observe(this, Observer { response ->
            weatherResponse = response
//            val firstWeather = weatherData.weatherList.firstOrNull { weather ->
//                weather.description == "cloudy"
//            }
            val weather = response.weatherList.firstOrNull()
            Glide.with(this)
                .load((response.weatherList.first().icon.url))
                .into(ivWeather)
            tvCity.text = weatherResponse?.name
            tvDescription.text = weather?.description
            tvWeatherSign.text = weather?.main
            val main = weatherResponse?.main
//            (32°F − 32) × 5/9
            tvTemperatureValue.text = convertCelsius(main?.temp).toString() + " °C"
            tvFeelsLikeValue.text = convertCelsius(main?.feels_like).toString() + " °C"
            tvMinValue.text = convertCelsius(main?.temp_min).toString() + " °C"
            tvMaxValue.text = convertCelsius(main?.temp_max).toString() + " °C"
            tvPressureValue.text = main?.pressure.toString() + " hPa"
            tvHumidityValue.text = main?.humidity.toString()
        })

        mainViewModel.errorText.observe(this, Observer { error ->
            Snackbar.make(tvDescription, error?: "", Snackbar.LENGTH_LONG)
        })
    }

    private fun convertCelsius(fahrenheit: Double?): Double {
        if (fahrenheit != null) {
//            var degree = (fahrenheit - 32) * 5 / 9 >>>> fahrenheit to celsius
            val degree = fahrenheit.minus(273.15) // kelvin to celsius
            return ceil(degree)
        }
        return 0.0
    }

    private fun showLoading() {
        Log.d("dataChanged", "show loading......")
    }

    private fun hideLoading() {
        Log.d("dataChanged", "hide loading......")
    }

    private fun showToast(message: MutableLiveData<String>) {
        Toast.makeText(this, message.value, Toast.LENGTH_LONG).show()
    }

}
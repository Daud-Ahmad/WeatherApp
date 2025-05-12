package com.codesaaz.mvvm.view

import android.os.Bundle
import android.view.View.GONE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codesaaz.mvvm.R
import com.codesaaz.mvvm.viewmodel.WeatherViewmodel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.weather_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cityInput = findViewById<EditText>(R.id.etCity)
        val btnFetch = findViewById<Button>(R.id.btnSearch)
        val tvLocation = findViewById<TextView>(R.id.tvLocation)
        val tvTemp = findViewById<TextView>(R.id.tvTemp)
        val tvFeelsLike = findViewById<TextView>(R.id.tvFeelsLike)

        btnFetch.setOnClickListener {
            val city = cityInput.text.toString()
            if (city.isNotEmpty()) {
                viewModel.fetchWeather(city)
            }
        }

        viewModel.weatherData.observe(
            this
        ) {
            it?.let {
                val location =
                    "Location: ${it.location.name}, ${it.location.region}, ${it.location.country}"
                val tem = "Temperature: ${it.current.temp_c} °C"
                val feelLike = "Feels Like: ${it.current.feelslike_c}  °C"
                tvLocation.text = location
                tvTemp.text = tem
                tvFeelsLike.text = feelLike
            }
        }

        viewModel.error.observe(
            this
        ) {
            tvLocation.text = it
            tvTemp.visibility = GONE
            tvFeelsLike.visibility = GONE
        }
    }
}
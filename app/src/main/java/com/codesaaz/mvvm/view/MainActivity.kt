package com.codesaaz.mvvm.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val cityInput = findViewById<EditText>(R.id.etCity)
        val btnFetch = findViewById<Button>(R.id.btnFetch)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnFetch.setOnClickListener {
            val city = cityInput.text.toString()
            if (city.isNotEmpty()) {
                viewModel.fetchWeather(city)
            }
        }

        viewModel.weatherData.observe(
            this,{
                it?.let {
                    tvResult.text = "City: ${it.location.name}\nTemperature: ${it.current.temp_c}°C"
                }
            }
        )

        viewModel.error.observe(
            this, {
                tvResult.text = it
            }
        )
    }
}
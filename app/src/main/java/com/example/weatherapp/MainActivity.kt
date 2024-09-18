package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Weather App")
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainPage(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        weatherViewModel
                    )
                }
            }
        }
    }

}
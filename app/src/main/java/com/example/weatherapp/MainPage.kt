package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.api.NetworkResponse
import com.example.weatherapp.api.WeatherModel
import com.example.weatherapp.ui.theme.Oswald

@Composable
fun MainPage(modifier: Modifier = Modifier, viewModel: WeatherViewModel) {

    var location by remember {
        mutableStateOf("")
    }

    val weatherResult = viewModel.weatherResult.observeAsState()

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = location,
                onValueChange = { newLocation ->
                    location = newLocation
                },
                label = {
                    Text("Enter the location")
                }
            )
            IconButton(
                onClick = {
                    viewModel.getData(location)
                }
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search Button"
                )
            }

        }
        when(val result = weatherResult.value) {
            is NetworkResponse.Error -> {
                Text(result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                WeatherInfo(data = result.data)
            }
            null -> {

            }
        }
    }
    
}

@Composable
fun WeatherInfo(modifier: Modifier = Modifier, data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location Icon",
                modifier = Modifier.size(40.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = data.location.name,
                    fontSize = 24.sp,
                    fontFamily = Oswald,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = data.location.country,
                    fontSize = 18.sp,
                    color = Color.LightGray,
                    fontFamily = Oswald
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Temperature: ${data.current.temp_c} °C",
            fontSize = 20.sp,
            fontFamily = Oswald
        )

        AsyncImage(
            modifier = Modifier.size(250.dp),
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "Weather Condition Icon"
        )

        Text(
            text = data.current.condition.text,
            fontSize = 15.sp,
            fontFamily = Oswald
        )
    }
}

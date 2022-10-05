package me.chronick.weatherapp.adapters

data class WeatherModel(
    val cityName: String,
    val dataTime: String,
    val condition: String,
    val imageURL: String,
    val currentTemper: String,
    val temperMin: String,
    val temperMax: String,
    val hoursToDay: String
)

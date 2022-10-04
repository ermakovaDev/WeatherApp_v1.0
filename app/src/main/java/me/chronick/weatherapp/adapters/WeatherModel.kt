package me.chronick.weatherapp.adapters

data class WeatherModel(
    val cityName: String,
    val dataTime: String,
    val condition: String,
    val imageURL: String,
    val currentTemper: String,
    val temperMax: String,
    val temperMin: String,
    val hoursToDay: String,
    val imageUrl: String
)

package me.chronick.weatherapp

data class DayItem(
    val cityName: String,
    val dataTime: String,
    val condition: String,
    val imageURL: String,
    val currentTemper: String,
    val temperMax: String,
    val temperMin: String,
    val hoursToDay: String
    )
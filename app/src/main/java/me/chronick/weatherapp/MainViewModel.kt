package me.chronick.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.chronick.weatherapp.adapters.WeatherModel
import java.nio.file.WatchEvent

class MainViewModel: ViewModel() {

    val liveDataCurrent = MutableLiveData<WeatherModel>()  // transfer parseCurrentData
    val liveDataList = MutableLiveData<List<WeatherModel>>() // transfer     val listDays = parseDaysWeatherData(myObject)

}
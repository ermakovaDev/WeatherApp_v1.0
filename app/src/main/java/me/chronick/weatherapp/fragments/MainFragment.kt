package me.chronick.weatherapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import me.chronick.weatherapp.MainViewModel
import me.chronick.weatherapp.adapters.ViewPageAdapter
import me.chronick.weatherapp.adapters.WeatherModel
import me.chronick.weatherapp.databinding.FragmentMainBinding
import org.json.JSONObject
import java.time.format.DateTimeFormatter


const val API_WEATHER_KEY = "a9194f5b279d4301b5c93017220706"

class MainFragment : Fragment() {
    private lateinit var fLocationClient: FusedLocationProviderClient
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding
    private val fragmentList = listOf(
        HoursFragment.newInstance(),
        DaysFragment.newInstance()
    )
    private val tabList = listOf(
        "Hours",
        "Days"
    )

    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //validate permission
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        initFragment()
        updateHeaderCurrentCard()
        getLocation()
    }

    private fun initFragment() = with(binding) { // directly from the markup
        fLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val adapter = ViewPageAdapter(activity as FragmentActivity, fragmentList)
        vp2Footer.adapter = adapter
        TabLayoutMediator(tablayoutBody, vp2Footer) { tab, position ->
            tab.text = tabList[position]
        }.attach()

        ibCardHeaderUpdateIcon.setOnClickListener{
            tablayoutBody.selectTab(tablayoutBody.getTabAt(0))
            getLocation()
        }
    }

    private fun getLocation() {
        val cancelToken = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } //SIC! https://stackoverflow.com/questions/66489605/is-constructor-locationrequest-deprecated-in-google-maps-v2
        fLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancelToken.token).addOnCompleteListener {
            requestWeatherData("${it.result.latitude},${it.result.longitude}")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateHeaderCurrentCard() = with(binding) { // add Observer, this is callback
        model.liveDataCurrent.observe(viewLifecycleOwner) { // == it ||  item->
            val minMaxTemper = "${it.temperMin}ºC / ${it.temperMax}ºC"
            tvCardHeadeData.text = it.dataTime
            tvCardHeaderCity.text = it.cityName
            tvCardHeaderCurrentTemper.text = it.currentTemper + "ºC"

            if (it.currentTemper != "") {
                tvCardHeaderCurrentTemper.text = it.currentTemper + "ºC"
                tvCardHeaderTemperMinMax.text = minMaxTemper
            } else {
                tvCardHeaderCurrentTemper.text = minMaxTemper
                tvCardHeaderTemperMinMax.text = "-"
            }

            tvCardHeaderCondition.text = it.condition
            Picasso.get().load("https:" + it.imageURL).into(ivCardHeaderPicture)
        }
    }

    private fun permissionListener() { // initialize Launcher. gave or did not give permission
        Log.d("MyLog","permissionListener")
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
            }
    }

    private fun checkPermission() { // checking permission
        Log.d("MyLog","checkP")
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) //requesting permission
        }
    }

    private fun requestWeatherData(cityName: String) {
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$API_WEATHER_KEY&q=$cityName&days=3&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url, { result ->
            parseWeatherData(result)
            Log.d("MyLog", "Result: $result")
        }, { error ->
            Log.d("MyLog", "Error: $error")
        })
        queue.add(request)
    }

    private fun parseWeatherData(jsonWithAPIKey: String) {
        val myObject = JSONObject(jsonWithAPIKey)
        val listDays = parseDaysWeatherData(myObject)
        parseCurrentData(myObject, listDays[0]) // first item in listDays is today
    }

    private fun getDataTimeFormat(item: String): String {
        val formatter = DateTimeFormatter.ofPattern("kk:mm")
        return item.format(formatter)
    }

    private fun parseDaysWeatherData(myObject: JSONObject): List<WeatherModel> {
        val listItemDay = ArrayList<WeatherModel>()
        val daysArray = myObject.getJSONObject("forecast").getJSONArray("forecastday")
        val nameCity = myObject.getJSONObject("location").getString("name")
        for (i in 0 until daysArray.length()) {
            val oneDay = daysArray[i] as JSONObject
            val item = WeatherModel(
                nameCity,
                oneDay.getString("date"),
                oneDay.getJSONObject("day").getJSONObject("condition").getString("text"),
                oneDay.getJSONObject("day").getJSONObject("condition").getString("icon"),
                "",
                oneDay.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString(),
                oneDay.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString(),
                oneDay.getJSONArray("hour").toString()
            )
            listItemDay.add(item)
        }
        model.liveDataList.value = listItemDay
        return listItemDay
    }

    private fun parseCurrentData(myObject: JSONObject, weatherItem: WeatherModel) { // Header Card
        val item = WeatherModel(
            myObject.getJSONObject("location").getString("name"),
            myObject.getJSONObject("current").getString("last_updated"),
            myObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            myObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            myObject.getJSONObject("current").getString("temp_c").toFloat().toInt().toString(),
            weatherItem.temperMin,
            weatherItem.temperMax,
            weatherItem.hoursToDay
        )
        model.liveDataCurrent.value = item
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
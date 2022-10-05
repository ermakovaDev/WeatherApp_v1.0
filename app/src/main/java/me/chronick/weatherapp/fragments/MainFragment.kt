package me.chronick.weatherapp.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import me.chronick.weatherapp.adapters.ViewPageAdapter
import me.chronick.weatherapp.adapters.WeatherModel
import me.chronick.weatherapp.databinding.FragmentMainBinding
import org.json.JSONObject

const val API_WEATHER_KEY = "a9194f5b279d4301b5c93017220706"

class MainFragment : Fragment() {

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
        requestWeatherData("Canberra")
    }

    private fun initFragment() = with(binding) { // directly from the markup
        val adapter = ViewPageAdapter(activity as FragmentActivity, fragmentList)
        vp2Footer.adapter = adapter
        TabLayoutMediator(tablayoutBody, vp2Footer) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun permissionListener() { // initialize Launcher. gave or did not give permission
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
            }
    }

    private fun checkPermission() { // checking permission
        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            permissionListener()
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) //requesting permission
        }
    }

    private fun requestWeatherData(cityName: String) {
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$API_WEATHER_KEY&q=$cityName&days=3&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(Request.Method.GET, url, { result -> parseWeatherData(result)
            Log.d("MyLog", "Result: $result")
        }, { error ->
            Log.d("MyLog", "Error: $error")
        })
        queue.add(request)
    }

    private fun parseWeatherData(jsonWithAPIKey: String) {
        val myObject = JSONObject(jsonWithAPIKey)
        val item = WeatherModel(
            myObject.getJSONObject("location").getString("name"),
            myObject.getJSONObject("current").getString("last_updated"),
            myObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            myObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            myObject.getJSONObject("current").getString("temp_c"),
            "",
            "",
            ""
        )

        Log.d("MyLog", "City: ${item.cityName}")
        Log.d("MyLog", "Time: ${item.dataTime}")
        Log.d("MyLog", "Condition: ${item.condition}")
        Log.d("MyLog", "Temp: ${item.currentTemper}")
        Log.d("MyLog", "Image URL: ${item.imageURL}")
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
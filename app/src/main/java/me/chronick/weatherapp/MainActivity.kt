package me.chronick.weatherapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import me.chronick.weatherapp.databinding.ActivityMainBinding
import org.json.JSONObject

const val API_WEATHER_KEY = "a9194f5b279d4301b5c93017220706"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnResult.setOnClickListener {
            val text = binding.etCity.text.toString()
            getResult(text)
        }
    }

    private fun getResult(name: String) {
        val url = "https://api.weatherapi.com/v1/current.json?key=$API_WEATHER_KEY&q=$name&aqi=no"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val obj = JSONObject(response)
                val temp = obj.getJSONObject("current")
                Log.d("MyLog", "Response: $response")
            Log.d("MyLog", "Response: ${temp.getString("temp_c")}") },
            { Log.d("MyLog", "Volley error: $it") })
        queue.add(stringRequest)
    }
}
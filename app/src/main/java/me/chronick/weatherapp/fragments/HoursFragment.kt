package me.chronick.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import me.chronick.weatherapp.MainViewModel
import me.chronick.weatherapp.R
import me.chronick.weatherapp.adapters.WeatherAdapter
import me.chronick.weatherapp.adapters.WeatherModel
import me.chronick.weatherapp.databinding.FragmentHoursBinding
import me.chronick.weatherapp.databinding.FragmentMainBinding
import org.json.JSONArray
import org.json.JSONObject

class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcViewItem()
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            adapter.submitList(getHoursList(it))
        }
    }

    private fun initRcViewItem() = with(binding) {
        rcViewItem.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcViewItem.adapter = adapter
    }

    private fun getHoursList(weatherItem: WeatherModel): List<WeatherModel> {
        val hoursArray = JSONArray(weatherItem.hoursToDay)  // parse from JSON_Str to hoursArray
        val hoursList = ArrayList<WeatherModel>() // parse to list 24Item
        for (i in 0 until hoursArray.length()) {
            val item = WeatherModel(
                "",
                (hoursArray[i] as JSONObject).getString("time"),
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text"),
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                "",
                "",
                ""
            )
            hoursList.add(item)
        }
        return hoursList
    }

    companion object {
        @JvmStatic
        fun newInstance() = HoursFragment()
    }
}
package me.chronick.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import me.chronick.weatherapp.R
import me.chronick.weatherapp.adapters.WeatherAdapter
import me.chronick.weatherapp.adapters.WeatherModel
import me.chronick.weatherapp.databinding.FragmentHoursBinding
import me.chronick.weatherapp.databinding.FragmentMainBinding

class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHoursBinding
    private lateinit var adapter: WeatherAdapter

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
    }

    private fun initRcViewItem() = with(binding){
        rcViewItem.layoutManager = LinearLayoutManager(activity)
        adapter = WeatherAdapter()
        rcViewItem.adapter = adapter

        val listTest = listOf(
            WeatherModel("", "12:00", "Sunny", "", "32C", "", "", "", ""),
                    WeatherModel("", "15:00", "Rain", "", "20C", "", "", "", ""),
        WeatherModel("", "10:00", "Snow", "", "21C", "", "", "", "")
        )
        adapter.submitList(listTest)
    }

    companion object {
        @JvmStatic
        fun newInstance()= HoursFragment()
    }
}
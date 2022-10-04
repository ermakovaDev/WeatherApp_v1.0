package me.chronick.weatherapp.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.chronick.weatherapp.databinding.ListItemBinding

class WeatherAdapter: ListAdapter<WeatherModel, WeatherAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val binding = ListItemBinding.bind(view)
        fun bind(item: WeatherModel) = with(binding){
            tvDateTime.text = item.dataTime
            tvCondition.text = item.condition
            tvTemper.text = item.currentTemper
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        TODO("Not yet implemented")
    }
}
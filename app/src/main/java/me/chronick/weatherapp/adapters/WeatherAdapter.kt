package me.chronick.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.chronick.weatherapp.R
import me.chronick.weatherapp.databinding.ListItemBinding

class WeatherAdapter: ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){ // creating pattern markup
        private val binding = ListItemBinding.bind(view)
        fun bind(item: WeatherModel) = with(binding){
            tvDateTime.text = item.dataTime
            tvCondition.text = item.condition
            tvTemper.text = item.currentTemper
        }
    }

    class Comparator: DiffUtil.ItemCallback<WeatherModel>(){
        override fun areItemsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WeatherModel, newItem: WeatherModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder { // the number of views is created by the number of items in the list
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false) // parent:ViewGroup contains context
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) { // how to fill Holder (stores all references to positions) in and position
        holder.bind(getItem(position))
    }
}
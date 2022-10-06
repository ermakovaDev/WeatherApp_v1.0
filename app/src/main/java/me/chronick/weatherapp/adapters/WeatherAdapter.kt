package me.chronick.weatherapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.chronick.weatherapp.R
import me.chronick.weatherapp.databinding.ListItemBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class WeatherAdapter: ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view){ // creating pattern markup
        private val binding = ListItemBinding.bind(view)
        @SuppressLint("SetTextI18n")
        fun bind(item: WeatherModel) = with(binding){
            val current = item.dataTime
            val formatter = DateTimeFormatter.ofPattern("kk:mm dd/MM/yy", Locale.getDefault())
            val formatted = current.format(formatter)

            tvDateTime.text = formatted
            tvCondition.text = item.condition
            tvTemper.text = item.currentTemper+"ºC"
            Picasso.get().load("https:"+item.imageURL).into(ivConditionPucture)
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
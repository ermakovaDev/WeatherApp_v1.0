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

class WeatherAdapter(private val listener: Listener?): ListAdapter<WeatherModel, WeatherAdapter.Holder>(Comparator()) {

    class Holder(view: View, private val listener: Listener?) : RecyclerView.ViewHolder(view){ // creating pattern markup

        private val binding = ListItemBinding.bind(view)
        private var itemTemp:WeatherModel? = null
        init {
            itemView.setOnClickListener { // all the markup of the dayItem in the body
                itemTemp?.let { it1 -> listener?.onClick(it1) }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: WeatherModel) = with(binding){
            itemTemp = item

            tvDateTime.text = item.dataTime
            tvCondition.text = item.condition
            if (item.currentTemper!=""){
                tvTemper.text = item.currentTemper+"ºC"
            } else { tvTemper.text = "${item.temperMin}ºC / ${item.temperMax}ºC" }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.Holder { // the number of views is created by the number of items in the list
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false) // parent:ViewGroup contains context
        return WeatherAdapter.Holder(view, listener)
    }

    override fun onBindViewHolder(holder: WeatherAdapter.Holder, position: Int) { // how to fill Holder (stores all references to positions) in and position
        holder.bind(getItem(position))

    }

    interface Listener{ // interface for switching header day cards
        fun onClick(item: WeatherModel)
    }
}
package com.raaziat.pagingexample.ui.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raaziat.accuweathersample.model.weather.DailyForecast
import com.raaziat.pagingexample.R
import com.raaziat.pagingexample.databinding.ItemWeatherBinding

class WeatherAdapter : ListAdapter<DailyForecast,WeatherAdapter.WeatherViewHolder>(DiffCallback()){

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemWeatherBinding = DataBindingUtil.inflate<ItemWeatherBinding>(
            layoutInflater,
            R.layout.item_weather,
            viewGroup,
            false
        )
        return WeatherViewHolder(itemWeatherBinding,listener)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.itemWeatherBinding.txtViewDayTemperature.text = getItem(position).Temperature.Minimum.Value.toString()
        holder.itemWeatherBinding.txtViewNightTemperature.text = getItem(position).Temperature.Maximum.Value.toString()
        holder.itemWeatherBinding.txtViewDay.text = getItem(position).Date
    }


    class WeatherViewHolder(var itemWeatherBinding: ItemWeatherBinding,listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemWeatherBinding.root){
        init {
            itemWeatherBinding.root.setOnClickListener {
                val position = adapterPosition
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DailyForecast>() {
        override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem.EpochDate == newItem.EpochDate
        }

        override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Int)
    }

    fun getItemAt(position: Int):DailyForecast = getItem(position)

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}
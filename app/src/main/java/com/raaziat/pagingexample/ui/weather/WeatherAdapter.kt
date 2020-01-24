package com.raaziat.pagingexample.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raaziat.accuweathersample.model.weather.DailyForecast
import com.raaziat.pagingexample.R
import com.raaziat.pagingexample.databinding.ItemWeatherBinding
import com.raaziat.pagingexample.model.openweather.X
import com.raaziat.pagingexample.utils.Constants
import com.raaziat.pagingexample.utils.formatDay
import com.raaziat.pagingexample.utils.getCelsius
import com.squareup.picasso.Picasso

class WeatherAdapter : ListAdapter<X,WeatherAdapter.WeatherViewHolder>(DiffCallback()){

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
        if(position == 0) {
            holder.itemWeatherBinding.imgViewWeatherImage.visibility = View.GONE
            holder.itemWeatherBinding.txtViewDayTemperature.visibility = View.GONE
            holder.itemWeatherBinding.txtViewDay.visibility = View.GONE
            holder.itemWeatherBinding.txtViewNightTemperature.visibility = View.GONE
        }
        Picasso.get().load(Constants.OPEN_WEATHER_ICON_BASE_URL + getItem(position).weather.get(0).icon + ".png").
            into(holder.itemWeatherBinding.imgViewWeatherImage)
        holder.itemWeatherBinding.txtViewDayTemperature.text = (getItem(position).main.temp_min).
            toString().dropLast(2)
        holder.itemWeatherBinding.txtViewNightTemperature.text = (getItem(position).main.temp_max).
            toString().dropLast(2)
        holder.itemWeatherBinding.txtViewDay.text = formatDay(getItem(position).dt)
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

    class DiffCallback : DiffUtil.ItemCallback<X>() {
        override fun areItemsTheSame(oldItem: X, newItem: X): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: X, newItem: X): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: Int)
    }

    fun getItemAt(position: Int):X = getItem(position)

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}